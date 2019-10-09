/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Guggio 
 * 
 * 
 * 
 * 
 */
public class Start {

    try(
            static Connection general_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "");
            )
    {}catch(SQLException e){
        e.printStackTrace();
     }
        

    static Scanner sc = new Scanner(System.in);
    

    public static void main(String[] args) throws SQLException{
        showMenu();
    }
    
    public static void getAgenda(String doctor, String date) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "select fk_paziente "
                         + "from visita "
                         + "where fk_dottore = ? and data = ?;");
                ){
            ps.setString(1, doctor);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(
                        "Patient: " + rs.getString("fk_paziente")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void getMedicals(String doctor, String patient) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "select data "
                         + "from visita "
                         + "where fk_dottore = ? and fk_paziente = ?;");
                ){
            ps.setString(1, doctor);
            ps.setString(2, patient);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(
                        "Date: " + rs.getString("fk_paziente")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void newMedical(String type, String date, String doctor, String patient) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "insert into visita"
                         + "values "
                         + "(?,?,?,?);");
                
                ){
            ps.setString(1, type);
            ps.setString(2, date);
            ps.setString(3, doctor);
            ps.setString(4, patient);
            int rows = ps.executeUpdate();
            if(rows == 1)
                System.out.println("Your medical has been correctly inserted");
            else
                System.out.println("Something went wrong, check if medical already exists!");
        }catch(SQLException e){
            e.printStackTrace();
        }
    
    public static void deleteMedical(String date, String doctor, String patient) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "delete from visita"
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
        
    public static void updateMedical(String oldDate, String newDate, String doctor, String patient) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "update visita"
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
        
    public static void showMenu(){
        String type = "";
        String exit = "";
        String codFisc = "";
        
        System.out.println("Type \"d\" if you're a doctor, \"p\" if you are a patient: ");
        type = sc.nextLine();
        System.out.println("Type identification:");
        codFisc = sc.nextLine();
        
        while(!exit.equals("q")){
            switch (type) {
                case "d":
                    exit = choicesDoc(codFisc);
                    break;
                case "p":
                    exit = choicesPat(codFisc);
                    break;
                default:
                    System.out.println("Error, type d or p");
                    break;
            }
        }
    }
    
    /*
        codFisc -> dottore
        date -> data
    */
    public static String choicesDoc(String codFisc){
        String choice = "";
        String date = "";
        System.out.println("Type command: ");
        System.out.println("\t1 -> Show Agenda");
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        switch(choice){
            case "1": 
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione mostra agenda
                getAgenda(codFisc, date);
                break;
            case "q":
                return "q";
            default:
                System.out.println("Invalid command.");
                return "err";
        }
        return "ok";
    }
    
    /*  
        codFisc -> paziente
        docCodFisc -> dottore
        date -> data iniziale
        finalDate -> data finale
    */
    public static String choicesPat(String codFisc){
        String choice = "";
        String docCodFisc = "";
        String date = "";
        String type = "";
        System.out.println("Type command: ");
        System.out.println("\t1 -> Create Medical");
        System.out.println("\t2 -> drop Medical");
        System.out.println("\t3 -> Change Medical date");
        System.out.println("\tq -> Quit");
        choice = sc.nextLine();
        switch(choice){
            case "1":
                System.out.println("Type doctor identification: ");
                docCodFisc = sc.nextLine();
                System.out.println("Type visit type: ");
                type = sc.nextLine();
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione crea appuntamento
                newMedical(type, date, docCodFisc, codFisc);
                break;
            case "2":
                System.out.println("Type doctor identification: ");
                docCodFisc = sc.nextLine();
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione cancella appuntamento
                deleteMedical(date, docCodFisc, codFisc);
                break;
            case "3":
                String finalDate = "";
                System.out.println("Type doctor identification: ");
                docCodFisc = sc.nextLine();
                System.out.println("Type curret date of medical in the format YYYY-MM-DD:");
                date = sc.nextLine();
                System.out.println("Type final date of medical in the format YYYY-MM-DD:");
                finalDate = sc.nextLine();
                //TODO funzione sposta appuntamento
                updateMedical(date, finalDate, docCodFisc, codFisc);
                break;
            case "q":
                return "q";
            default:
                System.out.println("Invalid command.");
                return "err";
        }
        return "ok";
    }
    
    
    
    
}
