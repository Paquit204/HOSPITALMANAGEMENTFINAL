package hospitalappointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reports {
    private static final config dbConfig = new config();
    private static Scanner scanner = new Scanner(System.in);

    public void Reports() {
        while (true) {
            System.out.println("\n   ================================== --Reports-- ===================================");
            System.out.println("    //                     |        1. All Patient History                |         // ");
            System.out.println("   //                      |        2. Appointment History                |        //  ");
            System.out.println("  //                       |        3. Individual Patient Report          |       //   ");
            System.out.println(" //                        |        4. Individual Appointment Report      |      //    ");
            System.out.println("//                         |        5. Back                               |     //     ");
            System.out.println("=================================================================================      ");
            System.out.print("Choose a report option: ");
            int choice = getValidIntegerInput(); 
            
            switch (choice) {
                case 1:
                    viewAllPatientHistory();
                    break;
                case 2:
                    viewAppointmentHistory();
                    break;
                case 3:
                    individualPatientReport();
                    break;
                case 4:
                    individualAppointmentReport();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllPatientHistory() {
        String sql = "SELECT p.id, p.name, p.age, p.gender, a.appointment_date, a.appointment_type, a.status, d.name AS doctor_name " +
                     "FROM patients p " +
                     "LEFT JOIN appointments a ON p.id = a.patient_id " +
                     "LEFT JOIN doctors d ON a.doctor_id = d.id";
        String[] headers = {"Patient ID", "Name", "Age", "Gender", "Appointment Date", "Appointment Type", "Status", "Doctor Name"};
        String[] columnNames = {"id", "name", "age", "gender", "appointment_date", "appointment_type", "status", "doctor_name"};
        dbConfig.viewRecords(sql, headers, columnNames);
    }

    private void viewAppointmentHistory() {
        String sql = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date, a.appointment_type, a.status " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.id " +
                     "JOIN doctors d ON a.doctor_id = d.id";
        String[] headers = {"Appointment ID", "Patient Name", "Doctor Name", "Appointment Date", "Appointment Type", "Status"};
        String[] columnNames = {"id", "patient_name", "doctor_name", "appointment_date", "appointment_type", "status"};
        dbConfig.viewRecords(sql, headers, columnNames);
    }

    private void individualPatientReport() {
    Patient.viewPatients();
 
    System.out.print(" Enter patient ID for report:");
    int patientId = getValidIntegerInput(); 
      System.out.print("\n");
     System.out.println("=============================================================================\n");
    String patientNameSql = "SELECT id, name FROM patients WHERE id = ?";
    String[] patientNameColumn = {"name", "id"};
   
    dbConfig.viewRecord(patientNameSql, null, patientNameColumn, patientId);
     System.out.println("=============================================================================\n");

    String sql = "SELECT p.id, p.name, p.age, p.gender, a.appointment_date, a.appointment_type, a.status " +
                 "FROM patients p " +
                 "LEFT JOIN appointments a ON p.id = a.patient_id " +
                 "WHERE p.id = ?";

    String[] headers = { "Age", "Gender", "Appointment Date", "Appointment Type", "Status" };
    String[] columnNames = { "age", "gender", "appointment_date", "appointment_type", "status" };

  
    dbConfig.viewRecords(sql, headers, columnNames, patientId);
}
   private void individualAppointmentReport() {
    Doctor.viewDoctors(); 
    System.out.print("Enter appointment ID for report: ");
    int appointmentId = getValidIntegerInput(); 

    // SQL to fetch appointment details
    String detailsSql = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name " +
                        "FROM appointments a " +
                        "JOIN patients p ON a.patient_id = p.id " +
                        "JOIN doctors d ON a.doctor_id = d.id " +
                        "WHERE a.id = ?";
    
    // Fetch appointment details
    try (Connection conn = config.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(detailsSql)) {
        
        pstmt.setInt(1, appointmentId);
        ResultSet rs = pstmt.executeQuery();
          
        if (rs.next()) {
               System.out.println("=============================================================================\n");
            System.out.println("Appointment Details:");
            System.out.println("Appointment ID: " + rs.getInt("id"));
            System.out.println("Patient Name: " + rs.getString("patient_name"));
            System.out.println("Doctor Name: " + rs.getString("doctor_name"));
            System.out.println(); 
               System.out.println("=============================================================================");
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving appointment details: " + e.getMessage());
    }

    // SQL to fetch the appointment report
    String sql = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date, a.appointment_type, a.status " +
                 "FROM appointments a " +
                 "JOIN patients p ON a.patient_id = p.id " +
                 "JOIN doctors d ON a.doctor_id = d.id " +
                 "WHERE a.id = ?";
    
    String[] headers = {"ID", "Patient Name", "Doctor Name", "Appointment Date", "Appointment Type", "Status"};
    String[] columnNames = {"id", "patient_name", "doctor_name", "appointment_date", "appointment_type", "status"};
    
    // Display the report for the selected appointment
    dbConfig.viewRecords(sql, headers, columnNames, appointmentId);
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