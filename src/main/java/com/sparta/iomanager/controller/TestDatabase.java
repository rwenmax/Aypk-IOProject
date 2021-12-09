package com.sparta.iomanager.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabase {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            new EmployeeDaoImpl().createTable();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}