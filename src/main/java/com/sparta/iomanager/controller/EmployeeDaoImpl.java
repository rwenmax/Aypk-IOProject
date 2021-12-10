package com.sparta.iomanager.controller;

import com.sparta.iomanager.model.Employee;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeDaoImpl implements DAO{
    /*establish connection first */

    @Override
    public boolean createTable() throws SQLException, IOException {
        try(PreparedStatement statement = StatementFactory.getCreateStatement()){
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        //ConnectionFactory.closeConnection();
        return false;
    }

    @Override
    public void dropTable() throws SQLException, IOException {
        try(PreparedStatement statement = StatementFactory.getDropStatement()){
            statement.executeUpdate();
        }
        ConnectionFactory.closeConnection();
    }



    class ThreadedProcs implements Runnable {

        PreparedStatement stmt;
        SortedMap<Integer, Employee> data;

        public ThreadedProcs(PreparedStatement stmt,SortedMap<Integer, Employee> data) {
            this.stmt = stmt;
            this.data = data;
        }

        @Override
        public void run() {

            try {
                //PreparedStatement stmt2 = StatementFactory.getInsertStatement();
                for (Employee e : data.values()) {
                    stmt.setInt(1, e.getEmployeeID());
                    stmt.setString(2, e.getToc());
                    stmt.setString(3, e.getFirstName());
                    stmt.setString(4, String.valueOf(e.getMiddleInitial()));
                    stmt.setString(5, e.getLastName());
                    stmt.setString(6, String.valueOf(e.getGender()));
                    stmt.setString(7, e.getEmail());
                    stmt.setObject(8, e.getDob());
                    stmt.setObject(9, e.getDoJ());
                    stmt.setInt(10, e.getSalary());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                stmt.close();
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean insertEmployee(Map<Integer, Employee> employee) throws SQLException, IOException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        SortedMap<Integer, Employee> sorted = new TreeMap<>(employee);

        int f1 = (int) (sorted.size()*0.25);
        int f2 = (int) (sorted.size()*0.5);
        int f3 = (int)(sorted.size()*0.75);
        int f4 =  (sorted.size());

        System.out.println(f1 +" " + f2 +" " +f3+" "+f4 );

        SortedMap<Integer, Employee> t1 = sorted.subMap(0, f1);
        SortedMap<Integer, Employee> t2 = sorted.subMap(f1, f2);
        SortedMap<Integer, Employee> t3 = sorted.subMap(f2, f3);
        SortedMap<Integer, Employee> t4 = sorted.subMap(f3,  (sorted.size())+1);



        Runnable work1 = new ThreadedProcs(StatementFactory.getInsertStatement(),t1);
        Runnable work2 = new ThreadedProcs(StatementFactory.getInsertStatement(),t2);
        Runnable work3 = new ThreadedProcs(StatementFactory.getInsertStatement(),t3);
        Runnable work4 = new ThreadedProcs(StatementFactory.getInsertStatement(),t4);
        executor.execute(work1);
        executor.execute(work2);
        executor.execute(work3);
        executor.execute(work4);
        //ConnectionFactory.closeConnection();


        //stmt.close();
        executor.shutdown();
        while(!executor.isTerminated()){
        }
        ConnectionFactory.closeConnection();



        return false;
    }

    /*

     // This also works

    class ThreadPro implements Runnable{
        SortedMap<Integer, Employee> empy;

        ThreadPro(  SortedMap<Integer, Employee> empy){
           this.empy = empy;
        }
        @Override
        public void run() {

            try{
                PreparedStatement stmt = StatementFactory.getInsertStatement();
                for (Employee e : empy.values()) {
                    stmt.setInt(1, e.getEmployeeID());
                    stmt.setString(2, e.getToc());
                    stmt.setString(3, e.getFirstName());
                    stmt.setString(4, String.valueOf(e.getMiddleInitial()));
                    stmt.setString(5, e.getLastName());
                    stmt.setString(6, String.valueOf(e.getGender()));
                    stmt.setString(7, e.getEmail());
                    stmt.setObject(8, e.getDob());
                    stmt.setObject(9, e.getDoJ());
                    stmt.setInt(10, e.getSalary());
                    //System.out.println(stmt);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                stmt.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized boolean insertEmployee(Map<Integer, Employee> employee) throws SQLException {

        TreeMap<Integer, Employee> sorted = new TreeMap<>(employee);

        SortedMap<Integer, Employee> t1 = sorted.subMap(0, (int) (sorted.size()*0.25));
        SortedMap<Integer, Employee> t2 = sorted.subMap((int) (sorted.size()*0.25), (int) (sorted.size()*0.5 ));
        SortedMap<Integer, Employee> t3 = sorted.subMap((int) (sorted.size()*0.5 ), (int) (sorted.size()*0.75));
        SortedMap<Integer, Employee> t4 = sorted.subMap((int) (sorted.size()*0.75 ),  (sorted.size()-1));


        ThreadPro thd = new ThreadPro(t1);
        Thread thread1 = new Thread(thd);
        ConnectionFactory.closeConnection();
        thread1.start();



        ThreadPro thd2 = new ThreadPro(t2);
        Thread thread2 = new Thread(thd2);
        ConnectionFactory.closeConnection();
        thread2.start();

        ThreadPro thd3 = new ThreadPro(t3);
        Thread thread3 = new Thread(thd3);
        ConnectionFactory.closeConnection();
        thread3.start();

        ThreadPro thd4 = new ThreadPro(t4);
        Thread thread4 = new Thread(thd4);
        ConnectionFactory.closeConnection();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

*/





//This works
  /*  @Override
    public synchronized boolean insertEmployee(Map<Integer, Employee> employee) {
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
                    //System.out.println(statement);
                    statement.addBatch();
                }
                int[] recordsAdded = statement.executeBatch();
            } catch (SQLException | IOException e){
                e.printStackTrace();
            }
        System.out.println("Inserted");
        //ConnectionFactory.closeConnection();
        return false;
    }*/





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
        //ConnectionFactory.closeConnection();
        return emp2;
    }

    @Override
    public HashMap<Integer, Employee> getEmployee(int employeeID) throws SQLException, IOException {
        Employee emp = new Employee();
        HashMap<Integer, Employee> emp2 = new HashMap<>();
        try (PreparedStatement statement = StatementFactory.getEmployeeStatement()) {
            statement.setString(1, employeeID + "");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
            emp2.put(emp.getEmployeeID(), emp);
        }
        //ConnectionFactory.closeConnection();
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
