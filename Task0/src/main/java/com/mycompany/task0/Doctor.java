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
