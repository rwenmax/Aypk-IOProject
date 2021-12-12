package com.sparta.iomanager.controller;
import com.sparta.iomanager.model.ConnectionFactory;
import com.sparta.iomanager.model.DAO;
import com.sparta.iomanager.model.Employee;
import com.sparta.iomanager.model.StatementFactory;
import com.sparta.iomanager.model.util.Logger;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeDaoImpl implements DAO {
    /*establish connection first */

    /** Drops the database*/
    @Override
    public void dropDatabase() {
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement statement = getStmt.getDatabaseDropStatement()){
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        }  finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
    }

    /** Creates the database*/
    @Override
    public void createDatabase() {
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement statement = getStmt.getDatabaseStatement()){
            statement.executeUpdate();
        }catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        }  finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
    }


    /** Creates the table*/
    @Override
    public boolean createTable() {
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement statement = getStmt.getCreateStatement()){
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        }  finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
        return false;
    }

    /**Dropping the table*/
    @Override
    public void dropTable() {
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement statement = getStmt.getDropStatement()){
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        }  finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
    }

    /** Multi-threaded process to insert the data into the database takes around 2500ms to insert database of 130998 records, compare to not threaded taking 290405ms to insert 65499 records */
    static class ThreadProcess implements Runnable {
        HashMap<Integer, Employee> data;

        public ThreadProcess(HashMap<Integer, Employee> data) {
            this.data = data;
        }
        @Override
        public void run() {
            try {
                StatementFactory getStmt = new StatementFactory();
                PreparedStatement stmt2 = getStmt.getInsertStatement();
                for (Employee e : data.values()) {
                    stmt2.setInt(1, e.getEmployeeID());
                    stmt2.setString(2, e.getToc());
                    stmt2.setString(3, e.getFirstName());
                    stmt2.setString(4, String.valueOf(e.getMiddleInitial()));
                    stmt2.setString(5, e.getLastName());
                    stmt2.setString(6, String.valueOf(e.getGender()));
                    stmt2.setString(7, e.getEmail());
                    stmt2.setObject(8, e.getDob());
                    stmt2.setObject(9, e.getDoJ());
                    stmt2.setInt(10, e.getSalary());
                    stmt2.addBatch();
                }
                stmt2.executeBatch();
                stmt2.close();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            } finally {
                try {
                    ConnectionFactory.closeConnection();
                } catch (SQLException | IOException e) {
                    Logger.logger.error(e.toString());
                }
            }
        }
    }

    /** Multi-threaded insertion */
    @Override
    public boolean insertEmployee(Map<Integer, Employee> employee, int numThreads) {
        if (numThreads == 0 || numThreads >= 100){
            numThreads = 10;
        }
        /* Convert HashMap into SortedMap inorder to split the dataset into 4 parts for multi-threading to occur */
        ExecutorService executor = Executors.newFixedThreadPool(numThreads); //Create thread pool of 10 threads, can be configured!

        /** Convert the hashmap Keys int arraylist for spliting them into 4 different parts */
        ArrayList<Integer> list = employee.keySet().stream().collect(Collectors.toCollection(ArrayList::new));


        //get the list size
        int sizeOne = (int)(list.size()*0.25);
        int sizeTwo = (int)(list.size()*0.5);
        int sizeThree = (int)(list.size()*0.75);
        int sizeFour = list.size();
        int counter = 0;

        /** Setting up the 4 hashmap to be used*/
        HashMap<Integer, Employee> map1 = new HashMap<>();
        HashMap<Integer, Employee> map2 = new HashMap<>();
        HashMap<Integer, Employee> map3 = new HashMap<>();
        HashMap<Integer, Employee> map4 = new HashMap<>();
        /** Iterate over the employee map, and splits it into 4 part*/
        for (Map.Entry<Integer, Employee> e : employee.entrySet()){
            if (counter < sizeOne){
                map1.put(e.getKey(), e.getValue());
                counter++;
            }
            else if (counter < sizeTwo){
                map2.put(e.getKey(), e.getValue());
                counter++;
            }
            else if (counter < sizeThree){
                map3.put(e.getKey(), e.getValue());
                counter++;
            }
            else if (counter <= sizeFour){
                map4.put(e.getKey(), e.getValue());
                counter++;
            }
        }

        /* Adding the work to the pool */
        Runnable work1 = new ThreadProcess(map1);
        Runnable work2 = new ThreadProcess(map2);
        Runnable work3 = new ThreadProcess(map3);
        Runnable work4 = new ThreadProcess(map4);
        executor.execute(work1);
        executor.execute(work2);
        executor.execute(work3);
        executor.execute(work4);


        //stmt.close();
        executor.shutdown();
        while(!executor.isTerminated()){
        }
        try {
            ConnectionFactory.closeConnection();
        } catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        }
        return false;
    }

    /** Uncomment this part of the code to use the Threaded based query */
    /*

     // This also works with tradtitional thread

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


    /**
     * This method inputs data into the database without any multi-threading or batch rewriting.
     */
    @Override
    public  boolean insertEmployee(Map<Integer, Employee> employee) {
        StatementFactory getStmt = new StatementFactory();
            try (PreparedStatement statement = getStmt.getInsertStatementN()) {
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
            }finally {
                try {
                    ConnectionFactory.closeConnection();
                } catch (SQLException | IOException e) {
                    Logger.logger.error(e.toString());
                }
            }
        System.out.println("Inserted");
        return true;
    }




    /** Gets all the employees from the database */
    @Override
    public HashMap<Integer, Employee> getEmployee() {
        StatementFactory getStmt = new StatementFactory();
        HashMap<Integer, Employee> emp2 = new HashMap<>();
        try (PreparedStatement statement = getStmt.getAllEmployee())
        {
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
        }catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
        return emp2;
    }
    /** Gets the employee record by id only */
    @Override
    public HashMap<Integer, Employee> getEmployee(int employeeID) {
        StatementFactory getStmt = new StatementFactory();
        Employee emp = new Employee();
        HashMap<Integer, Employee> emp2 = new HashMap<>();
        try (PreparedStatement statement = getStmt.getEmployeeStatement()) {
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
        }catch (SQLException | IOException e) {
            Logger.logger.error(e.toString());
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
        emp2.put(emp.getEmployeeID(), emp);
        return emp2;
    }

    /** To be added to backlog fot next sprint*/
    @Override
    public int updateEmployee(int employeeID, String firstName) {
        int updatedRecord = 0;
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement stmt = getStmt.getUpdateStatement()) {
            stmt.setString(1, firstName);
            stmt.setInt(2, employeeID);
            updatedRecord = stmt.executeUpdate();
        } catch ( SQLException |  IOException e){
            Logger.logger.error(e.toString());
        }finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        return updatedRecord;
    }
    }



    /** To be added to backlog fot next sprint*/
    @Override
    public int deleteEmployee(int employeeID) {
        int deletedRecord = 0;
        StatementFactory getStmt = new StatementFactory();
        try(PreparedStatement stmt = getStmt.getDeleteStatement()){
            stmt.setInt(1, employeeID);
            deletedRecord = stmt.executeUpdate();
        }catch ( SQLException |  IOException e){
            Logger.logger.error(e.toString());
        }finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException | IOException e) {
                Logger.logger.error(e.toString());
            }
        }
        return deletedRecord;
    }
}
