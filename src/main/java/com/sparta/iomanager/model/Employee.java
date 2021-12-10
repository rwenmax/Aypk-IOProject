/** Concrete class for the Employees extends Person*/
package com.sparta.iomanager.model;

import java.util.Date;
public class Employee extends Person {

    private int employeeID;
    private String email;
    private Date doJ;
    private int salary;

    public Employee(){
        this.employeeID = 0;
        this.email = " ";
        this.doJ = new Date();
        this.salary = 0;
    }

    public Employee(int employeeID, String email, Date doJ, int salary){
        this.employeeID = employeeID;
        this.email = email;
        this.doJ = doJ;
        this.salary = salary;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDoJ() {
        return doJ;
    }

    public void setDoJ(Date doJ) {
        this.doJ = doJ;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


}
