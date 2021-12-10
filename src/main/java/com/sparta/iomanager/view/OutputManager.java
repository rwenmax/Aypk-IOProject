package com.sparta.iomanager.view;

import com.sparta.iomanager.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputManager {

    //Printer
    public static void outPutResults(Map<Integer, Employee> employeeList){
        List<String> employees = new ArrayList();
        for( Employee emp: employeeList.values()){
            List<String> singleEmployee = new ArrayList<>();
            singleEmployee.add(emp.getEmployeeID() + " "
                    + emp.getToc() +" "
                    + emp.getFirstName() + " "
                    + emp.getMiddleInitial() +  " "
                    + emp.getLastName() + " "
                    + emp.getGender() + " "
                    + emp.getEmail() + " "
                    + emp.getDob() + " "
                    + emp.getDoJ() + " "
                    + emp.getSalary());

            employees.add(singleEmployee+"");
        }

        for (String eml : employees){
            System.out.println(eml);
        }
    }
}