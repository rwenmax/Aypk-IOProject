package com.sparta.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class IODriver {
    public static void readFile(String inFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(inFile))) {
            String lineOfText;
            while ((lineOfText = in.readLine()) != null) {
                System.out.println(Arrays.toString(lineOfText.split(",")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile("EmployeeRecords.csv");
    }
}
