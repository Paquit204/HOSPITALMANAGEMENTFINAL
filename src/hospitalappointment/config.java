package hospitalappointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class config {
   
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); 
            con = DriverManager.getConnection("jdbc:sqlite:paquit.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    // Method to add a record to the database
    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]); // Set the value dynamically
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    // Method to view records from any table
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames, Object... values) {
        if (columnHeaders.length != columnNames.length) {
            System.out.println("Error: Mismatch between column headers and column names.");
            return;
        }

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            // Set values for prepared statement if any
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                StringBuilder headerLine = new StringBuilder();
                headerLine.append("-------------------------------------------------------------------------------------------------------------------------------------\n| ");
                for (String header : columnHeaders) {
                    headerLine.append(String.format("%-20s | ", header));
                }
                headerLine.append("\n-------------------------------------------------------------------------------------------------------------------------------------");

                System.out.println(headerLine.toString());

                while (rs.next()) {
                    StringBuilder row = new StringBuilder("| ");
                    for (String colName : columnNames) {
                        String value = rs.getString(colName);
                        row.append(String.format("%-20s | ", value != null ? value : ""));
                    }
                    System.out.println(row.toString());
                }
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }

    //-----------------------------------------------
    // UPDATE METHOD
    //-----------------------------------------------
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = connectDB(); // Use the connectDB method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]);
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]);
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]);
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]);
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]);
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    // Method to delete a record from the database
    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    // Method to retrieve the current status of an appointment
    public String getCurrentStatus(int appointmentId) {
        String status = null;
        String sql = "SELECT status FROM appointments WHERE id = ?";
        
        try (Connection conn = connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                status = rs.getString("status");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving current status: " + e.getMessage());
        }
        return status;
    }
        // Method to check if a patient ID exists
    public boolean isPatientIdValid(int patientId) {
        return isIdValid("patients", patientId);
    }

    // Method to check if a doctor ID exists
    public boolean isDoctorIdValid(int doctorId) {
        return isIdValid("doctors", doctorId);
    }

    // General method to check ID validity
    private boolean isIdValid(String tableName, int id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE id = ?";
        try (Connection conn = connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0; // Return true if count is more than 0
        } catch (SQLException e) {
            System.out.println("Error checking ID validity: " + e.getMessage());
        }
        return false; // In case of an error or no record found
    }

}