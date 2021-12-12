package com.sparta.iomanager.model;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface DAO {
    /** Interface Class for Data Access Object, implement this class to your database specific requirements.
     *
     * @param employee
     * @return
     * @throws SQLException
     * @throws IOException
     *
     */

    /** Create database **/
    void createDatabase() throws  SQLException, IOException;

    /** Dropping the database */
    void dropDatabase() throws SQLException, IOException;

    /** Create the table in the database **/
    boolean createTable() throws SQLException, IOException;

    /** Dropping the table */
    void dropTable() throws SQLException, IOException;

    /** Method to insert data into database, uses Map to collect data and insert it into that database  **/
    boolean insertEmployee(Map<Integer, Employee> employee, int numThreads) throws SQLException, IOException;

    /** This can be used to test insertion method without any multi-threading*/
    boolean insertEmployee(Map<Integer, Employee> employee) throws SQLException, IOException;


    /** Method for getting all the employees from the database **/
    HashMap<Integer, Employee> getEmployee() throws SQLException, IOException;

    /** Method for getting the employee with specific employeeID from the database **/
    HashMap<Integer, Employee> getEmployee(int employeeID) throws SQLException, IOException;


    /** Used for updating the employee records **/
    int updateEmployee(int employeeID, String firstName);

    /** Delete the employee records from the database */
    int deleteEmployee(int employeeID);





}
