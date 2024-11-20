package hospitalappointment;

import static hospitalappointment.config.connectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private static final config dbConfig = new config();
    private static Scanner scanner = new Scanner(System.in);

    public void Patients() {
        while (true) {
            System.out.println("\n    ==============================+--Patients--+================================");
            System.out.println("     //                      ||     1. Add Patient     ||                      //");
            System.out.println("    //                       ||     2. View Patients   ||                     //");
            System.out.println("   //                        ||     3. Update Patient  ||                    //");
            System.out.println("  //                         ||     4. Delete Patient  ||                   //");
            System.out.println(" //                          ||     5. Back            ||                  //");
              System.out.println("=========================================================================");
            System.out.print("Choose an option: ");
            int choice = getValidIntegerInput(); 

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewPatients();
                    break;
                case 3:
                     viewPatients();
                    updatePatient();
                     viewPatients();
                    break;
                case 4:
                     viewPatients();
                    deletePatient();
                     viewPatients();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = getValidIntegerInput(); 
        System.out.print("Enter patient gender: ");
        String gender = scanner.nextLine();

        String sql = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
        dbConfig.addRecord(sql, name, age, gender);
    }

    public static void viewPatients() {
        String sql = "SELECT * FROM patients";
        String[] headers = {"ID", "Name", "Age", "Gender"};
        String[] columnNames = {"id", "name", "age", "gender"};
        dbConfig.viewRecords(sql, headers, columnNames);
    }

   private void updatePatient() {
    int id;
    
    // Loop until a valid patient ID is entered
    while (true) {
        System.out.print("Enter patient ID to update: ");
        id = getValidIntegerInput(); // Assume this method checks for valid integer input

        // Check if the patient ID exists in the database
        if (patientExists(id)) {
            break; // Exit the loop if the ID is valid
        } else {
            System.out.println("Invalid patient ID. Please enter again.");
        }
    }

    // Proceed to get new patient details
    System.out.print("Enter new patient name: ");
    String name = scanner.nextLine();
    System.out.print("Enter new patient age: ");
    int age = getValidIntegerInput(); 
    System.out.print("Enter new patient gender: ");
    String gender = scanner.nextLine();

    String sql = "UPDATE patients SET name = ?, age = ?, gender = ? WHERE id = ?";
    dbConfig.updateRecord(sql, name, age, gender, id);
    System.out.println("Patient information updated successfully.");
}

// Method to check if a patient exists
private boolean patientExists(int id) {
    String sql = "SELECT COUNT(*) FROM patients WHERE id = ?";
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Return true if count is greater than 0
        }
    } catch (SQLException e) {
        System.err.println("Database error: " + e.getMessage());
    }
    return false; // Return false if patient does not exist
}


    private void deletePatient() {
        System.out.print("Enter patient ID to delete: ");
        int id = getValidIntegerInput(); 

        String sql = "DELETE FROM patients WHERE id = ?";
        dbConfig.deleteRecord(sql, id);
    }

    
    private static int getValidIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine()); 
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
}