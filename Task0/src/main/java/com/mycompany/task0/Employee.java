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
    
    
    public boolean addMedical(String pat_name,String pat_surname,String doc_name, String doc_surname, String date) throws SQLException {
        Patient pat = new Patient(pat_name, pat_surname);
        Doctor doc = new Doctor(doc_name, doc_surname);
        if(pat.getIdCode() == 0 || doc.getIdCode() == 0)
            return false;
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "Insert into medical" 
                         + "values(?,?,?,true);");
        ps.setInt(1, doc.getIdCode());
        ps.setInt(2, pat.getIdCode());
        ps.setString(3, date);
        int rs = ps.executeUpdate();
        return rs == 1;
    }
    
    public boolean dropMedical(String pat_name,String pat_surname,String doc_name, String doc_surname, String date) throws SQLException {
        Patient pat = new Patient(pat_name, pat_surname);
        Doctor doc = new Doctor(doc_name, doc_surname);
        if(pat.getIdCode() == 0 || doc.getIdCode() == 0)
            return false;
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "delete from medical" 
                         + "where fk_doctor = ? and fk_patient = ? and medical_date = ?;");
        ps.setInt(1, doc.getIdCode());
        ps.setInt(2, pat.getIdCode());
        ps.setString(3, date);
        int rs = ps.executeUpdate();
        return rs == 1;
    }
    
    //approved = 0 -> rejected, else approved
    public boolean handleMedicalRequest(String pat_name,String pat_surname,String doc_name, String doc_surname, String date, int approved) throws SQLException{
        Patient pat = new Patient(pat_name, pat_surname);
        Doctor doc = new Doctor(doc_name, doc_surname);
        
        if(pat.getIdCode() == 0 || doc.getIdCode() == 0)
            return false;
       
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_medical_request(?,?,?,?)}");
        cs.setInt(1, pat.getIdCode());
        cs.setInt(2, doc.getIdCode());
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
    public boolean handleDeleteRequest(String pat_name,String pat_surname,String doc_name, String doc_surname, String date, int approved) throws SQLException{
        Patient pat = new Patient(pat_name, pat_surname);
        Doctor doc = new Doctor(doc_name, doc_surname);
        
        if(pat.getIdCode() == 0 || doc.getIdCode() == 0)
            return false;
       
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_delete_request(?,?,?,?)}");
        cs.setInt(1, pat.getIdCode());
        cs.setInt(2, doc.getIdCode());
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
    public boolean handleMoveRequest(String pat_name,String pat_surname,String doc_name, String doc_surname, String old_date, int approved) throws SQLException{
        Patient pat = new Patient(pat_name, pat_surname);
        Doctor doc = new Doctor(doc_name, doc_surname);
        
        if(pat.getIdCode() == 0 || doc.getIdCode() == 0)
            return false;
        
        CallableStatement cs = Interface.connection.prepareCall("{CALL handle_move_request(?,?,?,?)}");
        cs.setInt(1, pat.getIdCode());
        cs.setInt(2, doc.getIdCode());
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
                           "select d.name, d.surname, p.name, p.surname, m.medical_date"
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
                           "select d.name, d.surname, p.name, p.surname, m.medical_date"
                         + "(delete_request dq inner join medical m on dq.fk_medical = m.code) inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"                  
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", Date: " + rs.getString(5));
        }
    }
    
    public void printMoveRequests() throws SQLException{
        PreparedStatement ps = Interface.connection.prepareStatement(
                           "select d.name, d.surname, p.name, p.surname, m.medical_date, mq.new_date"
                         + "(move_request mq inner join medical m on mq.fk_medical = m.code) inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"                  
                         + "order by m.medical_date;");
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", old date: " + rs.getString(5) + "new date: " + rs.getString(6));
        }
    }
    
    //pass "" to paramethers you don't want to select by
    public void printSchedule(String byDocName, String byDocSurname, String byPatName, String byPatSurname, String byDate) throws SQLException{
        Patient pat = new Patient(byPatName, byPatSurname);
        Doctor doc = new Doctor(byDocName, byDocSurname);
        
        String query = "select d.name, d.surname, p.name, p.surname, m.medical_date"
                     + "from medical m inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode"
                     + "where ";
        boolean previous = false;
        if(pat.getIdCode() != 0) {
            query += "m.fk_patient = ?";
            previous = true;
        }
        if(doc.getIdCode() != 0) {
            if(previous)
                query += " and ";
            query += "m.fk_doctor = ?";
            previous = true;
        }
        if(byDate.compareTo("") != 0) {
            if(previous)
                query += " and ";
            query += "m.medical_date = ?";
        }
        query += ";";
        
        PreparedStatement ps = Interface.connection.prepareStatement(query);
        int counter = 1;
        if(pat.getIdCode() != 0) {
            ps.setInt(counter++, pat.getIdCode());
        }
        if(doc.getIdCode() != 0) {
            ps.setInt(counter++, doc.getIdCode());
        }
        if(byDate.compareTo("") != 0) {
            ps.setString(counter++, byDate);
        }
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ", Patient " + rs.getString(3) + " " + rs.getString(4) + ", date: " + rs.getString(5));
        }
    }
    
    
}
