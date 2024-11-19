package se2203b.ipayroll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EarningTableAdapter implements DataStore{

    private Connection connection;
    private String DB_URL = "jdbc:derby:iPAYROLLDB";

    public EarningTableAdapter(Boolean reset) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Earning");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            }
        }

        try {
            // Create the table
            stmt.execute("CREATE TABLE Earning ("
                    + "earningId VARCHAR(9) NOT NULL PRIMARY KEY, "
                    + "amount DOUBLE, "
                    + "ratePerHour DOUBLE, "
                    + "startDate DATE, "
                    + "endDate DATE, "
                    + "earningSource VARCHAR(9) REFERENCES EarningSource(code),"
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
        Earning earning = (Earning) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        try {
            String command = "INSERT INTO Earning (earningId, amount, ratePerHour, startDate, endDate, earningSource, employee) "
                    + "VALUES ('"
                    + earning.getEarningID() + "', "
                    + earning.getAmount() + ", "
                    + earning.getRatePerHour() + ", "
                    + (earning.getStartDate() == null? null : "'" + earning.getStartDate() + "'") + ", "
                    + (earning.getEndDate() == null? null : "'" + earning.getEndDate() + "'") + ", '"
                    + earning.getEarningSource().getCode() + "', "
                    + (earning.getEmployee().getID() == null? null : "'" + earning.getEmployee().getID() + "'")
                    +  " )";
            stmt.executeUpdate(command);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRecord(Object data) throws SQLException {
        Earning earning = (Earning) data;
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        // Get the user account name if exists
        String command = null;

        command = "UPDATE Earning "
                + "SET amount = " + earning.getAmount() + ", "
                + "ratePerHour = " + earning.getRatePerHour() + ", "
                + "startDate = '" + earning.getStartDate() + "', "
                + "endDate = '" + earning.getEndDate() + "', "
                + "earningSource = " + (earning.getEarningSource().getCode() == null? null : "'" + earning.getEarningSource().getCode() + "' ") + ", "
                + "employee = " + (earning.getEmployee().getID() == null? null : "'" + earning.getEmployee().getID() + "' ")
                + "WHERE earningId = "
                + "'" + earning.getEarningID() + "'";

        stmt.executeUpdate(command);
        connection.close();
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Earning WHERE employee = '" + key + "'");
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
        String command = "SELECT earningId FROM Earning";

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
        Earning earning = new Earning();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM Earning WHERE earningId = '" + key + "'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            earning.setEarningID(rs.getString("earningId"));
            earning.setAmount(rs.getDouble("amount"));
            earning.setRatePerHour(rs.getDouble("ratePerHour"));
            earning.setStartDate(rs.getDate("startDate"));
            earning.setEndDate(rs.getDate("endDate"));
            if (rs.getString("earningSource") != null) {
                EarningSource earningSource = new EarningSource(rs.getString("earningSource"), "");
                earning.setEarningSource(earningSource);
            }
            if (rs.getString("employee") != null) {
                Employee employee = new Employee();
                employee.setID(rs.getString("employee"));
                earning.setEmployee(employee);
            }
        }
        connection.close();
        return earning;
    }

    @Override
    public Object findOneRecord(Object employeeId) throws SQLException {
        Earning earning = new Earning();
        ResultSet rs;
        connection = DriverManager.getConnection(DB_URL);

        // Create a Statement object
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String command = "SELECT * FROM Earning WHERE employee = '" + employeeId + "'";

        // Execute the statement and return the result
        rs = stmt.executeQuery(command);
        while (rs.next()) {
            earning.setEarningID(rs.getString("earningId"));
            earning.setAmount(rs.getDouble("amount"));
            earning.setRatePerHour(rs.getDouble("ratePerHour"));
            earning.setStartDate(rs.getDate("startDate"));
            earning.setEndDate(rs.getDate("endDate"));
            if (rs.getString("earningSource") != null) {
                EarningSource earningSource = new EarningSource(rs.getString("earningSource"), "");
                earning.setEarningSource(earningSource);
            }
            if (rs.getString("employee") != null) {
                Employee employee = new Employee();
                employee.setID(rs.getString("employee"));
                earning.setEmployee(employee);
            }
        }
        connection.close();
        return earning;

    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        List<Object> list = new ArrayList<>();
        ResultSet result;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM Earning";

            // Execute the statement and return the result
            result = stmt.executeQuery(command);

            while (result.next()) {
                Earning earning = new Earning();
                earning.setEarningID(result.getString("earningId"));
                earning.setAmount(result.getDouble("amount"));
                earning.setRatePerHour(result.getDouble("ratePerHour"));
                earning.setStartDate(result.getDate("startDate"));
                earning.setEndDate(result.getDate("endDate"));
                if (result.getString("earningSource") != null) {
                    EarningSource earningSource = new EarningSource(result.getString("earningSource"), "");
                    earning.setEarningSource(earningSource);
                }
                if (result.getString("employee") != null) {
                    Employee employee = new Employee();
                    employee.setID(result.getString("employee"));
                    earning.setEmployee(employee);
                }

                list.add(earning);
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
        ResultSet result;

        try {
            connection = DriverManager.getConnection(DB_URL);

            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Create a string with a SELECT statement
            String command = "SELECT * FROM Earning WHERE employee = '" + id + "'";
            // Execute the statement and return the result
            result = stmt.executeQuery(command);
            while (result.next()) {
                Earning earning = new Earning();
                earning.setEarningID(result.getString("earningId"));
                earning.setAmount(result.getDouble("amount"));
                earning.setRatePerHour(result.getDouble("ratePerHour"));
                earning.setStartDate(result.getDate("startDate"));
                earning.setEndDate(result.getDate("endDate"));
                if (result.getString("earningSource") != null) {
                    EarningSource earningSource = new EarningSource(result.getString("earningSource"), "");
                    earning.setEarningSource(earningSource);
                }
                if (result.getString("employee") != null) {
                    Employee employee = new Employee();
                    employee.setID(result.getString("employee"));
                    earning.setEmployee(employee);
                }
                list.add(earning);
            }
            connection.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
