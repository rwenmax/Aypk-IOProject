package com.sparta.iomanager.view;

import com.sparta.iomanager.controller.InputManager;

public class Report {
    public static void runReport(InputManager inputManager){
        System.out.println("\n || REPORT || \n\n The List Size of Employees: " + inputManager.getEmployeeHashMap().size()
         + "\n The List Size of Duplicates: " + inputManager.getDuplicateValues().size());
    }
}
