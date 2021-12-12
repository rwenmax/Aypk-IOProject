package com.sparta.iomanager.model.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final List<String> TITLES = new ArrayList<>(List.of("Mr.", "Mrs.", "Miss.","Hon.", "Prof.", "Ms.", "Drs.", "Dr."));

    public static final String VALID_NAME = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
    public static final String VALID_INITIAL = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0}$";
    public static final String CHAR_REGEX = "[A-Za-z]+";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    public static final List<String> GENDER_LIST = new ArrayList<>(List.of("M", "F"));
    public static final String CSV_HEADER = "Emp ID,Name Prefix,First Name,Middle Initial,Last Name," +
            "Gender,E Mail,Date of Birth,Date of Joining,Salary";
    public static final int CSV_LENGTH = 10;

    public static final int ID = 0;
    public static final int TOC = 1;
    public static final int FIRST_NAME = 2;
    public static final int MIDDLE_INITIAL = 3;
    public static final int LAST_NAME = 4;
    public static final int GENDER = 5;
    public static final int EMAIL = 6;
    public static final int DOB = 7;
    public static final int DOJ = 8;
    public static final int SALARY = 9;

    public static final String CSV_SEPARATOR = ",";

    public static final int MULTI_THREADED = 1;
    public static final int NON_THREADED = 2;

}
