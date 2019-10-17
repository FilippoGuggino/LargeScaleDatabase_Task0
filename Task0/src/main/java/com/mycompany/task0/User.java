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

import java.sql.*;

/**
 *
 * @author Zaccaria
 */
public class User {
    
    protected String name;
    protected String surname;
    protected int id;
    
    public User(String name,String surname, String role)throws SQLException{
        if(name.compareTo("") == 0)
            name = "NaN";
         if(surname.compareTo("") == 0)
            surname = "NaN";
        this.name=name;
        this.surname=surname;
        switch(role){
            case "d":
                signIn("doctor");
                break;
            case "p":
                signIn("patient");
                break;
            case "e":
                signIn("employee");
                break;
        }
    }
     protected void signIn(String role) throws SQLException{
        CallableStatement cs = Interface.connection.prepareCall("{CALL get_code(?,?,?)}");
        cs.setString(1, name);
        cs.setString(2, surname);
        cs.setString(3,role);
        ResultSet rs = cs.executeQuery();
        if(rs.next())
            id = rs.getInt(1);
        else 
            id=0;
    }
     
    /* Returns false if the user is already registered*/
    public boolean signUp(String role) throws SQLException{
        if(id != 0)
            return false;
        switch(role){
            case "d":
                role="doctor";
                break;
            case "p":
                role="patient";
                break;
            case "e":
                role="employee";
                break;
        }
        PreparedStatement ps = Interface.connection.prepareStatement(
                  //      " INSERT INTO ? (name,surname)"
                //      + " values('?','?')");
        "insert into "+role+"(name,surname) values('"+name+"','"+surname+"');");
     /*   ps.setString(1, role);
        ps.setString(2, name);
        ps.setString(3, surname);*/
        int rs = ps.executeUpdate();
        if(rs > 0) {
            return true;
        }
        return false;
    }
    public int getIdCode(){
        return id;
    }
}
