package com.sparta.iomanager.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementFactory {
    private static PreparedStatement createStatement = null;
    private static PreparedStatement insertStatement = null;
    private static PreparedStatement selectEmployeeStatement = null;
    private static PreparedStatement deleteStatement = null;
    private static PreparedStatement updateStatement = null;
    private static PreparedStatement dropStatement = null;
    private static PreparedStatement selectAllEmployeeStatement = null;
    private static ConnectionFactory conn = new ConnectionFactory();


    public PreparedStatement getDropStatement() throws SQLException, IOException {
        if (dropStatement == null){
            dropStatement = conn.getConnection().prepareStatement("DROP TABLE IF EXISTS Employee");

        }
        return dropStatement;
    }


    public  PreparedStatement getInsertStatement() throws SQLException, IOException {
       // if (insertStatement == null){
             ConnectionFactory conn = new ConnectionFactory();
            insertStatement = conn.getConnection().prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?,?,?)");
       // }
        return insertStatement;
    }

    public PreparedStatement getAllEmployee() throws SQLException, IOException{
        if (selectAllEmployeeStatement == null){
            selectAllEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM Employee");
        }
        return selectAllEmployeeStatement;
    }





    public  PreparedStatement getCreateStatement() throws SQLException, IOException{
        if (createStatement == null){
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

    public  PreparedStatement getEmployeeStatement() throws SQLException, IOException {
        if (selectEmployeeStatement == null)
        {
            selectEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM employee WHERE employeeID = ?");
        }
        return selectEmployeeStatement;
    }






    public static void closeStatement() throws SQLException {
        if (insertStatement != null) insertStatement.close();
        if (createStatement != null) createStatement.close();
        if (deleteStatement != null) deleteStatement.close();
        if (updateStatement !=null) updateStatement.close();
        if (dropStatement !=null) dropStatement.close();

    }



}
