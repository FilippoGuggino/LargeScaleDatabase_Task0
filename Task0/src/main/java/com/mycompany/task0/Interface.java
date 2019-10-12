/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author admin
 */
public class Interface {
    public static Connection connection;
    public static Scanner sc=new Scanner(System.in);
    
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
            switch (role) {
                case "d":
                    exit = "q";
                    break;
                case "p":
                    exit = "q";
                    break;
                case "e":
                    exit = "q";
                    break;
                default:
                    System.out.println("Error, type d, p or e ");
                    break;
            }
        }
        exit="";
        System.out.println("Type 1 if you want to sign in, 2 if you are not registered yet: ");
        type = sc.nextLine();
        
        while(!exit.equals("q")){
            switch (type) {
                case "1": 
                    System.out.println("Insert your name:");
                    name = sc.nextLine();
                    System.out.println("Insert your surname:");
                    surname = sc.nextLine();
                    user=new User(name,surname,role);
                   
                    if(user.getIdCode()!=0)
                        exit="q";
                    else
                        System.out.println("Autentication failed");
                    break;
                    
                case "2":
                    System.out.println("Insert your name:");
                    name = sc.nextLine();
                    System.out.println("Insert your surname:");
                    surname = sc.nextLine();
                    
                        user=new User(name,surname,role);             
                        if(user.signUp()){
                            exit="q";
                            System.out.println("Registration successfully completed!");
                        }
                        else
                            System.out.println("The user already exists");
                     
                    break;
                default:
                    System.out.println("Error, type 1 or 2");
                    break;
            }
        }
        while(true){
            switch(role){
                case "d":
                    Doctor d=(Doctor)user;
                    choicesDoc(d);
                    break;
                case "p":
                    Patient p=(Patient)user;
                //    choicesPat(p);
                    break;
                case "e":
                    Employee e=(Employee)user;
                    choicesEmp(e);
                    break;
            }
        }
    }
    
     /*
        codFisc -> dottore
        date -> data
    */
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
                    System.out.println("Type date in the format YYYY-MM-DD:");
                    date = sc.nextLine();
                    if(verifyDateFormat(date)==true)
                        doctor.showAgenda(date);              
                    else
                        System.out.println("Invalid date format.");
                    break;
                case "q":
                    return "q";
                default:
                    System.out.println("Invalid command.");
                    return "err";
            }
        }
    }
    public static Boolean verifyDateFormat(String date){
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
           return true;
        }
        return false;
    }
    /*  
        codFisc -> paziente
        docCodFisc -> dottore
        date -> data iniziale
        finalDate -> data finale
    *//*
    public static String choicesPat(String patIdCode){
        String choice = "";
        String docIdCode = "";
        String date = "";
        System.out.println("Type command: ");
        System.out.println("\t1 -> Create Medical");
        System.out.println("\t2 -> Drop Medical");
        System.out.println("\t3 -> Update Medical date");
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        switch(choice){
            case "1":
                System.out.println("Type doctor ID Code: ");
                docIdCode = sc.nextLine();
                
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true)
                    newMedical(docIdCode, patIdCode, date);
                else
                    System.out.println("Invalid date format.");

                break;
            case "2":
                System.out.println("Type doctor identification: ");
                docIdCode = sc.nextLine();
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true)
                    deleteMedical(docIdCode, patIdCode, date);
                else
                    System.out.println("Invalid date format.");

                break;
            case "3":
                String finalDate = "";
                System.out.println("Type doctor identification: ");
                docIdCode = sc.nextLine();
                System.out.println("Type curret date of medical in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==false){
                    System.out.println("Invalid date format.");
                    break;
                }
                System.out.println("Type final date of medical in the format YYYY-MM-DD:");
                finalDate = sc.nextLine();
                //TODO funzione sposta appuntamento
                if(verifyDateFormat(finalDate)==true)
                    updateMedical( docIdCode, patIdCode,date, finalDate);
                else
                    System.out.println("Invalid date format.");

                break;
            case "q":
                return "q";
            default:
                System.out.println("Invalid command.");
                return "err";
        }
        return "ok";
    }*/
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
        System.out.println("\t1 -> Create Medical");
        System.out.println("\t2 -> Drop Medical");
        System.out.println("\t3 -> Update Medical date");
        System.out.println("\t4 -> View Medical requests");
        System.out.println("\t5 -> View Delete requests");
        System.out.println("\t6 -> View Move requests");
        System.out.println("\t7 -> View schedule");       
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        switch(choice){
            case "1":
                System.out.println("Type patient name: ");
                patName = sc.nextLine();
                
                System.out.println("Type patient surname: ");
                patSurname = sc.nextLine();
                p=null;
                    p=new Patient(patName,patSurname);
                
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                System.out.println("Type doctor name: ");
                docName = sc.nextLine();
                
                System.out.println("Type doctor surname: ");
                docSurname = sc.nextLine();
                d=new Doctor(docName,docSurname);
                
                
                if(d.getIdCode()==0){
                    System.out.println("The doctor you selected doesn't exist");
                    break;
                }
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                if(verifyDateFormat(date)==true){
                    System.out.println("Type 0 if you want to approve the request");
                    choice = sc.nextLine();
                        employee.handleMedicalRequest(p,d, date,Integer.parseInt(choice));
                 
                }
                else
                    System.out.println("Invalid date format.");
                break;
            case "2":
                System.out.println("Type patient name: ");
                patName = sc.nextLine();
                
                System.out.println("Type patient surname: ");
                patSurname = sc.nextLine();
                    p=new Patient(patName,patSurname);
                
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                System.out.println("Type doctor name: ");
                docName = sc.nextLine();
                
                System.out.println("Type doctor surname: ");
                docSurname = sc.nextLine();
                   d=new Doctor(docName,docSurname);
                
                
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
                 System.out.println("Type patient name: ");
                patName = sc.nextLine();
                
                System.out.println("Type patient surname: ");
                patSurname = sc.nextLine();
                    p=new Patient(patName,patSurname);
                
                if(p.getIdCode()==0){
                    System.out.println("The patient you selected doesn't exist");
                    break;
                }
                System.out.println("Type doctor name: ");
                docName = sc.nextLine();
                
                System.out.println("Type doctor surname: ");
                docSurname = sc.nextLine();
                    d=new Doctor(docName,docSurname);
             
                if(d.getIdCode()==0){
                    System.out.println("The doctor you selected doesn't exist");
                    break;
                }
               
                System.out.println("Type curret date of medical in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione sposta appuntamento
                if(verifyDateFormat(date)==true){
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
                        System.out.println("Type the name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the surname of the doctor");
                        docSurname = sc.nextLine();
                        d=new Doctor(docName,docSurname);
                        if(d.getIdCode()==0){
                            System.out.println("The doctor you selected doesn't exist");
                            break;
                        }
                        employee.printSchedule(null, d, "");
                        break;
                    case "3":
                        System.out.println("Type the name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the surname of the patient");
                        patSurname = sc.nextLine();
                        p=new Patient(patName,patSurname);
                        if(p.getIdCode()==0){
                            System.out.println("The patient you selected doesn't exist");
                            break;
                        }
                        employee.printSchedule(p, null, "");
                        break;
                    case "4":
                        System.out.println("Type the name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the surname of the doctor");
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
                        System.out.println("Type the name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the surname of the patient");
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
                        System.out.println("Type the name of the patient");
                        patName = sc.nextLine();
                        System.out.println("Type the surname of the patient");
                        patSurname = sc.nextLine();
                        p=new Patient(patName,patSurname);
                        if(p.getIdCode()==0){
                            System.out.println("The patient you selected doesn't exist");
                            break;
                        }
                        System.out.println("Type the name of the doctor");
                        docName = sc.nextLine();
                        System.out.println("Type the surname of the doctor");
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
                default:
                    System.out.println("Invalid command.");
                    return "err";
        }
        return "ok";
    }
  /*
    public static void newMedical(String doctor, String patient, String date) {
        try(
                PreparedStatement ps = connection.prepareStatement(
                           "insert into medical(fk_doctor,fk_patient,medical_date)"
                         + "values "
                         + "(?,?,?);");
                ){
            ps.setString(1, doctor);
            ps.setString(2, patient);
            ps.setString(3, date);
            int rows = ps.executeUpdate();
            if(rows == 1)
                System.out.println("Your medical has been correctly inserted");
            else
                System.out.println("Something went wrong, check if medical already exists!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void deleteMedical(String doctor, String patient,String date) {
        try(
                PreparedStatement ps = connection.prepareStatement(
                           "delete from medicals"
                         + "where data=? and fk_dottore=? and fk_paziente=?;");
                ){
            ps.setString(1, date);
            ps.setString(2, doctor);
            ps.setString(3, patient);
            int rows = ps.executeUpdate();
            if(rows == 1)
                System.out.println("Your medical has been correctly deleted");
            else
                System.out.println("Something went wrong, check if medical data is correct!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateMedical(String doctor, String patient,String oldDate, String newDate) {
        try(
                PreparedStatement ps = connection.prepareStatement(
                           "update medicals"
                         + "set data = ? "
                         + "where fk_dottore = ? and fk_paziente = ? and data = ?");
                ){
            ps.setString(1, newDate);
            ps.setString(2, doctor);
            ps.setString(3, patient);
            ps.setString(4, oldDate);
            int rows = ps.executeUpdate();
            if(rows == 1)
                System.out.println("Your medical has been correctly modified");
            else
                System.out.println("Something went wrong, check if medical data is correct");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static Boolean signUp(String role,String name, String surname) {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "studenti");
            PreparedStatement ps = connection.prepareStatement(
                           "insert into ?(IDCode,name,surname)"
                         + "values "
                         + "(?,?,?);");
            switch(role){
                case "d":
                    ps.setString(1, "doctor");
                    break;
                case "p":
                    ps.setString(1, "patient");
                    break;
                case "e":
                    ps.setString(1, "employee");
                    break;
            }
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.executeUpdate();
            return true;
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static Boolean signIn(String role,String idCode) {
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "studenti");
            PreparedStatement ps = connection.prepareStatement(
                      "select * "
                    + "from ? "
                    + "where IDCode=?"
            );
            switch(role){
                case "d":
                    ps.setString(1, "doctor");
                    ps.setString(2, idCode);
                    break;
                case "p":
                    ps.setString(1, "patient");
                    ps.setString(2, idCode);
                    break;
                case "e":
                    ps.setString(1, "employee");
                    ps.setString(2, idCode);
                    break;
            }
            ResultSet rs = ps.executeQuery();
            if(rs==null)
                return false;
            return true;
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
        */
}
