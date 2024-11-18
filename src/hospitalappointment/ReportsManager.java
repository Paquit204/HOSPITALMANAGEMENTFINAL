package hospitalappointment;

import java.util.Scanner;

public class ReportsManager {
    private static final config dbConfig = new config();
    private static Scanner scanner = new Scanner(System.in);

    public void manageReports() {
        while (true) {
            System.out.println("\n================================== Reports ===================================");
            System.out.println("1. All Patient History");
            System.out.println("2. Appointment History");
            System.out.println("3. Individual Patient Report");
            System.out.println("4. Individual Appointment Report");
            System.out.println("5. Back");
            System.out.println("\n==============================================================================");
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
    System.out.print("Enter patient ID for report: ");
    int patientId = getValidIntegerInput(); 

    
    String sql = "SELECT p.id, p.name, p.age, p.gender, a.appointment_date, a.appointment_type, a.status " +
                 "FROM patients p " +
                 "LEFT JOIN appointments a ON p.id = a.patient_id " +
                 "WHERE p.id = ?";

    
    String[] headers = {"Patient ID", "Name", "Age", "Gender", "Appointment Date", "Appointment Type", "Status"};
    // Corresponding column names in the result set
    String[] columnNames = {"id", "name", "age", "gender", "appointment_date", "appointment_type", "status"};
    
    
    dbConfig.viewRecords(sql, headers, columnNames, patientId);
}

    private void individualAppointmentReport() {
        System.out.print("Enter appointment ID for report: ");
        int appointmentId = getValidIntegerInput(); 
        String sql = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date, a.appointment_type, a.status " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.id " +
                     "JOIN doctors d ON a.doctor_id = d.id WHERE a.id = ?";
        String[] headers = {"ID", "Patient Name", "Doctor Name", "Appointment Date", "Appointment Type", "Status"};
        String[] columnNames = {"id", "patient_name", "doctor_name", "appointment_date", "appointment_type", "status"};
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