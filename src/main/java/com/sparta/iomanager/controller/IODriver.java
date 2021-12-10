package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IODriver {
    static long startRead;
    static long endRead;
    static long startSQL;
    static long endSQL;
    public static void main(String[] args) throws SQLException, IOException {
        InputManager inputManager = new InputManager();
        Map<Integer, Employee> employeeMap;

        employeeMap = read(inputManager);
        write(employeeMap);

        //OutputManager.outPutResults(employeeMap);
        //OutputManager.outPutResults(new EmployeeDaoImpl().getEmployee());

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
        startRead = System.nanoTime();
        Map<Integer, Employee> map = inputManager.insertion(inputManager.readStreamFile(fileFinder.findFile()));
        endRead = System.nanoTime();
        return map;
    }

    /**
     * Takes the map and inserts the data into a SQL Database by creating the table
     * Then inserting all the objects
     * @param employeeMap
     * @throws SQLException
     * @throws IOException
     */
    private static void write(Map<Integer, Employee> employeeMap) throws SQLException, IOException{
        startSQL = System.nanoTime();
        new EmployeeDaoImpl().dropTable();
        new EmployeeDaoImpl().createTable();
        new EmployeeDaoImpl().insertEmployee(employeeMap);
        endSQL = System.nanoTime();
    }
}
