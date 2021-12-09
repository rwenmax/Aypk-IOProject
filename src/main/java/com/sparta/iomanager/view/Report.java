package com.sparta.iomanager.view;

import com.sparta.iomanager.controller.InputManager;

public class Report {
    public static void runReport(InputManager inputManager, long startRead, long endRead, long startSQL, long endSQL){
        System.out.println("\n || REPORT || \n\nThe List Size of Employees: " +
                inputManager.getEmployeeHashMap().size() +
                "\nThe List Size of Duplicates: " +
                inputManager.getDuplicateValues().size() +
                "\nTime taken to read the file: " +
                (endRead - startRead)/1000000 + " ms" +
                "\nTime taken to write the file into MySQL: " +
                (endSQL - startSQL)/1000000 + " ms"
        );
    }
}