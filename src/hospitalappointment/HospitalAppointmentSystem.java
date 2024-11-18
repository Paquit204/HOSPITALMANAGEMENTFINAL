package hospitalappointment;

import java.util.Scanner;

public class HospitalAppointmentSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Patient patientManager = new Patient();
        Appointment appointmentManager = new Appointment();
        Doctor doctorManager = new Doctor();
        Reports reportsManager = new Reports();

        while (true) {
            System.out.println("\n======================== Hospital Appointment System ========================");
            System.out.println("                                                                               ");
            System.out.println("                                 1.  Patients");
            System.out.println("                                 2.  Appointments");
            System.out.println("                                 3.  Doctors");
            System.out.println("                                 4.  Reports");
            System.out.println("                                 5.  Exit");
            System.out.println("                                                                               ");
              System.out.println("\n====================== Hospital Appointment System ========================");
            System.out.print ("Pili lng pre: ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    patientManager.Patients();
                    break;
                case 2:
                    appointmentManager.Appointment();
                    break;
                case 3:
                    doctorManager.Doctors();
                    break;
                case 4:
                    reportsManager.Reports();
                    break;
                case 5:
                    System.out.println("                  SALAMAT SA TANAN                  ");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
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