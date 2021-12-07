package com.sparta.iomanager.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilManager {
    public static boolean checkInteger(String num){
        try {
            int number = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkCharacter(String cha){
        try {
            char charac = cha.charAt(0);
            return true;
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
    }

    public static Date setDateFormat(String dateS){
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(dateS);
            return date;
        } catch (ParseException e){
            return null;
        }
    }
}
