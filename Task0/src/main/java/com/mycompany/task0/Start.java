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
        Scanner sc = new Scanner(System.in);
        String nome = sc.nextLine();
        
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
}
