package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.model.util.UtilManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputManager {
    final int ID = 0, toc = 1, firstName = 2, middleInitial = 3, lastName = 4, gender = 5,
    email = 6, dob = 7, doj = 8, salary = 9;
    private Map<Integer, Employee> employeeHashMap = new HashMap<>();
    private Map<Integer, Employee> duplicateValues = new HashMap<>();
    Employee employee = new Employee();
    public String[][] readFile(String inFile){
        String[][] fields = new String[0][0];
        try (BufferedReader inp = new BufferedReader(new FileReader(inFile))){
            int lines = 0;
            while (inp.readLine() != null) lines++;
            fields = new String[--lines][10];
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            in.readLine();
            String lineOfText;
            for (int i = 0; i < lines; i++){
                lineOfText = in.readLine();
                String[] tfields = lineOfText.split(",");
                for (int j = 0; j < tfields.length; j++){
                    fields[i][j] = tfields[j];
                }
            }
            in.close();
        } catch (IOException e){
            //Log the error
            System.out.println("Unable to open file!");
            e.printStackTrace();
        }
        return fields;
    }

    public Map insertion(String[][] fields){
        Employee employee = new Employee();
        for (int i = 0; i < fields.length; i++) {
            int id = 0;
            if (UtilManager.checkInteger(fields[i][ID])) {
                id = Integer.parseInt(fields[i][ID]);
                employee.setEmployeeID(id);
            }
            if (UtilManager.checkInteger(fields[i][salary])) employee.setSalary(Integer.parseInt(fields[i][salary]));

            if (UtilManager.checkCharacter(fields[i][middleInitial]))
                employee.setMiddleInitial(fields[i][middleInitial].charAt(0));
            if (UtilManager.checkCharacter(fields[i][gender])) employee.setMiddleInitial(fields[i][gender].charAt(0));

            employee.setToc(fields[i][toc]);
            employee.setFirstName(fields[i][firstName]);
            employee.setLastName(fields[i][lastName]);
            employee.setEmail(fields[i][email]);

            try {
                employee.setDob(UtilManager.setDateFormat(fields[i][dob]));
                employee.setDoJ(UtilManager.setDateFormat(fields[i][doj]));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (employeeHashMap.containsKey(id))
            {
                duplicateValues.put(id, employee);
            }
            employeeHashMap.put(id, employee);
        }
        return employeeHashMap;
    }
}