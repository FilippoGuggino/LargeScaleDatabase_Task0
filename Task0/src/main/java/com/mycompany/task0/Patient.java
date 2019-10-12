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
  
    public Patient (String Name, String Surname) {
        //uses super class constructor
        super(Name,Surname,'p');
    }
}
