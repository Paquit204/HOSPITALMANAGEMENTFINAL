package hospitalappointment;

import java.util.Scanner;

public class HospitalAppointmentSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PatientManager patientManager = new PatientManager();
        AppointmentManager appointmentManager = new AppointmentManager();
        DoctorManager doctorManager = new DoctorManager();
        ReportsManager reportsManager = new ReportsManager();

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
                    patientManager.managePatients();
                    break;
                case 2:
                    appointmentManager.manageAppointments();
                    break;
                case 3:
                    doctorManager.manageDoctors();
                    break;
                case 4:
                    reportsManager.manageReports();
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