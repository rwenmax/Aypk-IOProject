package com.sparta.iomanager;

import java.io.*;

public class IOManager {
    public static void readFile(String inFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(inFile))){
            String lineOfText;
            while( (lineOfText = in.readLine()) != null){
                System.out.println(lineOfText);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        readFile("README.md");
    }
}
