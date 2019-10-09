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
 */
public class Start {
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws SQLException{
        showMenu();
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
        codFisc -> paziente
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
        finaleDate -> data finale
    */
    public static String choicesPat(String codFisc){
        String choice = "";
        String docCodFisc = "";
        String date = "";
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
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione crea appuntamento
                break;
            case "2":
                System.out.println("Type doctor identification: ");
                docCodFisc = sc.nextLine();
                System.out.println("Type date in the format YYYY-MM-DD:");
                date = sc.nextLine();
                //TODO funzione cancella appuntamento
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
