package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDaoImpl implements DAO{
    /*establish connection first */

    @Override
    public boolean createTable() throws SQLException, IOException {
        PreparedStatement statement = StatementFactory.getCreateStatement();
        if (statement.executeUpdate() > 0) return true;
        return false;
    }



    @Override
    public boolean insertEmployee(Employee employee) throws SQLException, IOException {
        PreparedStatement statement = StatementFactory.getInsertStatement();
        System.out.println(statement);
        statement.setInt(1, employee.getEmployeeID());
        statement.setString(2, employee.getToc());
        statement.setString(3, employee.getFirstName());
        statement.setString(4, String.valueOf(employee.getMiddleInitial()));
        statement.setString(5, employee.getLastName());
        statement.setString(6, String.valueOf(employee.getGender()));
        statement.setString(7, employee.getEmail());
        statement.setDate(8, (Date) employee.getDob());
        statement.setDate(9, (Date) employee.getDoJ());
        statement.setInt(10, employee.getSalary());
        if (statement.executeUpdate() > 0){
            return true;
        }
        else return false;

    }





    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public Employee getEmployee(int employeeID) {
        return null;
    }

    @Override
    public int updateEmployee(Employee employee) {
        return 0;
    }

    @Override
    public int deleteEmployee(Employee employee) {
        return 0;
    }


}
