package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.util.Map;

public class IODriver {
    public static void main(String[] args) {
        long start = 0;
        long end = 0;

        FileFinder fileFinder = new FileFinder();

        InputManager inputManager = new InputManager();
        Map<Integer, Employee> employeeMap;
        start = System.nanoTime();
        employeeMap = inputManager.insertion(inputManager.readFile(fileFinder.findFile()));
        end = System.nanoTime();

        OutputManager.outPutResults(employeeMap);

        System.out.println("Time taken to read file: " + ((end-start)/1000000) + " ms");

        Report.runReport(inputManager);
    }
}
