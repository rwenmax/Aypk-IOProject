package com.sparta.iomanager.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementFactory implements StatementFactoryInterface {
    private  PreparedStatement createStatement = null;
    private  PreparedStatement insertStatement = null;
    private  PreparedStatement selectEmployeeStatement = null;
    private  PreparedStatement deleteStatement = null;
    private  PreparedStatement updateStatement = null;
    private  PreparedStatement dropStatement = null;
    private  PreparedStatement selectAllEmployeeStatement = null;
    private  PreparedStatement databaseDropStatement = null;
    private  PreparedStatement databaseStatement = null;
    private  ConnectionFactory conn = new ConnectionFactory();
    private  static String databaseName = "Employees";


    public PreparedStatement getDatabaseDropStatement() throws SQLException, IOException {
        if (databaseDropStatement == null){
            databaseDropStatement = conn.getConnection().prepareStatement("DROP DATABASE IF EXISTS " + databaseName);
        }
        return databaseDropStatement;
    }

    public  PreparedStatement getDatabaseStatement() throws SQLException, IOException {
        if (databaseStatement == null){
            databaseStatement = conn.getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS  " + databaseName);
        }
        return databaseStatement;
    }


    public  PreparedStatement getDropStatement() throws SQLException, IOException {
        conn.setDatabaseName(databaseName);
        if (dropStatement == null){
            dropStatement = conn.getConnection().prepareStatement("DROP TABLE IF EXISTS Employee");
        }
        return dropStatement;
    }


    public  PreparedStatement getInsertStatement() throws SQLException, IOException {
        // if (insertStatement == null){
        ConnectionFactory conn = new ConnectionFactory();
        conn.setDatabaseName(databaseName);
        insertStatement = conn.getConnection().
                prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?,?,?)");
        // }
        return insertStatement;
    }

    public  PreparedStatement getAllEmployee() throws SQLException, IOException{
        if (selectAllEmployeeStatement == null){
            conn.setDatabaseName(databaseName);
            selectAllEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM Employee");
        }
        return selectAllEmployeeStatement;
    }


    public  PreparedStatement getCreateStatement() throws SQLException, IOException{
        if (createStatement == null){
            conn.setDatabaseName(databaseName);
            createStatement = conn.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + "Employee ("
                    + "employeeID INT PRIMARY KEY NOT NULL,"
                    + "name_prefix VARCHAR(5),"
                    + "first_name VARCHAR (20),"
                    + "middle_initial CHAR(1),"
                    + "last_name VARCHAR (20),"
                    + "gender CHAR(1),"
                    + "email VARCHAR(50),"
                    + "date_of_birth DATE, "
                    + "date_of_joining DATE,"
                    + "salary INT"
                    +")");
        }
        return createStatement;
    }

    /** MySQL specific prepared statement**/
    public  PreparedStatement getEmployeeStatement() throws SQLException, IOException {
        if (selectEmployeeStatement == null)
        {
            conn.setDatabaseName(databaseName);
            selectEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM employee WHERE employeeID = ?");
        }
        return selectEmployeeStatement;
    }
}
