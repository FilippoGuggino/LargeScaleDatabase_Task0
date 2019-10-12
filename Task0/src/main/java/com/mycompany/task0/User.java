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
    protected String role;
    protected int idCode;
    
    public User(String name,String surname, String role)throws SQLException{
        if(name.compareTo("") == 0)
            name = "NaN";
         if(surname.compareTo("") == 0)
            surname = "NaN";
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
     protected void signIn() throws SQLException{
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
                        " insert into ?"
                      + " values(?,?);");
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
