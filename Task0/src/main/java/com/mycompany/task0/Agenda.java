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

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Agenda {
    private Vector<Medical> agenda;

    public Agenda(ResultSet rs) throws SQLException {
        Medical tmp;
        agenda = new Vector<Medical>();
        while(rs.next()) {
            tmp = new Medical(rs.getString(1) + " " + rs.getString(2),
                                rs.getString(3) + " " + rs.getString(4),
                                rs.getString(5));
            this.agenda.add(tmp);
        }
    }

    public void addMedical(String docName, String patName, String date){
        agenda.add(new Medical(docName, patName, date));
    }

    public String toString(){
        if(agenda.isEmpty())
            return "No medical in the Agenda.\n";
        String tmp = "";
        for(int i = 0; i < agenda.size(); i++)
            tmp += agenda.remove(i).toString() + "\n";
        return tmp;
    }

    public int getSize(){
        return agenda.size();
    }
}
