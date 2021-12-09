package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class IODriver {
    public static void main(String[] args) throws SQLException, IOException {
        FileFinder fileFinder = new FileFinder();

        InputManager inputManager = new InputManager();
        Map<Integer, Employee> employeeMap;
        long startRead = System.nanoTime();
        employeeMap = inputManager.insertion(inputManager.readStreamFile(fileFinder.findFile()));
        long endRead = System.nanoTime();
        long startSQL = System.nanoTime();
        new EmployeeDaoImpl().dropTable();
        new EmployeeDaoImpl().createTable();
        new EmployeeDaoImpl().insertEmployee(employeeMap);
        long endSQL = System.nanoTime();
        OutputManager.outPutResults(employeeMap);

        Report.runReport(inputManager, startRead, endRead, startSQL, endSQL);
    }
}
