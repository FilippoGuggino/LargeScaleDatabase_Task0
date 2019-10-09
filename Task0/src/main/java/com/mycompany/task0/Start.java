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
 */
public class Start {
    public static void main(String[] args) throws SQLException{
        
        
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "");
                PreparedStatement ps = co.prepareStatement("select codfisc from clinica.paziente where name = ?;");
                ){
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
            co.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void showMenu(){
        Scanner sc = new Scanner(System.in);
        String type = "";
        System.out.println("Inserisci \"d\" se sei un dottore, \"p\" se sei un paziente:);");
        type = sc.nextLine();
        if(type.equals("d"))
            choicesDoc();
        else if(type.equals("p"))
            choicesPat();
        else{
            System.out.println("Input errato, inserisci d o p");
        }
        System.out.println("Inserisci il comando:");
        System.out.println("\t1 -> Crea Appuntamento");
        System.out.println("\t2 -> Cancella Appuntamento");
        System.out.println("\t3 -> ")
    }
    
    public static void choicesDoc(){
        System.out.println("");
    }
    
    public static void choicesPat(){
        
    }
}
