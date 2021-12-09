package com.sparta.iomanager;

import com.sparta.iomanager.model.util.Constants;
import com.sparta.iomanager.model.util.UtilManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTest {
    BufferedReader file;
    final String fileName = "EmployeeRecords.csv";

    @BeforeEach
    public void setUpAll() {
        try {
            file = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the file exists
     * @param strings
     */
    @ParameterizedTest
    @DisplayName("Check if the file exists")
    @ValueSource(strings = {fileName})
    public void checkFileExists(String strings) {
        File file = new File(strings);
        assertTrue(file.exists());
    }

    /**
     * Checks the extension of the file
     * @param strings
     */
    @ParameterizedTest
    @DisplayName("Check if the file is the correct extension")
    @ValueSource(strings = {fileName})
    public void checkFileIsCorrectExtension(String strings) {
        String name = strings.substring(strings.length() - 3);
        assertEquals("csv", name);
    }

    /**
     * Reads the first line and compares to a constant of type string
     * @throws IOException
     */
    @Test
    @DisplayName("Check if first line is read")
    public void checkCSVTitles() throws IOException {
        assertEquals(Constants.CSV_HEADER, file.readLine());
    }

    /**
     * reads the first line and the length should be 10
     * @throws IOException
     */
    @Test
    @DisplayName("Checks if 10 values are read")
    public void CheckNumberOfFields() throws IOException {
        String[] firstLine = file.readLine().split(Constants.CSV_SEPARATOR);
        assertEquals(Constants.CSV_LENGTH, firstLine.length);
    }

    /**
     * Checks if the ID that is read in is of type Integer
     * @throws IOException
     */
    @Test
    @DisplayName("Checks the first id")
    public void checkIdMatch() throws IOException {
        file.readLine();
        String[] firstLine = file.readLine().split(Constants.CSV_SEPARATOR);
        String id = firstLine[Constants.ID];
        assertEquals(Integer.parseInt(id), Integer.parseInt(id));
    }

    /**
     * Reads in all the lines and matches them to a predefined list of titles
     * @throws IOException
     */
    @Test
    @DisplayName("Checks the title of courtesy")
    public void checkTocFormat() throws IOException {
        file.readLine();
        Set<String> titlesFromFile = new HashSet<>();
        String line;
        while((line = file.readLine()) != null){
            titlesFromFile.add(line.split(Constants.CSV_SEPARATOR)[Constants.TOC]);
        }
        assertTrue(Constants.TITLES.containsAll(titlesFromFile));
    }

    /**
     * Reads in all the lines and validates the name format
     * @throws IOException
     */
    @Test
    @DisplayName("Check first name contains only letters")
    public void checkFirstNameFormat() throws IOException{
        file.readLine();
        Set<String> namesFromFiles = new HashSet<>();
        String line;
        while((line = file.readLine()) != null){
            namesFromFiles.add(line.split(Constants.CSV_SEPARATOR)[Constants.FIRST_NAME]);
        }
        for(String name : namesFromFiles){
            assertTrue(UtilManager.nameValidation(name));
        }
    }

    /**
     * Reads in all the line and validates the last name format
     * @throws IOException
     */
    @Test
    @DisplayName("Check last name contains only letters")
    public void checkLastNameFormat() throws IOException{
        file.readLine();
        Set<String> namesFromFiles = new HashSet<>();
        String line;
        while((line = file.readLine()) != null){
            namesFromFiles.add(line.split(Constants.CSV_SEPARATOR)[Constants.LAST_NAME]);
        }
        for(String name : namesFromFiles){
            assertTrue(UtilManager.nameValidation(name));
        }
    }
}
