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

import javax.xml.transform.Result;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor extends User{
    private String name;
    private String surname;
    private int id;

    public Doctor(String name, String surname) throws SQLException {
        super(name, surname, "d");
    }

    public Agenda getAgenda(String date) throws SQLException {
        ResultSet rs;
        CallableStatement cst = Interface.connection.prepareCall("{CALL get_personal_agenda(?,?)}");

        cst.setInt(1,this.id);
        cst.setString(2, date);
        rs = cst.executeQuery();

        return new Agenda(rs);
    }

    public String showAgenda(String date) throws SQLException {
        return  getAgenda(date).toString();
    }
}
