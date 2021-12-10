package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.model.util.Constants;
import com.sparta.iomanager.model.util.UtilManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class InputManager{
    /**
     * A class to make an object of for multi-threading file reading
     */
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

    /**
     * A class to make an object of for multi-threading file reading with streams
     */
    public class ThreadedStreamRead implements Runnable{

        String inFile;
        int start, end;
        public ThreadedStreamRead(String inFile, int start, int end){
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
                    final int k = i;
                    AtomicInteger counter = new AtomicInteger(-1);
                    Stream<String> stream = Pattern.compile(",").splitAsStream(lineOfText);
                    stream.forEach(e -> insertArray(k, counter.incrementAndGet(),e));
                }
            } catch (IOException e){
                System.out.println("Unable to open file!");
                e.printStackTrace();
            }
            threadsFinished++;
        }
    }

    /**
     * Hashmaps for Employee objects and their duplicates
     */
    private Map<Integer, Employee> employeeHashMap = new HashMap<>();
    private Map<Integer, Employee> duplicateValues = new HashMap<>();

    private String[][] fields = new String[0][0];
    private int threadsFinished = 0;

    // Non-stream based read file approach
    // Reads in the amount of lines, divdes the portions of the data into 4 quarters
    // These quarters are separately read by 4 threaded objects
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
            System.out.print("");
        }
        return fields;
    }

    /**
     * Stream based read file approach
     * @param inFile
     * @return
     */
    public String[][] readStreamFile(String inFile){
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

            ThreadedStreamRead threadedRead1 = new ThreadedStreamRead(inFile, 0, thread1);
            Thread t1 = new Thread(threadedRead1);
            t1.start();

            ThreadedStreamRead threadedRead2 = new ThreadedStreamRead(inFile, thread1, thread2);
            Thread t2 = new Thread(threadedRead2);
            t2.start();

            ThreadedStreamRead threadedRead3 = new ThreadedStreamRead(inFile, thread2, thread3);
            Thread t3 = new Thread(threadedRead3);
            t3.start();

            ThreadedStreamRead threadedRead4 = new ThreadedStreamRead(inFile, thread3, lines);
            Thread t4 = new Thread(threadedRead4);
            t4.start();
        } catch (IOException e){
            e.printStackTrace();
        }
        while(threadsFinished < 4){
            System.out.print("");
        }
        return fields;
    }

    /**
     * A class to make an object of for multi-threaded data insertion into the Hashmaps
     */
    public class ThreadedInsertion implements Runnable{
        String[][] fieldArray = new String[0][0];
        int start, end;
        public ThreadedInsertion(String[][] fieldArray,int start, int end){
            this.fieldArray = fieldArray;
            this.start = start;
            this.end = end;
        }

        /**
         * Loops through data and validates it before adding it to the object
         */
        @Override
        public void run() {
            for (int i = start; i < end; i++){
                Employee employee = new Employee();
                int id = 0;
                if (UtilManager.checkInteger(fieldArray[i][Constants.ID])) {
                    id = Integer.parseInt(fieldArray[i][Constants.ID]);
                    employee.setEmployeeID(id);
                }
                if (UtilManager.checkInteger(fieldArray[i][Constants.SALARY])) employee.setSalary(Integer.parseInt(fieldArray[i][Constants.SALARY]));

                if (UtilManager.initialsValidation(fieldArray[i][Constants.MIDDLE_INITIAL])) employee.setMiddleInitial(fieldArray[i][Constants.MIDDLE_INITIAL].charAt(0));
                if (UtilManager.genderValidation(fieldArray[i][Constants.GENDER])) employee.setGender(fieldArray[i][Constants.GENDER].charAt(0));

                if (UtilManager.prefixValidation(fieldArray[i][Constants.TOC])) employee.setToc(fieldArray[i][Constants.TOC]);
                if (UtilManager.nameValidation(fieldArray[i][Constants.FIRST_NAME])) employee.setFirstName(fieldArray[i][Constants.FIRST_NAME]);
                if (UtilManager.nameValidation(fieldArray[i][Constants.LAST_NAME])) employee.setLastName(fieldArray[i][Constants.LAST_NAME]);
                if (UtilManager.emailValidation(fieldArray[i][Constants.EMAIL])) employee.setEmail(fieldArray[i][Constants.EMAIL]);

                try {
                    Date date = UtilManager.setDateFormat(fieldArray[i][Constants.DOB]);
                    if (UtilManager.dobValidation(date)) employee.setDob(date);
                    date = UtilManager.setDateFormat(fieldArray[i][Constants.DOJ]);
                    if (UtilManager.dojValidation(date)) employee.setDoJ(date);
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

    /**
     * Insertion method that divdes the array matrix of strings into 4
     * Then runs 4 objects on seperate threads to insert them into the Hashmaps
     * @param fields
     * @return
     */
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
            System.out.print("");
        }
        return employeeHashMap;
    }

    /**
     * Synchronized variables for multi-threading
     * @param id
     * @param employee
     */
    private synchronized void putEmployeeHashMap(int id, Employee employee) {
        employeeHashMap.put(id, employee);
    }

    private synchronized void putDuplicateValues(int id, Employee employee) {
        duplicateValues.put(id, employee);
    }

    private synchronized void insertArray(int x, int y, String value){
        fields[x][y] = value;
    }

    /**
     * Get methods for hashmaps
     * @return
     */
    public Map<Integer, Employee> getEmployeeHashMap() {
        return employeeHashMap;
    }

    public Map<Integer, Employee> getDuplicateValues() {
        return duplicateValues;
    }
}