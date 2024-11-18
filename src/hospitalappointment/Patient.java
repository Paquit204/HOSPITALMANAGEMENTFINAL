package hospitalappointment;

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
        System.out.print("Enter patient ID to update: ");
        int id = getValidIntegerInput();

        System.out.print("Enter new patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new patient age: ");
        int age = getValidIntegerInput(); 
        System.out.print("Enter new patient gender: ");
        String gender = scanner.nextLine();

        String sql = "UPDATE patients SET name = ?, age = ?, gender = ? WHERE id = ?";
        dbConfig.updateRecord(sql, name, age, gender, id);
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