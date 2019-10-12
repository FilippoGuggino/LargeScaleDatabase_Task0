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
        for(int i = 0; i < agenda.size(); i++) {
            tmp += agenda.remove(i).toString() + "\n";
        }
        return tmp;
    }

    public int getSize(){
        return agenda.size();
    }
}
