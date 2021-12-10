/** Interface for all the prepared statement, more methods can be added as per user's requirement **/
package com.sparta.iomanager.model;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface StatementFactoryInterface  {
    PreparedStatement getDatabaseDropStatement() throws SQLException, IOException;
    PreparedStatement getDatabaseStatement() throws SQLException, IOException;
    PreparedStatement getDropStatement() throws SQLException, IOException;
    PreparedStatement getInsertStatement() throws SQLException, IOException;
    PreparedStatement getAllEmployee() throws SQLException, IOException;
    PreparedStatement getCreateStatement() throws SQLException, IOException;
    PreparedStatement getEmployeeStatement() throws SQLException, IOException;
    PreparedStatement getUpdateStatement() throws SQLException, IOException;
    PreparedStatement getDeleteStatement() throws SQLException, IOException;
}