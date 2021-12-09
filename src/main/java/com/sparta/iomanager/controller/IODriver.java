package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IODriver {
    public static void main(String[] args) throws SQLException, IOException {
        long start = 0;
        long end = 0;

        FileFinder fileFinder = new FileFinder();

        InputManager inputManager = new InputManager();
        Map<Integer, Employee> employeeMap;
        start = System.nanoTime();
        employeeMap = inputManager.insertion(inputManager.readFile(fileFinder.findFile()));
        end = System.nanoTime();

        long startTime  = System.nanoTime();
       // new EmployeeDaoImpl().dropTable();
       // new EmployeeDaoImpl().createTable();
        //new EmployeeDaoImpl().insertEmployee(employeeMap);
        System.out.println("Time taken to insertData file: " + ((System.nanoTime()-startTime)/1000000) + " ms");

        HashMap<Integer, Employee> employee2 = new EmployeeDaoImpl().getEmployee();
        OutputManager.outPutResults(employee2);



        //OutputManager.outPutResults(employeeMap);

        System.out.println("Time taken to read file: " + ((end-start)/1000000) + " ms");

        Report.runReport(inputManager);
    }
}
