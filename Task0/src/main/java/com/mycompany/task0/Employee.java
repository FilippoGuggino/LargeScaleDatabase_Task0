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
 * @author baccio
 */
public class Employee extends User{
   
    
    public Employee(String name, String surname) throws SQLException{
        super(name,surname,"e");
    }
    
    public boolean handleMedicalRequest(Patient patient,Doctor doctor, String date, int approved) throws SQLException{
        int patCode=patient.getIdCode();
        int docCode=doctor.getIdCode();
    
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_medical_request(?,?,?,?)}");
        cs.setInt(1, patCode);
        cs.setInt(2, docCode);
        cs.setString(3,date);
        cs.setInt(4,approved);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;   
    }
    
    //approved = 0 -> rejected, else approved
    public boolean handleDeleteRequest(Patient patient,Doctor doctor, String date, int approved) throws SQLException{
        int patCode=patient.getIdCode();
        int docCode=doctor.getIdCode();
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_delete_request(?,?,?,?)}");
        cs.setInt(1, patCode);
        cs.setInt(2, docCode);
        cs.setString(3,date);
        cs.setInt(4,approved);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;   
    }
    
    //approved = 0 -> rejected, else approved
    public boolean handleMoveRequest(Patient patient,Doctor doctor, String old_date, int approved) throws SQLException{
        int patCode=patient.getIdCode();
        int docCode=doctor.getIdCode();
        
      
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_move_request(?,?,?,?)}");
        cs.setInt(1, patCode);
        cs.setInt(2, docCode);
        cs.setString(3,old_date);
        cs.setInt(4,approved);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;   
    }
    
    public void printMedicalRequests() throws SQLException{
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "select m.name, m.surname, p.name, p.surname, m.medical_date"
                         + "medical m inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"
                         + "where approved=false"
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", Date: " + rs.getString(5));
        }
    }
    
    public void printDeleteRequests() throws SQLException{
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "select m.name, m.surname, p.name, p.surname, m.medical_date"
                         + "(delete_request dq inner join medical m on dq.fk_medical = m.code) inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"                  
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", Date: " + rs.getString(5));
        }
    }
    
    public void printMoveRequests() throws SQLException{
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "select m.name, m.surname, p.name, p.surname, m.medical_date, mq.new_date"
                         + "(move_request mq inner join medical m on mq.fk_medical = m.code) inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"                  
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", old date: " + rs.getString(5) + "new date: " + rs.getString(6));
        }
    }
    
    
    
}
