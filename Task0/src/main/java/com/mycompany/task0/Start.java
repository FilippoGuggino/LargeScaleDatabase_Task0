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
    static Connection general_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinica", "root", "");
    public static void main(String[] args) throws SQLException{
        try(
                PreparedStatement ps = general_conn.prepareStatement(
                           "select * from visita where fk_dottore = ? and data = ?;"
                            );
                ){
            ps.setString(1, doctor);
            ps.setString(2, date)
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
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
    
    
}
