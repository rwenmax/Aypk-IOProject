

/** This class implements the interface of StatementFactoryInterface **/

package com.sparta.iomanager.model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** This  class prepares all the MySQL statements to be executed, this also allow help with security, as we would set what each variable that is passed be set to i.e. first_name
 * would be set as String. */

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
    private ConnectionFactory conn = new ConnectionFactory();
    private  static String databaseName = "Employees";

    /**
    * Deletes the database name "Employees" can be set to different name by using setter/getter methods
    */
    public PreparedStatement getDatabaseDropStatement() throws SQLException, IOException {
        if (databaseDropStatement == null){
            databaseDropStatement = conn.getConnection().prepareStatement("DROP DATABASE IF EXISTS " + databaseName);
        }
        return databaseDropStatement;
    }

    /**
     * Creates the database name "Employees" can be set to different name by using setter/getter methods
     */
    public  PreparedStatement getDatabaseStatement() throws SQLException, IOException {
        if (databaseStatement == null){
            databaseStatement = conn.getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS  " + databaseName);
        }
        return databaseStatement;
    }

    /**
     *
     * Dropping the table if already exist
     */
    public  PreparedStatement getDropStatement() throws SQLException, IOException {
        conn.setDatabaseName(databaseName);
        if (dropStatement == null){
            dropStatement = conn.getConnection().prepareStatement("DROP TABLE IF EXISTS Employee");
        }
        return dropStatement;
    }


    /**
     *
     * Inserting the data into the database
     */
    public  PreparedStatement getInsertStatement() throws SQLException, IOException {
        // if (insertStatement == null){
        ConnectionFactory conn = new ConnectionFactory();
        conn.setDatabaseName(databaseName);
        insertStatement = conn.getConnection().
                prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?,?,?)");
        // }
        return insertStatement;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws IOException
     *
     * Test method for insertion without multithreading and batch rewriting!
     *
     */
    public  PreparedStatement getInsertStatementN() throws SQLException, IOException {
        // if (insertStatement == null){
        ConnectionFactory conn = new ConnectionFactory();
        conn.setDatabaseName(databaseName);
        insertStatement = conn.getConnectionT().
                prepareStatement("INSERT INTO Employee VALUES (?,?,?,?,?,?,?,?,?,?)");
        // }
        return insertStatement;
    }



    /**
     * Get all the employees from the table
     */
    public  PreparedStatement getAllEmployee() throws SQLException, IOException{
        if (selectAllEmployeeStatement == null){
            conn.setDatabaseName(databaseName);
            selectAllEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM Employee");
        }
        return selectAllEmployeeStatement;
    }

    /**
     * Creating the table that would hold the data
     */
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

    /** MySQL specific prepared statement **/
    public  PreparedStatement getEmployeeStatement() throws SQLException, IOException {
        if (selectEmployeeStatement == null)
        {
            conn.setDatabaseName(databaseName);
            selectEmployeeStatement = conn.getConnection().prepareStatement("SELECT * FROM employee WHERE employeeID = ?");
        }
        return selectEmployeeStatement;
    }
    /***/
    @Override
    public PreparedStatement getUpdateStatement() throws SQLException, IOException {
        if (updateStatement == null){
            updateStatement = conn.getConnection().prepareStatement("UPDATE employee SET first_name = ? WHERE employeeID = ?");
        }
        return updateStatement;
    }

    @Override
    public PreparedStatement getDeleteStatement() throws SQLException, IOException {
        if (deleteStatement == null){
            deleteStatement = conn.getConnection().prepareStatement("DELETE FROM employee WHERE employeeID = ?");
        }
        return deleteStatement;
    }
}
