package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DAO {

    //CREATE table and INSERT
    boolean insertEmployee(Map<Integer, Employee> employee) throws SQLException, IOException;
    //SELECT ALL
    HashMap<Integer, Employee> getEmployee() throws SQLException, IOException;
    //SELECT
    HashMap<Integer, Employee> getEmployee(int employeeID) throws SQLException, IOException;
    //UPDATE
    int updateEmployee(Employee employee);
    //DELETE
    int deleteEmployee(Employee employee);

    boolean createTable() throws SQLException, IOException;

    void dropTable() throws SQLException, IOException;



}
