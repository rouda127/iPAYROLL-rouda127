package se2203b.ipayroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeductionTableAdapter implements DataStore {

    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public DeductionTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Deduction");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            }
        }

        try {
            // Create the table
            stmt.execute("CREATE TABLE Deduction ("
                    + "deductionId VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "amount DOUBLE, "
                    + "percentOfEarnings DOUBLE, "
                    + "upperLimit DOUBLE, "
                    + "startDate DATE, "
                    + "stopDate DATE, "
                    + "deductionSource VARCHAR(9) REFERENCES DeductionSource(code),"
                    + "employee VARCHAR(9) REFERENCES Employee(id) "
                    + ")");
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
        connection.close();
    }

    @Override
    public void addNewRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        try {
            String command = "INSERT INTO Deduction (deductionId, amount, percentOfEarnings, upperLimit, startDate, stopDate, deductionSource, employee) "
                    + "VALUES ('"
                    + deduction.getDeductionID() + "', "
                    + deduction.getAmount() + ", "
                    + deduction.getPercentOfEarnings() + ", "
                    + deduction.getUpperLimit() + ", "
                    + (deduction.getStartDate() == null? null : "'" + deduction.getStartDate() + "'") + ", "
                    + (deduction.getStopDate() == null? null : "'" + deduction.getStopDate() + "'") + ", "
                    + (deduction.getDeductionSource().getCode() == null? null : "'" + deduction.getDeductionSource().getCode() + "' ") + ", "
                    + (deduction.getEmployee().getID() == null? null : "'" + deduction.getEmployee().getID() + "' ")
                    +  " )";
            stmt.executeUpdate(command);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Deduction deduction = (Deduction) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        // Get the user account name if exists
        String command = null;

        command = "UPDATE Deduction "
                + "SET amount = " + deduction.getAmount() + ", "
                + "percentOfEarnings = " + deduction.getPercentOfEarnings() + ", "
                + "upperLimit = " + deduction.getUpperLimit() + ", "
                + "startDate = '" + deduction.getStartDate() + "', "
                + "stopDate = '" + deduction.getStopDate() + "', "
                + "deductionSource = " + (deduction.getDeductionSource().getCode() == null? null : "'" + deduction.getDeductionSource().getCode() + "' ") + ", "
                + "employee = " + (deduction.getEmployee().getID() == null? null : "'" + deduction.getEmployee().getID() + "' ")
                + " WHERE deductionId = "
                + "'" + deduction.getDeductionID() + "'";

        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Deduction WHERE employee = '" + key + "'");
        connection.close();
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {

    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT deductionId FROM Deduction";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);

        while (rs.next()) {
            list.add(rs.getString(1));
        }
        connection.close();
        return list;
    }

    @Override
    public Object findOneRecord(String key) throws SQLException {
        Deduction deduction = new Deduction();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM Deduction WHERE deductionId = '" + key + "'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            deduction.setDeductionID(rs.getString("deductionId"));
            deduction.setAmount(rs.getDouble("amount"));
            deduction.setPercentOfEarnings(rs.getDouble("percentOfEarnings"));
            deduction.setUpperLimit(rs.getDouble("upperLimit"));
            deduction.setStartDate(rs.getDate("startDate"));
            deduction.setStopDate(rs.getDate("stopDate"));
            if (rs.getString("deductionSource") != null) {
                DeductionSource deductionSource = new DeductionSource(rs.getString("deductionSource"), "");
                deduction.setDeductionSource(deductionSource);
            }
            if (rs.getString("employee") != null) {
                Employee employee = new Employee();
                employee.setID(rs.getString("employee"));
                deduction.setEmployee(employee);
            }
        }
        connection.close();
        return deduction;
    }

    @Override
    public Object findOneRecord(Object referencedObject) throws SQLException {
        return null;

    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet rs;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM Deduction";

            // Execute the statement and return the result
            rs = stmt.executeQuery(command);

            while (rs.next()) {
                Deduction deduction = new Deduction();
                deduction.setDeductionID(rs.getString("deductionId"));
                deduction.setAmount(rs.getDouble("amount"));
                deduction.setPercentOfEarnings(rs.getDouble("percentOfEarnings"));
                deduction.setUpperLimit(rs.getDouble("upperLimit"));
                deduction.setStartDate(rs.getDate("startDate"));
                deduction.setStopDate(rs.getDate("stopDate"));
                if (rs.getString("deductionSource") != null) {
                    DeductionSource deductionSource = new DeductionSource(rs.getString("deductionSource"), "");
                    deduction.setDeductionSource(deductionSource);
                }
                if (rs.getString("employee") != null) {
                    Employee employee = new Employee();
                    employee.setID(rs.getString("employee"));
                    deduction.setEmployee(employee);
                }

                list.add(deduction);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> getAllRecords(Object id) throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet rs;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM Deduction WHERE employee = '" + id + "'";

            // Execute the statement and return the result
            rs = stmt.executeQuery(command);

            while (rs.next()) {
                Deduction deduction = new Deduction();
                deduction.setDeductionID(rs.getString("deductionId"));
                deduction.setAmount(rs.getDouble("amount"));
                deduction.setPercentOfEarnings(rs.getDouble("percentOfEarnings"));
                deduction.setUpperLimit(rs.getDouble("upperLimit"));
                deduction.setStartDate(rs.getDate("startDate"));
                deduction.setStopDate(rs.getDate("stopDate"));
                if (rs.getString("deductionSource") != null) {
                    DeductionSource deductionSource = new DeductionSource(rs.getString("deductionSource"), "");
                    deduction.setDeductionSource(deductionSource);
                }
                if (rs.getString("employee") != null) {
                    Employee employee = new Employee();
                    employee.setID(rs.getString("employee"));
                    deduction.setEmployee(employee);
                }

                list.add(deduction);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
