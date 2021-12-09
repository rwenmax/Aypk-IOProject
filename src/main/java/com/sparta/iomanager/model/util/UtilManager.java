package com.sparta.iomanager.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.sparta.iomanager.model.util.Logger.WARNING;

public class UtilManager {
    static List<String> titles = new ArrayList<>(List.of("Mr.", "Mrs.", "Miss.","Hon.", "Prof.", "Hons.", "Ms.", "Drs."));

    /**
     * returns true if the prefix is contained within the titles arraylist
     * @param prefix
     * @return
     */
    public static boolean prefixValidation(String prefix){
        return titles.contains(prefix);
    }

    /**
     * This will be used for both first name and last name
     * @param name
     * @return
     */
    public static boolean nameValidation(String name){
        String validName = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"; //a string of 24 character is allowed and no number or special character
        if (name == null || name.isEmpty()) return false;
        return name.matches(validName);
    }

    /**
     * returns true if the initial is valid
     * @param initial
     * @return
     */
    public static boolean initialsValidation(String initial){
        String validInitials = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0}$";
        if (initial == null || initial.isEmpty()) return false;
        return initial.matches(validInitials);
    }

    /**
     * returns true if the gender matches "M" or "F"
     * @param gender
     * @return
     */
    public static boolean genderValidation(String gender){
        return (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"));
    }

    /**
     * returns true if the email has a valid format
     * @param email
     * @return
     */
    public static boolean emailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    /**
     * returns true if min age is 18
     * @param dob date of birth
     * @return true/false
     */
    public static boolean dobValidation(Date dob){
        LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        System.out.println(period.getYears());
        return period.getYears() >= 18;
    }

    /**
     * returns false if the date is in the future
     * @param doj
     * @return
     */
    public static boolean dojValidation(Date doj){
        LocalDate date = doj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        return period.getYears() < 0;
    }

    /**
     * returns true or false if the string can be parsed as an integer
     * @param num
     * @return
     */
    public static boolean checkInteger(String num){
        try {
            int number = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e){
            Logger.logger.warn(WARNING, e);
            return false;
        }
    }

    /**
     * if true it matches. if false doesnt match
     * @param character
     * @return
     */
    public static boolean checkCharacter(String character){
        String charRegex = "[A-Za-z]+";
        return character.matches(charRegex);
    }

    /**
     * returns the string as a Date
     * @param dateS string as date
     * @return Date object
     */
    public static Date setDateFormat(String dateS){
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateS);
            return date;
        } catch (ParseException e){
            return null;
        }
    }
}
