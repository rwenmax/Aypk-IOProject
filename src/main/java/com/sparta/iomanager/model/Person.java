package com.sparta.iomanager.model;

import java.util.Date;

public abstract class Person {
    private String toc;
    private String firstName;
    private char middleInitial;
    private String lastName;
    private char gender;
    private Date dob;

    Person(){
        this.toc = "";
        this.firstName = " ";
        this.middleInitial = ' ';
        this.lastName = " ";
        this.gender = ' ';
        this.dob = new Date();;
    }

    Person(String toc,String firstName, char middleInitial, String lastName, char gender, Date dob){
        this.toc = toc;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
    }

    public String getToc(){
        return toc;
    }
    public void setToc(String toc){
        this.toc = toc;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public char getMiddleInitial() {
        return middleInitial;
    }
    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
}
