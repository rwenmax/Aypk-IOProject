package com.sparta.iomanager.model.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DataValidation {
    ArrayList<String> titles = new ArrayList<>(List.of("Mr", "Mrs", "Miss"," "));


    public static boolean prefixValidation(String prefix){
        return false;
    }


    /* This could be used for employee id and salary checker*/
    public static boolean employeeIDValidation(int emp_id){
        return false;
    }


    /* This will be used for both first name and last name*/
    public static boolean nameValidation(String name){
        String validName = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"; //a string of 24 character is allowed and no number or special character
        if (name == null || name.isEmpty()) return false;
        return name.matches(validName);
    }


    public static boolean initialsValidation(String initial){
        String validInitials = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0}$";
        if (initial == null || initial.isEmpty()) return false;
         return initial.matches(validInitials);
    }

    public static boolean genderValidation(String gender){
    return false;
    }

    public static boolean emailValidation(String email){
            String emailRegex = "^[a-zA-Z0-9+&*-]+(?:\\."
                    + "[a-zA-Z0-9+&-]+)@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    +"A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (email == null) {
                return false;
            }
            return pat.matcher(email).matches();
    }


    public static boolean dOBValidation(Date dOB){

        return false;
    }

    public static boolean dateOfBirthValidation(Date dOJ){
        return false;
    }






}
