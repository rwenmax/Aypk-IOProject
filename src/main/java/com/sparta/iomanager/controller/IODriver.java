package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.view.FileFinder;
import com.sparta.iomanager.view.Report;

import java.util.Map;

public class IODriver {
    public static void main(String[] args) {
        FileFinder fileFinder = new FileFinder();

        InputManager inputManager = new InputManager();
        Map<Integer, Employee> employeeMap;
        employeeMap = inputManager.insertion(inputManager.readFile(fileFinder.findFile()));
        for (Map.Entry<Integer, Employee> entry : employeeMap.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue().getDob());
        }

        OutputManager.outPutResults(employeeMap);

        Report.runReport(inputManager);
    }
}
