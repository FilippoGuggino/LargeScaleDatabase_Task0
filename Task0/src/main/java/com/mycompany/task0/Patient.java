/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task0;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author leocecche
 */
public class Patient extends User {
    public int IDcode;
    public String patName;
    public String patSur;
    
    private static Connection general_conn = Start.general_conn;
    
    public Patient (String Name, String Surname) throws SQLException {
        this.patName = Name;
        this.patSur = Surname;
        boolean ok = sign_in();
        if(!ok){
            this.IDcode = 0;
        }
    }
    
    //function that manage login -> returns 1 if login is successful, returns 0
    //if login fails
    private boolean sign_in() throws SQLException {
        CallableStatement cs = general_conn.prepareCall("{CALL get_code(?,?,?)}");
        cs.setString(1, patName);
        cs.setString(2, patSur);
        cs.setString(3,"patient");
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            this.IDcode = rs.getInt(1);
            return true;
        }
        return false;
    }
    
    //funtion that manage registration, it is done only if account does 
    //not exist (IDcode = 0)
    public boolean sign_up() throws SQLException {
        if(IDcode != 0)
            return false;
        PreparedStatement ps = general_conn.prepareStatement(
                        "insert into patient"
                      + "values(?,?);");
        ps.setString(1, patName);
        ps.setString(2, patSur);
        int rs = ps.executeUpdate();
        if(rs > 0) {
            return true;
        }
        return false;
    }
}
