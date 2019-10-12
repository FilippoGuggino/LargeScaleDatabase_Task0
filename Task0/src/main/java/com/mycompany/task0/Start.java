/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task0;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Guggio 
 * 
 * 
 * 
 * 
 */
public class Start {

    public static void main(String[] args) {
        try{
            Interface.showMenu();
        }
        catch(SQLException e){
            e.getMessage();
        }
        
    }
    
    
}
