package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DAO {

    //CREATE table and INSERT
    boolean insertEmployee(Employee employee) throws SQLException, IOException;
    //SELECT ALL
    List<Employee> getAllEmployee();
    //SELECT
    Employee getEmployee(int employeeID);
    //UPDATE
    int updateEmployee(Employee employee);
    //DELETE
    int deleteEmployee(Employee employee);

    boolean createTable() throws SQLException, IOException;



}
