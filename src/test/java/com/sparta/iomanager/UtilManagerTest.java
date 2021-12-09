package com.sparta.iomanager;

import com.sparta.iomanager.model.util.UtilManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UtilManagerTest {
    @DisplayName("Testing valid emails")
    @ParameterizedTest
    @ValueSource(strings = {"test@test.com", "test234@testing.co.uk", "ValidEmail@test.co", "joeBiden@test.ru"})
    public void checkValidEmail(String strings){
        assertTrue(UtilManager.emailValidation(strings));
    }

    @DisplayName("Testing invalid emails")
    @ParameterizedTest
    @ValueSource(strings = {"test#test.com", "t@testing.c", "InValidEmail@test.ff@", "joeBiden@test.whiteHouse"})
    public void checkInvalidEmail(String strings){
        assertFalse(UtilManager.emailValidation(strings));
    }

    @DisplayName("Testing valid integers")
    @ParameterizedTest
    @ValueSource(strings = {"12354", "345637", "86548949", "4967946"})
    public void checkValidInteger(String strings){
        assertTrue(UtilManager.checkInteger(strings));
    }

    @DisplayName("Testing invalid integers")
    @ParameterizedTest
    @ValueSource(strings = {"sada", "tsetst", "927342fks", "32474328947858757"})
    public void checkInvalidInteger(String strings){
        assertFalse(UtilManager.checkInteger(strings));
    }

    @DisplayName("Testing valid dates")
    @ParameterizedTest
    @ValueSource(strings = {"6/20/1958", "7/11/1975", "10/20/1960", "10/20/2003"})
    public void dateOfBirthValidation(String strings) throws ParseException {
        Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(strings);
        assertTrue(UtilManager.dobValidation(dob));
    }

    @DisplayName("Testing invalid dates")
    @ParameterizedTest
    @ValueSource(strings = {"6/20/2019", "7/11/2017", "10/20/2060", "10/20/2021"})
    public void dateOfBirthInvalid(String strings) throws ParseException {
        Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(strings);
        assertFalse(UtilManager.dobValidation(dob));
    }

    @DisplayName("Testing valid doj")
    @ParameterizedTest
    @ValueSource(strings = {"6/20/1958", "7/11/1975", "10/20/1960", "10/20/2003"})
    public void dateOfJoiningValidation(String strings) throws ParseException {
        Date doj = new SimpleDateFormat("MM/dd/yyyy").parse(strings);
        assertFalse(UtilManager.dojValidation(doj));
    }

    @DisplayName("Testing invalid doj")
    @ParameterizedTest
    @ValueSource(strings = {"6/20/2025", "7/11/2024", "10/20/2030", "10/20/2080"})
    public void dateOfJoiningInvalid(String strings) throws ParseException {
        Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(strings);
        assertTrue(UtilManager.dojValidation(dob));
    }

    @DisplayName("Testing valid char")
    @ParameterizedTest
    @ValueSource(strings = {"F", "R", "E", "D"})
    public void checkValidChars(String strings) {
        assertTrue(UtilManager.initialsValidation(strings));
    }

    @DisplayName("Testing invalid char")
    @ParameterizedTest
    @ValueSource(strings = {"1", "#", "0", "|"})
    public void checkInvalidChars(String strings) {
        assertFalse(UtilManager.initialsValidation(strings));
    }

    @DisplayName("Testing valid genders")
    @ParameterizedTest
    @CsvSource({"F, true", "M, true", "T, false"})
    public void checkGenderValidation(String s, Boolean b) {
        assertEquals(b, UtilManager.genderValidation(s));
    }

    @DisplayName("Testing valid name")
    @ParameterizedTest
    @CsvSource({"Yefri, true", "Alex, true", "Pru##thvi, false", "kamil512, false"})
    public void checkValidName(String s, Boolean b) {
        assertEquals(b, UtilManager.nameValidation(s));
    }

    @DisplayName("Testing valid name")
    @ParameterizedTest
    @CsvSource({"Drs., true", "Mr., true", "Profs., false", "Hono., false"})
    public void checkPrefixValidation(String s, Boolean b) {
        assertEquals(b, UtilManager.prefixValidation(s));
    }

}
