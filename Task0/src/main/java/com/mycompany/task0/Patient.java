/*
 * Copyright (C) 2019 baccio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
	
	/**
	* constructor which takes and initializes all attributes of the class using
	* the constructor of superclass User.
	* @param Name first name of the patient
	* @param Surname last name of the patient
	* @throws SQLException if an SQL exception occurred retrieving the related
	*                      record in the database
	*/
    public Patient (String Name, String Surname) throws SQLException{
        //uses super class constructor
        super(Name,Surname,"p");
    }
    
	/**
	* prints the schedule of the patient in the format Doctor: doctor name surname, date: xxxx
	* @throws SQLException if an SQL exception occurred retrieving the related
	*                      record in the database
	*/
    //prints string of the schedule of the patient in the format Doctor: name surname, date: xxxx
    public void printSchedule() throws SQLException {
    	ResultSet rs;
        CallableStatement cst = Interface.connection.prepareCall("{CALL get_personal_schedule(?)}");
        cst.setInt(1,this.idCode);
        rs = cst.executeQuery();
        
        while(rs.next()) {
        	System.out.println("Doctor " + rs.getString(1) + " " + rs.getString(2) + ",date " + rs.getString(3));
        }
    }
    
	/**
	* inserts a new medical request for the patient with the specified doctor
	* @param doctor doctor that is requested by the patient
	* @param date date that is requested by the patient
	* @throws SQLException if an SQL exception occurred retrieving the related
	*                      record in the database
	* @return true if request is correctly inserted, false if doctor does not exists or something went wrong
	*/
    
    //returns true if everything was ok, false if it fails
    public boolean newMedicalRequest (Doctor doctor, String date) throws SQLException{
        if(doctor == null || doctor.getIdCode() == 0)
            return false;
        int docCode = doctor.getIdCode();
    	ResultSet rs;
        CallableStatement cst = Interface.connection.prepareCall("{CALL new_medical_request(?,?,?)}");
        cst.setInt(1,this.idCode);
        cst.setInt(2, docCode);
        cst.setString(3, date);
        rs = cst.executeQuery();
        
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;
    }
    
	/**
	* inserts a new request of cancellation for the specified medical request
	* @param doctor doctor that is requested by the patient
	* @param date date of the specified medical request that is asked to be deleted
	* @throws SQLException if an SQL exception occurred retrieving the related
	*                      record in the database
	* @return true if request is correctly inserted, false if doctor does not exists or something went wrong
	*/
    
    //returns true if everything was ok, false if it fails
    public boolean deleteMedicalRequest(Doctor doctor,String date) throws SQLException{
        if(doctor == null || doctor.getIdCode() == 0)
            return false;
        int docCode = doctor.getIdCode();
       	ResultSet rs;
        CallableStatement cst = Interface.connection.prepareCall("{CALL new_delete_request(?,?,?)}");
        cst.setInt(1,this.idCode);
        cst.setInt(2, docCode);
        cst.setString(3, date);
        rs = cst.executeQuery();
        
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;
    }
    
	/**
	* inserts a new request of cancellation for the specified medical request
	* @param doctor doctor that is requested by the patient
	* @param oldDate date of the specified medical request that is asked to be moved
	* @param newDate new date for the medical request
	* @throws SQLException if an SQL exception occurred retrieving the related
	*                      record in the database
	* @return true if request is correctly inserted, false if doctor does not exists or something went wrong
	*/
    
    //returns true if everything was ok, false if it fails
    public boolean newMoveRequest(Doctor doctor,String oldDate,String newDate) throws SQLException{
       	if(doctor == null || doctor.getIdCode() == 0)
            return false;
        int docCode = doctor.getIdCode();
        ResultSet rs;
        CallableStatement cst = Interface.connection.prepareCall("{CALL new_move_request(?,?,?,?)}");
        cst.setInt(1,this.idCode);
        cst.setInt(2, docCode);
        cst.setString(3, oldDate);
        cst.setString(4, newDate);
        rs = cst.executeQuery();
        
        if(rs.next()){
            if(rs.getInt(1) == 1)
               return true;
        }
        return false;
    }
    
}

