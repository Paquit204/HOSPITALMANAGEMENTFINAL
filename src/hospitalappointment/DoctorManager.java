package hospitalappointment;

import java.util.Scanner;

public class DoctorManager {
    private static final config dbConfig = new config();
    private static Scanner scanner = new Scanner(System.in);

    public void manageDoctors() {
        while (true) {
            System.out.println("\n=================================== Manage Doctors ===========================================");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Back");
              System.out.println("\n============================================================================================");
            System.out.print("Choose an option: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    viewDoctors();
                    break;
                case 3:
                     viewDoctors();
                    updateDoctor();
                     viewDoctors();
                    break;
                case 4:
                     viewDoctors();
                    deleteDoctor();
                     viewDoctors();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addDoctor() {
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter doctor specialization: ");
        String specialization = scanner.nextLine();

        String sql = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
        dbConfig.addRecord(sql, name, specialization);
    }

    public static void viewDoctors() {
        String sql = "SELECT * FROM doctors";
        String[] headers = {"ID", "Name", "Specialization"};
        String[] columnNames = {"id", "name", "specialization"};
        dbConfig.viewRecords(sql, headers, columnNames);
    }

    private void updateDoctor() {
        System.out.print("Enter doctor ID to update: ");
        int id = getValidIntegerInput(); 

        System.out.print("Enter new doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new doctor specialization: ");
        String specialization = scanner.nextLine();

        String sql = "UPDATE doctors SET name = ?, specialization = ? WHERE id = ?";
        dbConfig.updateRecord(sql, name, specialization, id);
    }

    private void deleteDoctor() {
        System.out.print("Enter doctor ID to delete: ");
        int id = getValidIntegerInput(); 

        String sql = "DELETE FROM doctors WHERE id = ?";
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