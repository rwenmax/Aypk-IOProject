package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.model.util.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class InputManager {
    final int ID = 0, toc = 1, firstName = 2, middleInitial = 3, lastName = 4, gender = 5,
    email = 6, dob = 7, doj = 8, salary = 9;

    public Map readFile(String inFile){
        Map<Integer, Employee> employeeHashMap = new HashMap<>();
        try (BufferedReader in = new BufferedReader(new FileReader(inFile))){
            in.readLine();
            String lineOfText;
            while((lineOfText = in.readLine()) != null){
                String[] fields = lineOfText.split(",");
                // check if correct format
                Employee employee = new Employee();
                try {
                    int id = Integer.parseInt(fields[ID]);
                    employee.setEmployeeID(id);
                    employee.setSalary(Integer.parseInt(fields[salary]));

                    employee.setMiddleInitial(fields[middleInitial].charAt(0));
                    employee.setGender(fields[gender].charAt(0));

                    employee.setToc(fields[toc]);
                    employee.setFirstName(fields[firstName]);
                    employee.setLastName(fields[lastName]);
                    employee.setEmail(fields[email]);

                    employee.setDob(new SimpleDateFormat("MM/dd/yyyy").parse(fields[dob]));
                    employee.setDoJ(new SimpleDateFormat("MM/dd/yyyy").parse(fields[doj]));

                    employeeHashMap.put(id, employee);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    Logger.logger.warn(Logger.WARNING, e);
                } catch (ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                    Logger.logger.warn(Logger.WARNING, e);
                } catch (ParseException e){
                    e.printStackTrace();
                    Logger.logger.warn(Logger.WARNING, e);
                }
            }
        } catch (IOException e){
            //Log the error
            System.out.println("Unable to open file!");
            Logger.logger.warn(Logger.WARNING, e);
            e.printStackTrace();
        }
        return employeeHashMap;
    }
}
