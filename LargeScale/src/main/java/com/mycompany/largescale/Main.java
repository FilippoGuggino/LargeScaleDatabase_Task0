/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.largescale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author guggi
 */
public class Main {
    public static void main(String[] agrs){
        Scanner in = new Scanner(System.in);
        
        try(
                Connection co = DriverManager.getConnection("jdbc:mysql://localhost/clinica");
                PreparedStatement ps = co.prepareStatement("select * from pazienti");
                ){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SY
            }
            
        }catch(SQLException e){}
    }
}
