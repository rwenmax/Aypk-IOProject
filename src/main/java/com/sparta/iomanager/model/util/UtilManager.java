package com.sparta.iomanager.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import static com.sparta.iomanager.model.util.Logger.WARNING;

public class UtilManager {
    /**
     * returns true if the prefix is contained within the titles arraylist
     * @param prefix
     * @return
     */
    public static boolean prefixValidation(String prefix){
        return Constants.TITLES.contains(prefix);
    }

    /**
     * This will be used for both first name and last name
     * @param name
     * @return
     */
    public static boolean nameValidation(String name){
        if (name == null || name.isEmpty()) return false;
        return name.matches(Constants.VALID_NAME);
    }

    /**
     * returns true if the initial is valid
     * @param initial
     * @return
     */
    public static boolean initialsValidation(String initial){
        if (initial == null || initial.isEmpty()) return false;
        return initial.matches(Constants.VALID_INITIAL);
    }

    /**
     * returns true if the gender matches "M" or "F"
     * @param gender
     * @return
     */
    public static boolean genderValidation(String gender){
        return Constants.GENDER_LIST.contains(gender);
    }

    /**
     * returns true if the email has a valid format
     * @param email
     * @return
     */
    public static boolean emailValidation(String email) {
        Pattern pat = Pattern.compile(Constants.EMAIL_REGEX);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    /**
     * calculatePeriod Helper Method
     * @param d
     * @return
     */
    private static Period calculatePeriod(Date d){
        LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        return period;
    }

    /**
     * returns true if min age is 18
     * @param dob date of birth
     * @return true/false
     */
    public static boolean dobValidation(Date dob){
        return calculatePeriod(dob).getYears() >= 18;
    }

    /**
     * returns false if the date is in the future
     * @param doj
     * @return
     */
    public static boolean dojValidation(Date doj){
        return calculatePeriod(doj).getYears() < 0;
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
        return character.matches(Constants.CHAR_REGEX);
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
