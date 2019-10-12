/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task0;

import java.sql.*;

/**
 *
 * @author Zaccaria
 */
public class User {
    
    private String name;
    private String surname;
    private String role;
    private int idCode;
    
    public User(String name,String surname, String role)throws SQLException{
        this.name=name;
        this.surname=surname;
        switch(role){
            case "d":
                this.role="doctor";
                break;
            case "p":
                this.role="patient";
                break;
            case "e":
                this.role="employee";
                break;
        }
        signIn();
    }
     private void signIn() throws SQLException{
        CallableStatement cs = Interface.connection.prepareCall("{CALL get_code(?,?,?)}");
        cs.setString(1, name);
        cs.setString(2, surname);
        cs.setString(3,role);
        ResultSet rs = cs.executeQuery();
        if(rs.next())
            this.idCode = rs.getInt(1);
        else 
            idCode=0;
    }
    
    public boolean signUp() throws SQLException{
        if(idCode != 0)
            return false;
        PreparedStatement ps = Interface.connection.prepareStatement(
                        "insert into ?"
                      + "values(?,?);");
        ps.setString(1, role);
        ps.setString(2, name);
        ps.setString(3, surname);
        int rs = ps.executeUpdate();
        if(rs > 0) {
            return true;
        }
        return false;
    }
    public int getIdCode(){
        return idCode;
    }
}
