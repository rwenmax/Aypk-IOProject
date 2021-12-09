package com.sparta.iomanager.model.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final List<String> TITLES = new ArrayList<>(List.of("Mr.", "Mrs.", "Miss.","Hon.", "Prof.", "Ms.", "Drs.", "Dr."));

    public static final String VALID_NAME = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0,24}$"; //a string of 24 character is allowed and no number or special character
    public static final String VALID_INITIAL = "(^[A-Za-z])((?![ .,'-]$)[a-z .,'-]){0}$";
    public static final String CHAR_REGEX = "[A-Za-z]+";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
    public static final List<String> GENDER = new ArrayList<>(List.of("M", "F"));

}
