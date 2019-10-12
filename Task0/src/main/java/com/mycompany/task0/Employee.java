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
    
    private int get_code(String name,String surname,String role) throws SQLException{
        int code = 0;
        CallableStatement cs = Interface.connection.prepareCall("{CALL get_code(?,?,?)}");
        cs.setString(1, name);
        cs.setString(2, surname);
        cs.setString(3,role);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            code = rs.getInt(1);
        }
        return code;
    }
    
    //approved = 0 -> rejected, else approved
    public boolean handle_medical_request(String pat_name,String pat_surname,String doc_name, String doc_surname, String date, int approved) throws SQLException{
        int patCode;
        int docCode;
        
        patCode = get_code(pat_name, pat_surname, "patient");
        if(patCode == 0){
            return false;
        }
        
        docCode = get_code(doc_name, doc_surname, "doctor");
        if(docCode == 0){
            return false;
        }
        
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
    public boolean handle_delete_request(String pat_name,String pat_surname,String doc_name, String doc_surname, String date, int approved) throws SQLException{
        int pat_code;
        int doc_code;
        
        pat_code = get_code(pat_name, pat_surname, "patient");
        if(pat_code == 0){
            return false;
        }
        
        doc_code = get_code(doc_name, doc_surname, "doctor");
        if(doc_code == 0){
            return false;
        }
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_delete_request(?,?,?,?)}");
        cs.setInt(1, pat_code);
        cs.setInt(2, doc_code);
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
    public boolean handle_move_request(String pat_name,String pat_surname,String doc_name, String doc_surname, String old_date, int approved) throws SQLException{
        int pat_code;
        int doc_code;
        
        pat_code = get_code(pat_name, pat_surname, "patient");
        if(pat_code == 0){
            return false;
        }
        
        doc_code = get_code(doc_name, doc_surname, "doctor");
        if(doc_code == 0){
            return false;
        }
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_move_request(?,?,?,?)}");
        cs.setInt(1, pat_code);
        cs.setInt(2, doc_code);
        cs.setString(3,old_date);
        cs.setInt(4,approved);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;   
    }
    
    public void print_medical_requests() throws SQLException{
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
    
    public void print_delete_requests() throws SQLException{
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "select m.name, m.surname, p.name, p.surname, m.medical_date"
                         + "(delete_request dq inner join medical m on dq.fk_medical = m.code) inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"                  
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", Date: " + rs.getString(5));
        }
    }
    
    public void print_move_requests() throws SQLException{
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
