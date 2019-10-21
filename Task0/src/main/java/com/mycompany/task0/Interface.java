/*
 * Copyright (C) 2019 baccio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.task0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author zacca
 */
public class Interface {
    public static Connection connection;
    public static Scanner sc=new Scanner(System.in);

    static{
        try {
            String url= "jdbc:mysql://localhost:3306/clinic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET";
String user= "root";
String pass= "";
connection= DriverManager.getConnection(url, user, pass);
   //         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinic?user=root&pass=");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showMenu()throws SQLException{
        String role = "";
        String type = "";
        String exit = "";
        String idCode  = "";
        String name  = "";
        String surname  = "";
        User user=null;
        System.out.println("Type \"d\" if you are a doctor, \"p\" if you are a patient, \"e\" if you are an employee: ");
        role = sc.nextLine();
        while(!exit.equals("q")){
            if(role.equals("d")||role.equals("p")||role.equals("e"))
                exit="q";
            else{
                System.out.println("Error, type d, p or e ");
                role = sc.nextLine();
            }
        }
        exit="";
        System.out.println("Type 1 if you want to sign in, 2 if you are not registered yet: ");
        type = sc.nextLine();
        
        while(!exit.equals("q")){
            switch (type) {
                case "1": 
                    user=createUser(role);
                    if(user.getIdCode()!=0){
                        System.out.println("Authentication succeded");
                        exit="q";
                    }
                    else
                        System.out.println("Authentication failed");
                    break;        
                case "2":
                    user=createUser(role);             
                    if(user.signUp(role)){
                        exit="q";
                        System.out.println("Registration successfully completed!");
                    }
                    else
                        System.out.println("User already exists");
                    break;
                default:
                    System.out.println("Error, type 1 or 2");
                    type = sc.nextLine();
                    break;
            }
        }
        while(true){
            switch(role){
                case "d":
                    Doctor d = (Doctor)user;
                    choicesDoc(d);
                    break;
                case "p":
                    Patient p=(Patient)user;
                    choicesPat(p);
                    break;
                case "e":
                    Employee e=(Employee)user;
                    choicesEmp(e);
                    break;
            }
        }
    }
    public static Boolean verifyDateFormat(){
        System.out.println("Type date in the format YYYY-MM-DD:");
        String date = sc.nextLine();
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
           return true;
        }
        return false;
    }
    public static Boolean verifyDateFormat(String date){
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
           return true;
        }
        return false;
    }
    public static User createUser(String role)throws SQLException{
        System.out.println("Type your first name:");
        String name = sc.nextLine();
        System.out.println("type your last name:");
        String surname = sc.nextLine(); 
        switch(role){
            case "d":
                return new Doctor(name,surname);
            case "p":
                return new Patient(name,surname);
            case "e":
                return new Employee(name,surname);
        }
        return null;
    }
    public static Doctor createDoctor()throws SQLException{
        System.out.println("Type the doctor's first name:");
        String name = sc.nextLine();
        System.out.println("type the doctor's last name:");
        String surname = sc.nextLine(); 
        return new Doctor(name,surname);
    }
    public static Patient createPatient()throws SQLException{
        System.out.println("Type patient first name: ");
        String patName = sc.nextLine();
        System.out.println("Type patient last name: ");
        String patSurname = sc.nextLine();
        return new Patient(patName,patSurname);
    }

    public static String choicesDoc(Doctor doctor)throws SQLException{
        String choice = "";
        String date = "";
        while(true){
            System.out.println("Type command: ");
            System.out.println("\t1 -> Show Agenda");
            System.out.println("\tq -> Quit");
            choice = sc.nextLine();
            switch(choice){
                case "1":        
                    if(verifyDateFormat()==true)

                        System.out.println(doctor.showAgenda(date));              
                    else
                        System.out.println("Invalid date format.");
                    break;
                case "q":
                    System.exit(1);
                default:
                    System.out.println("Invalid command.");
                    return "err";
            }
        }
    }
  
    public static String choicesPat(Patient patient)throws SQLException{
        String choice = "";
        String docIdCode = "";
        String date = "";
        System.out.println("Type command: ");
        System.out.println("\t1 -> Make medical request");
        System.out.println("\t2 -> Make medical drop request");
        System.out.println("\t3 -> Make medical move request");        
        System.out.println("\t4 -> View personal schedule");
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        Doctor doctor=null;
        switch(choice){
            case "1":
                doctor=createDoctor();
                if(doctor.getIdCode()==0){
                    System.out.println("The selected doctor doesn't exist");
                    break;
                }
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true)
                    patient.newMedicalRequest(doctor, date);
                else
                    System.out.println("Invalid date format.");
                break;
            case "2":
                doctor=createDoctor();
                if(doctor.getIdCode()==0){
                    System.out.println("The selected doctor doesn't exist");
                    break;
                }
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true)
                    patient.deleteMedicalRequest(doctor, date);
                else
                    System.out.println("Invalid date format.");
                break;
            case "3":
                doctor=createDoctor();
                if(doctor.getIdCode()==0){
                    System.out.println("The selected doctor doesn't exist");
                    break;
                }
                System.out.println("Type current date of medical in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==false){
                    System.out.println("Invalid date format.");
                    break;
                }
                System.out.println("Type new date of medical in the format YYYY-MM-DD:");
                String finalDate = sc.nextLine();
                //TODO funzione sposta appuntamento
                if(verifyDateFormat(finalDate)==true)
                    patient.newMoveRequest(doctor,date, finalDate);
                else
                    System.out.println("Invalid date format.");
                break;
            case "4":
                patient.printSchedule();
                break;
            case "q":
                System.exit(1);
            default:
                System.out.println("Invalid command.");
                return "err";
        }
        return "ok";
    }
    public static String choicesEmp(Employee employee)throws SQLException{
        String choice = "";
        String docName = "";
        String docSurname = "";   
        String patName = "";        
        String patSurname = "";        
        String date = "";
        Doctor d=null;
        Patient p=null;
        System.out.println("Type command: ");
        System.out.println("\t1 -> Handle medical request");
        System.out.println("\t2 -> Handle medical drop request");
        System.out.println("\t3 -> Handle medical update request");
        System.out.println("\t4 -> View Medical requests");
        System.out.println("\t5 -> View Delete requests");
        System.out.println("\t6 -> View Move requests");
        System.out.println("\t7 -> View schedule");       
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        switch(choice){
            case "1":
                p=createPatient();
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                d=createDoctor();
                if(d.getIdCode()==0){
                    System.out.println("The doctor you selected doesn't exist");
                    break;
                }
                if(verifyDateFormat()==true){
                    System.out.println("Type 0 if you want to approve the request");
                    choice = sc.nextLine();
                    employee.handleMedicalRequest(p,d, date,Integer.parseInt(choice));
                    System.out.println("Operation succeeded");
                }
                else
                    System.out.println("Invalid date format.");
                break;
            case "2":
                p=createPatient();
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                d=createDoctor();
                if(d.getIdCode()==0){
                    System.out.println("The doctor you selected doesn't exist");
                    break;
                }
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true){
                    System.out.println("Type 0 if you want to approve the request");
                    choice = sc.nextLine();
                        employee.handleDeleteRequest(p,d, date,Integer.parseInt(choice));
                
                }
                else
                    System.out.println("Invalid date format.");
                break;
                
            case "3":
                p=createPatient();
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                d=createDoctor();
                if(d.getIdCode()==0){
                    System.out.println("The doctor you selected doesn't exist");
                    break;
                }

                if(verifyDateFormat()==true){
                    System.out.println("Type 0 if you want to approve the request");
                    choice = sc.nextLine();
                    employee.handleMoveRequest(p,d, date,Integer.parseInt(choice));                  
                }
                else
                    System.out.println("Invalid date format.");
                break;
                
            case "4":
                employee.printMedicalRequests();
                break;
            case "5":
                employee.printDeleteRequests();
                break;
            case "6":
                employee.printMoveRequests();
                break;
            case "7":
                System.out.println("Select an option: ");
                System.out.println("\t1 -> View schedule per day");
                System.out.println("\t2 -> View schedule per doctor");
                System.out.println("\t3 -> View schedule per patient");
                System.out.println("\t4 -> View schedule per day and doctor");
                System.out.println("\t5 -> View schedule per day and patient");
                System.out.println("\t6 -> View schedule per doctor and patient");
                choice = sc.nextLine();
                switch(choice){
                    case "1":
                        System.out.println("Type date in the format YYYY-MM-DD:");
                        date = sc.nextLine();
                        if(verifyDateFormat(date)==true)
                            employee.printSchedule(null, null, date);
                        else
                            System.out.println("Invalid date format.");
                        break;
                    case "2":
                        System.out.println("Type the first name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the last name of the doctor");
                        docSurname = sc.nextLine();
                        d=new Doctor(docName,docSurname);
                        if(d.getIdCode()==0){
                            System.out.println("The doctor you selected doesn't exist");
                            break;
                        }
                        employee.printSchedule(null, d, "");
                        break;
                    case "3":
                        System.out.println("Type the first name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the last name of the patient");
                        patSurname = sc.nextLine();
                        p=new Patient(patName,patSurname);
                        if(p.getIdCode()==0){
                            System.out.println("The patient you selected doesn't exist");
                            break;
                        }
                        employee.printSchedule(p, null, "");
                        break;
                    case "4":
                        System.out.println("Type the first name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the last name of the doctor");
                        docSurname = sc.nextLine();
                        d=new Doctor(docName,docSurname);
                        if(d.getIdCode()==0){
                            System.out.println("The doctor you selected doesn't exist");
                            break;
                        }
                        System.out.println("Type date in the format YYYY-MM-DD:");
                        date = sc.nextLine();
                        if(verifyDateFormat(date)==true)
                            employee.printSchedule(null, d, date);
                        else
                            System.out.println("Invalid date format.");
                        break;
                     case "5":
                        System.out.println("Type the first name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the last name of the patient");
                        patSurname = sc.nextLine();
                        p=new Patient(patName,patSurname);
                        if(p.getIdCode()==0){
                            System.out.println("The patient you selected doesn't exist");
                            break;
                        }
                        System.out.println("Type date in the format YYYY-MM-DD:");
                        date = sc.nextLine();
                        if(verifyDateFormat(date)==true)
                            employee.printSchedule(p, null, date);
                        else
                            System.out.println("Invalid date format.");
                        break;
                    case "6":
                        System.out.println("Type the first name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the last name of the patient");
                        patSurname = sc.nextLine();
                        p=new Patient(patName,patSurname);
                        if(p.getIdCode()==0){
                            System.out.println("The patient you selected doesn't exist");
                            break;
                        }
                        System.out.println("Type the first name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the last name of the doctor");
                        docSurname = sc.nextLine();
                        d=new Doctor(docName,docSurname);
                        if(d.getIdCode()==0){
                            System.out.println("The doctor you selected doesn't exist");
                            break;
                        }
                        employee.printSchedule(p, d, "");
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
                break;

                default:
                    System.out.println("Invalid command.");
                    return "err";
        }
        return "ok";
    }  
}
