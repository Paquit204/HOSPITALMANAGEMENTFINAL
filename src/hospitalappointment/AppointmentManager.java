package hospitalappointment;

import java.util.Scanner;

public class AppointmentManager {
    private static final config dbConfig = new config();
    private static Scanner scanner = new Scanner(System.in);

    public static void manageAppointments() {
        while (true) {
            displayMenu();
            int choice = getValidIntegerInput(); 

            switch (choice) {
                case 1:
                    addAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    updateAppointment();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n================================ Manage Appointments ============================");
        System.out.println("1. Add Appointment");
        System.out.println("2. View Appointments");
        System.out.println("3. Update Appointment");
        System.out.println("4. Delete Appointment");
        System.out.println("5. Back");
        System.out.println("\n===============================================================================");
        System.out.print("Choose an option: ");
    }

   private static void addAppointment() {
   
    PatientManager.viewPatients();  
    int patientId = getValidPatientId();  

    
    DoctorManager.viewDoctors();  
    int doctorId = getValidDoctorId();  

    
     
    System.out.print("Enter appointment date (YYYY-MM-DD): ");
    String appointmentDate = scanner.nextLine().trim();  
    System.out.print("Enter appointment type: ");
    String appointmentType = scanner.nextLine().trim();  
    System.out.print("Enter status (Scheduled, Completed, Cancelled): ");
    String status = scanner.nextLine().trim();  

    
    if (appointmentDate.isEmpty() || appointmentType.isEmpty() || status.isEmpty()) {
        System.out.println("All fields must be filled in. Please try again.");
        return;  
    }

    
    String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_type, status) VALUES (?, ?, ?, ?, ?)";
    dbConfig.addRecord(sql, patientId, doctorId, appointmentDate, appointmentType, status);
}
    private static int getValidPatientId() {
        int patientId;
        while (true) {
            System.out.print("Enter patient ID: ");
            patientId = getValidIntegerInput();
            if (dbConfig.isPatientIdValid(patientId)) {
                break; 
            }
            System.out.println("Patient ID not found. Please enter a valid patient ID.");
        }
        return patientId;
    }

    private static int getValidDoctorId() {
        int doctorId;
        while (true) {
            System.out.print("Enter doctor ID: ");
            doctorId = getValidIntegerInput();
            if (dbConfig.isDoctorIdValid(doctorId)) {
                break; 
            }
            System.out.println("Doctor ID not found. Please enter a valid doctor ID.");
        }
        return doctorId;
    }

    private static void viewAppointments() {
        String sql = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date, a.appointment_type, a.status " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.id " +
                     "JOIN doctors d ON a.doctor_id = d.id";
        String[] headers = {"ID", "Patient Name", "Doctor Name", "Appointment Date", "Appointment Type", "Status"};
        String[] columnNames = {"id", "patient_name", "doctor_name", "appointment_date", "appointment_type", "status"};
        dbConfig.viewRecords(sql, headers, columnNames);
    }

    private static void updateAppointment() {
        System.out.print("Enter appointment ID to update: ");
        int id = getValidIntegerInput(); 

        String currentStatus = dbConfig.getCurrentStatus(id); 

        if ("Completed".equalsIgnoreCase(currentStatus)) {
            System.out.println("Cannot update appointment. The status is already 'Completed'.");
            return;
        }

        scanner.nextLine(); 
        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.nextLine();
        System.out.print("Enter new appointment type: ");
        String appointmentType = scanner.nextLine();
        System.out.print("Enter new status (Scheduled, Completed, Cancelled): ");
        String status = scanner.nextLine();

        String sql = "UPDATE appointments SET appointment_date = ?, appointment_type = ?, status = ? WHERE id = ?";
        dbConfig.updateRecord(sql, appointmentDate, appointmentType, status, id);
    }

    private static void deleteAppointment() {
        System.out.print("Enter appointment ID to delete: ");
        int id = getValidIntegerInput(); 

        String sql = "DELETE FROM appointments WHERE id = ?";
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