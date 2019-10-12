package com.mycompany.task0;

import javax.xml.transform.Result;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
    private String name;
    private String surname;
    private int id;

    public Doctor(String name, String surname) throws SQLException {
        this.name = name;
        this.surname = surname;

        int id = 0;
        ResultSet rs;
        CallableStatement cst = Start.con.prepareCall("call get_code(?, ?, ?);");
        cst.setString(1, name);
        cst.setString(2, surname);
        cst.setString(3, "doctor");

        rs = cst.executeQuery();
        rs.next();
        this.id = rs.getInt(1);
    }

    public Agenda getAgenda(String date) throws SQLException {
        ResultSet rs;
        CallableStatement cst = Start.con.prepareCall("call get_personal_agenda(?,?);");

        cst.setInt(1,this.id);
        cst.setString(2, date);
        rs = cst.executeQuery();

        return new Agenda(rs);
    }

    public String showAgenda(String date) throws SQLException {
        return  getAgenda(date).toString();
    }
}
