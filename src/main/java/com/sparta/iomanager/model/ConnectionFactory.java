/** Creates the connection with the database provided the connection.properties file **/
package com.sparta.iomanager.model;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
public class ConnectionFactory {

    private static String databaseName ="";
    private  static Connection theConnection  = null;


    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }


    public  static Connection getConnection() throws SQLException, IOException {
        /* Reading the properties file for user and database Connection, database should be created with the name "Employees"*/
        if (theConnection==null || theConnection.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileReader("connection.properties"));
            String url = properties.getProperty("dbUrl") + databaseName;
            String userId = properties.getProperty("dbUserName");
            String userPassword = properties.getProperty("dbUserPassword");
            //theConnection = DriverManager.getConnection(url, userId, userPassword);
            theConnection = DriverManager.getConnection(url  + "?rewriteBatchedStatements=true", userId, userPassword);
        }
        return theConnection;
    }



    public  Connection getConnectionT() throws SQLException, IOException {
        /* Reading the properties file for user and database Connection, database should be created with the name "Employees"*/
        if (theConnection==null || theConnection.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileReader("connection.properties"));
            String url = properties.getProperty("dbUrl") + databaseName;
            String userId = properties.getProperty("dbUserName");
            String userPassword = properties.getProperty("dbUserPassword");
            //theConnection = DriverManager.getConnection(url, userId, userPassword);
            theConnection = DriverManager.getConnection(url  , userId, userPassword);
        }
        return theConnection;
    }





/*
    public Connection getConnection2() throws SQLException, IOException {
        Properties props;
        String url = null;
        String userId = null;
        String password = null;
        try (FileReader props_file = new FileReader("connection.properties")) {
            props = new Properties();
            props.load(props_file);
            url = props.getProperty("dbUrl");
            userId = props.getProperty("dbUserName");
            password = props.getProperty("dbUserPassword");
        } catch (IOException e){
            e.printStackTrace();
        }

        try (Connection theConn = DriverManager.getConnection(url + databaseName + "?rewriteBatchedStatements=true", userId, password))
        {
            return theConn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    public static void closeConnection() throws SQLException, IOException {
        if (theConnection !=null) theConnection.close();
    }

}
