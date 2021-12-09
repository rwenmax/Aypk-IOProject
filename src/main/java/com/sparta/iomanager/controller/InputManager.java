package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.model.util.UtilManager;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputManager{
    public class ThreadedRead implements Runnable{

        String inFile;
        int start, end;
        public ThreadedRead(String inFile, int start, int end){
            this.inFile = inFile;
            this.start = start;
            this.end = end;
        }
        @Override
        public void run() {
            try (BufferedReader inp = new BufferedReader(new FileReader(inFile))){
                inp.readLine();
                for (int i = 0; i < start; i++){
                    inp.readLine();
                }
                String lineOfText;
                for (int i = start; i < end; i++) {
                    lineOfText = inp.readLine();
                    String[] tfields = lineOfText.split(",");
                    for (int j = 0; j < tfields.length; j++) {
                        fields[i][j] = tfields[j];
                    }
                }
            } catch (IOException e){
                System.out.println("Unable to open file!");
                e.printStackTrace();
            }
            threadsFinished++;
        }
    }
    final int ID = 0, toc = 1, firstName = 2, middleInitial = 3, lastName = 4, gender = 5,
            email = 6, dob = 7, doj = 8, salary = 9;
    private Map<Integer, Employee> employeeHashMap = new HashMap<>();
    private Map<Integer, Employee> duplicateValues = new HashMap<>();

    private String[][] fields = new String[0][0];
    private int threadsFinished = 0;

    public String[][] readFile(String inFile){
        threadsFinished = 0;
        try (BufferedReader inp = new BufferedReader(new FileReader(inFile))){
            inp.readLine();
            int lines = 0;
            while (inp.readLine() != null) lines++;
            fields = new String[lines][10];
            int thread1, thread2, thread3;

            thread1 = (int)(lines*0.25);
            thread2 = (int)(lines*0.5);
            thread3 = (int)(lines*0.75);

            ThreadedRead threadedRead1 = new ThreadedRead(inFile, 0, thread1);
            Thread t1 = new Thread(threadedRead1);
            t1.start();

            ThreadedRead threadedRead2 = new ThreadedRead(inFile, thread1, thread2);
            Thread t2 = new Thread(threadedRead2);
            t2.start();

            ThreadedRead threadedRead3 = new ThreadedRead(inFile, thread2, thread3);
            Thread t3 = new Thread(threadedRead3);
            t3.start();

            ThreadedRead threadedRead4 = new ThreadedRead(inFile, thread3, lines);
            Thread t4 = new Thread(threadedRead4);
            t4.start();
        } catch (IOException e){
            e.printStackTrace();
        }
        while(threadsFinished < 4){
            System.out.println(threadsFinished);
        }
        return fields;
    }

    public class ThreadedInsertion implements Runnable{
        String[][] fieldArray = new String[0][0];
        int start, end;
        public ThreadedInsertion(String[][] fieldArray,int start, int end){
            this.fieldArray = fieldArray;
            this.start = start;
            this.end = end;
        }
        @Override
        public void run() {
            for (int i = start; i < end; i++){
                Employee employee = new Employee();
                int id = 0;
                if (UtilManager.checkInteger(fieldArray[i][ID])) {
                    id = Integer.parseInt(fieldArray[i][ID]);
                    employee.setEmployeeID(id);
                }
                if (UtilManager.checkInteger(fieldArray[i][salary])) employee.setSalary(Integer.parseInt(fieldArray[i][salary]));

                if (UtilManager.checkCharacter(fieldArray[i][middleInitial]))
                    employee.setMiddleInitial(fieldArray[i][middleInitial].charAt(0));
                if (UtilManager.checkCharacter(fieldArray[i][gender])) employee.setMiddleInitial(fieldArray[i][gender].charAt(0));

                employee.setToc(fieldArray[i][toc]);
                employee.setFirstName(fieldArray[i][firstName]);
                employee.setLastName(fieldArray[i][lastName]);
                employee.setEmail(fieldArray[i][email]);

                try {
                    employee.setDob(UtilManager.setDateFormat(fieldArray[i][dob]));
                    employee.setDoJ(UtilManager.setDateFormat(fieldArray[i][doj]));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (employeeHashMap.containsKey(id)) {
                    putDuplicateValues(id, employee);
                }
                putEmployeeHashMap(id, employee);
            }
            threadsFinished++;
        }
    }

    public Map insertion(String[][] fields) {
        threadsFinished = 0;
        int thread1, thread2, thread3;

        thread1 = (int)(fields.length*0.25);
        thread2 = (int)(fields.length*0.5);
        thread3 = (int)(fields.length*0.75);

        ThreadedInsertion threadedInsertion1 = new ThreadedInsertion(fields, 0, thread1);
        Thread t1 = new Thread(threadedInsertion1);
        t1.start();

        ThreadedInsertion threadedInsertion2 = new ThreadedInsertion(fields, thread1, thread2);
        Thread t2 = new Thread(threadedInsertion2);
        t2.start();

        ThreadedInsertion threadedInsertion3 = new ThreadedInsertion(fields, thread2, thread3);
        Thread t3 = new Thread(threadedInsertion3);
        t3.start();

        ThreadedInsertion threadedInsertion4 = new ThreadedInsertion(fields, thread3, fields.length);
        Thread t4 = new Thread(threadedInsertion4);
        t4.start();
        while(threadsFinished < 4){
            System.out.println(threadsFinished);
        }
        return employeeHashMap;
    }

    private synchronized void putEmployeeHashMap(int id, Employee employee) {
        employeeHashMap.put(id, employee);
    }

    private synchronized void putDuplicateValues(int id, Employee employee) {
        duplicateValues.put(id, employee);
    }

    public Map<Integer, Employee> getEmployeeHashMap() {
        return employeeHashMap;
    }

    public Map<Integer, Employee> getDuplicateValues() {
        return duplicateValues;
    }
}