package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IODriver {
    static long startRead;
    static long endRead;
    static long startSQL;
    static long endSQL;
    public static void main(String[] args) throws SQLException, IOException {
        InputManager inputManager = new InputManager();
        UserInput uI = new UserInput();
        Map<Integer, Employee> employeeMap;

        employeeMap = read(inputManager);
        databaseBuild();
        List<Integer> optionsSelected = uI.userInput();
        if (optionsSelected.get(0) == 1){
            //multi-threaded
            write(employeeMap,optionsSelected.get(1));
        }
        else{
            writeN(employeeMap);
        }


        //Optional console printing methods
        //OutputManager.outPutResults(employeeMap);
        //OutputManager.outPutResults(new EmployeeDaoImpl().getEmployee());


        //delete a record based on the id
        //deleteRecord();
        Report.runReport(inputManager, startRead, endRead, startSQL, endSQL);
    }

    /**
     * Runs the UI that allows the user to find the file they want read
     * Then reads in the file and creates a map of employees from it
     * With the Employee ID as the key
     * @param inputManager
     * @return
     */
    private static Map<Integer, Employee> read(InputManager inputManager){
        FileFinder fileFinder = new FileFinder();
        String fileName = fileFinder.findFile();
        startRead = System.nanoTime();
        Map<Integer, Employee> map = inputManager.insertion(inputManager.readStreamFile(fileName));
        endRead = System.nanoTime();
        return map;
    }

    /**
     * Creates the database using the connection.properties file,
     * Run this if you would like to create brand-new database named "Employees"
     */
    private static void databaseBuild(){
        new EmployeeDaoImpl().dropDatabase();
        new EmployeeDaoImpl().createDatabase();
    }

    /**
     * Takes the map and inserts the data into a SQL Database by creating the table
     * Then inserting all the objects
     * @param employeeMap
     * @throws SQLException
     * @throws IOException
     */
    private static void write(Map<Integer, Employee> employeeMap, int threads) {
        startSQL = System.nanoTime();
        new EmployeeDaoImpl().dropTable();
        new EmployeeDaoImpl().createTable();
        new EmployeeDaoImpl().insertEmployee(employeeMap, threads);  /* Provide number of threads to be used */
        endSQL = System.nanoTime();
    }


    /**
     * Used for normal tpye insertion into the database, without multi-threaded.
     * @param employeeMap
     */
    private static void writeN(Map<Integer, Employee> employeeMap) {
        startSQL = System.nanoTime();
        new EmployeeDaoImpl().dropTable();
        new EmployeeDaoImpl().createTable();
        new EmployeeDaoImpl().insertEmployee(employeeMap);  /* Provide number of threads to be used */
        endSQL = System.nanoTime();
    }


    /**
     * Gets the employee records from the database based on the employeeID
     */
    private static void getRecord(){
        new EmployeeDaoImpl().getEmployee(65499);
    }



    /**
     * Can be used to update a record of first name given the employeeID
     */
    private static void updateRecord(){
        new EmployeeDaoImpl().updateEmployee(65499,"James");
    }


    /**
     * Can be used to delete a record from employee's table given the employeeID
     */
    private static void deleteRecord(){
        new EmployeeDaoImpl().deleteEmployee(65499);
    }







}
