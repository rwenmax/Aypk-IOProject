package com.sparta.iomanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IOManagerTest {
    @Test
    @DisplayName("Check if file exists")
    public void checkFileExists(String f){
        File file = new File(f);
        assertTrue(file.exists());
    }

    @Test
    public void checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9+&*-]+(?:\\."+
                "[a-zA-Z0-9+&-]+)@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(email);
        assertTrue(pat.matcher(email).matches());
    }
}
