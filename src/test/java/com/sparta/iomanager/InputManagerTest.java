package com.sparta.iomanager;

import com.sparta.iomanager.controller.IODriver;
import com.sparta.iomanager.controller.InputManager;
import com.sparta.iomanager.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTest {
    String filename = "EmployeesRecord.csv";
    InputManager inputManager = new InputManager();

    @ParameterizedTest
    @DisplayName("Check if the file exists")
    @ValueSource(strings = {"EmployeeRecords.csv"})
    public void checkFileExists(String strings){
        File file = new File(strings);
        assertTrue(file.exists());
    }

    @ParameterizedTest
    @DisplayName("Check if the file is the correct extension")
    @ValueSource(strings = {"EmployeeRecords.csv"})
    public void checkFileIsCorrectExtension(String strings){
        String name = strings.substring(strings.length() - 3);
        assertEquals("csv", name);
    }

    @ParameterizedTest
    @DisplayName("Check if first line is read")
    @ValueSource(strings = {"EmployeeRecords.csv"})
    //Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary
    public void checkCSVTitles(String filename){
        String expected = "Emp ID,Name Prefix,First Name,Middle Initial,Last Name," +
                          "Gender,E Mail,Date of Birth,Date of Joining,Salary";
        BufferedReader bufferedReader = null;
        String firstLine = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            firstLine = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(expected, firstLine);
    }

    @Test
    @DisplayName("Checks if 10 values are read")
    public void CheckNumberOfFields(){
        // take in the first 3-4 lines, split"," -> size == 10
        //check the map -> readFile (filenmae) -> map
        // k -> vaue size == 10
//        inputManager.readFile(filename).get(1).toString();
    }

    @Test
    @DisplayName("Break Kamil's readFile method")
    public void testReadFileReturnsMap(String filename){
        HashMap expected = new HashMap();
//        expected = inputManager.readFile(filename);
    }

    @Test
    @DisplayName("Checks the first id")
    public void checkIdMatch(){
        inputManager.readFile(filename).get(100);
    }

    @Test
    @DisplayName("Checks if the id stored is the correct format")
    public void checkIdFormat(){

    }

    @Test
    @DisplayName("Checks the title of courtesy") // String array of titles and check
    public void checkTocFormat(){

    }

    @Test
    @DisplayName("") // checks if only 1 char middle name
    public void checkMiddleNameChar(){

    }

    @Test
    @DisplayName("") // check email is valid
    public void checkNameFormat(){

    }

    @Test
    @DisplayName("") // check date from the file
    public void checkDateFormat(){

    }





}
