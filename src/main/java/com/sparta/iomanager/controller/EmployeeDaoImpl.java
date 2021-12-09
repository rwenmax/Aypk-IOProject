package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

            try (PreparedStatement statement = StatementFactory.getInsertStatement()) {
                for (Employee e : employee.values()) {
                    //they were all .setObject
                    statement.setInt(1, e.getEmployeeID());
                    statement.setString(2, e.getToc());
                    statement.setString(3, e.getFirstName());
                    statement.setString(4, String.valueOf(e.getMiddleInitial()));
                    statement.setString(5, e.getLastName());
                    statement.setString(6, String.valueOf(e.getGender()));
                    statement.setString(7, e.getEmail());
                    statement.setObject(8, e.getDob());
                    statement.setObject(9, e.getDoJ());
                    statement.setInt(10, e.getSalary());
                    System.out.println(statement);
                    statement.addBatch();
                }
                int[] recordsAdded = statement.executeBatch();
            } catch (SQLException | IOException e){
                e.printStackTrace();
            }
        ConnectionFactory.closeConnection();
        return false;
    }





    @Override
    public HashMap<Integer, Employee> getEmployee() throws SQLException, IOException {

        HashMap<Integer, Employee> emp2 = new HashMap<>();
        try (PreparedStatement statement = StatementFactory.getAllEmployee()){
            ResultSet rS = statement.executeQuery();
            while (rS.next()){
                Employee emp = new Employee();
                emp.setEmployeeID(rS.getInt("employeeID"));
                emp.setToc(rS.getString(2));
                emp.setFirstName(rS.getString(3));
                emp.setMiddleInitial((rS.getString(4).charAt(0)));
                emp.setLastName(rS.getString(5));
                emp.setGender(rS.getString(6).charAt(0));
                emp.setEmail(rS.getString(7));
                emp.setDob(rS.getDate(8));
                emp.setDoJ(rS.getDate(9));
                emp.setSalary(rS.getInt(10));
                emp2.put(rS.getInt("employeeID"),emp);
            }
            //emp2.put(emp.getEmployeeID(),emp);
        }
        return emp2;
    }

    @Override
    public HashMap<Integer, Employee> getEmployee(int employeeID) throws SQLException, IOException {
        Employee emp = new Employee();
        HashMap<Integer, Employee> emp2 = new HashMap<>();
        try (PreparedStatement statement = StatementFactory.getEmployeeStatement()){
            statement.setString(1, employeeID+"");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                emp.setEmployeeID(resultSet.getInt("employeeID"));
                emp.setToc(resultSet.getString("name_prefix"));
                emp.setFirstName(resultSet.getString("first_name"));
                emp.setMiddleInitial((resultSet.getString("middle_initial").charAt(0)));
                emp.setLastName(resultSet.getString("last_name"));
                emp.setGender(resultSet.getString("gender").charAt(0));
                emp.setEmail(resultSet.getString("email"));
                emp.setDob(resultSet.getDate("date_of_birth"));
                emp.setDoJ(resultSet.getDate("date_of_joining"));
                emp.setSalary(resultSet.getInt("salary"));
            }
            emp2.put(emp.getEmployeeID(),emp);
        }
      return emp2;
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
