package com.sparta.iomanager.controller;

import com.sparta.iomanager.view.Report;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {


    private static Connection theConnection  = null;

    public static Connection getConnection() throws SQLException, IOException {

        /* Reading the properties file for user and database Connection, database should be created with the name "Employees"*/
        if (theConnection ==null) {
            Properties properties = new Properties();
            properties.load(new FileReader("connection.properties"));
            String url = properties.getProperty("dbUrl");
            String userId = properties.getProperty("dbUserName");
            String userPassword = properties.getProperty("dbUserPassword");
            theConnection = DriverManager.getConnection(url, userId, userPassword);
        }
        return theConnection;
    }


    public static Connection getConnection2() throws SQLException, IOException {



        /* Reading the properties file for user and database Connection, database should be created with the name "Employees"*/
      /*  try (Connection theConn = DriverManager.getConnection(url, userId, userPassword)){

        }*/
            Properties properties = new Properties();
            properties.load(new FileReader("connection.properties"));
            String url = properties.getProperty("dbUrl");
            String userId = properties.getProperty("dbUserName");
            String userPassword = properties.getProperty("dbUserPassword");
            theConnection = DriverManager.getConnection(url, userId, userPassword);

        return theConnection;
    }




    public static void closeConnection() throws SQLException {
        if (theConnection !=null) theConnection.close();
    }

}
