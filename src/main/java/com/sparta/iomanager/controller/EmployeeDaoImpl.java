package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements DAO{
    /*establish connection first */

    @Override
    public boolean createTable() throws SQLException, IOException {
        PreparedStatement statement = StatementFactory.getCreateStatement();
        if (statement.executeUpdate() > 0) return true;
        return false;
    }

    @Override
    public void dropTable() throws SQLException, IOException {
        PreparedStatement statement = StatementFactory.getDropStatement();
        statement.executeUpdate();
    }


    @Override
    public boolean insertEmployee(Map<Integer, Employee> employee) throws SQLException, IOException {
        PreparedStatement statement = StatementFactory.getInsertStatement();
        boolean success = false;
            for (Employee e : employee.values()) {
                statement.setObject(1, e.getEmployeeID());
                statement.setObject(2, e.getToc());
                statement.setObject(3, e.getFirstName());
                statement.setObject(4, String.valueOf(e.getMiddleInitial()));
                statement.setObject(5, e.getLastName());
                statement.setObject(6, String.valueOf(e.getGender()));
                statement.setObject(7, e.getEmail());
                statement.setObject(8, e.getDob());
                statement.setObject(9, e.getDoJ());
                statement.setObject(10, e.getSalary());
                System.out.println(statement);
                statement.executeUpdate();
            }

        StatementFactory.closeStatement();
        ConnectionFactory.closeConnection();
        return false;

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
