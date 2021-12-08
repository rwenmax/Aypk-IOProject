package com.sparta.iomanager.model.util;

import java.util.Date;

public class DataValidation {

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
        if (name.matches(validName)){
            return true;
        }
        return false;
    }


    public static boolean initialsValidation(String initial){
        String validInitials = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
        if (initial == null || initial.isEmpty()) return false;
        if (initial.matches(validInitials)) return true;
        return false;
    }

    public static boolean genderValidation(String gender){
    return false;
    }

    public static boolean emailValidation(String email){
        return false;
    }


    public static boolean dOBValidation(Date dOB){

        return false;
    }

    public static boolean dateOfBirthValidation(Date dOJ){
        return false;
    }






}
