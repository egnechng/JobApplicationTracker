import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WindowManager {

    private final Scanner scanner;
    private static WindowManager instance;

    private WindowManager() {
        scanner = new Scanner(System.in);
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }
        return instance;
    }

    public String showMainOptionsWindow() {
        System.out.println("==============JOB TRACKER===============");
        System.out.println("Choose one of the following options:");
        System.out.println("(1) Browse all Job Applications.");
        System.out.println("(2) Search for Job Application");
        System.out.println("(3) Add new Job Application");
        System.out.println("(4) Import Job Applications (from csv file)");
        System.out.println("(5) Quit");
        System.out.println("Enter your choice:");
        return scanner.nextLine();
    }

    public String printJobsTable(ArrayList<Job> jobsList) {
        clear();
        System.out.println("My Job Applications");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s %-20s %-20s %-15s %-20s %-15s %-15s %-30s\n", "ID", "Company", "Role", "Salary", "Location", "Date Applied", "Status", "Link");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        for (Job job : jobsList) {
            System.out.printf("%-4s %-20s %-20s $%-14.2f %-20s %-15s %-15s %-30s\n",
                    job.getId(),
                    job.getCompany(),
                    job.getRole(),
                    job.getSalary(),
                    job.getLocation(),
                    job.getDateAppliedFormatted(),
                    job.getStatus(),
                    job.getLinkToPosting());
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        // Options coming out of Browse
        System.out.println("Choose on of the following options: ");
        System.out.println("(1) Filter Applications by Company, Role or Location");
        System.out.println("(2) Edit Job Application");
        System.out.println("(3) Return to Main Window");
        System.out.println("Enter your choice:");
        return scanner.nextLine();
    }


    public Job addJobWindow() {
        clear();
        System.out.println("Add New Job Application: (Enter the following information)");
        System.out.println("===========================================================================");
        System.out.print("Company Name: ");
        String company = scanner.nextLine();
        System.out.print("Role/Industry: ");
        String role = scanner.nextLine();
        System.out.print("Salary/Wage: ");
        double salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Location: ");
        String location = scanner.nextLine();
        // keep asking for date until correct format
        boolean correctFormat = false;
        Date date = new Date();
        while (!correctFormat) {
            System.out.print("Date Applied (MM/DD/YYYY): ");
            String dateString = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);

            try {
                date = dateFormat.parse(dateString);
                // System.out.println("Parsed Date: " + date);
                correctFormat = true;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in MM/DD/YYYY format.");
            }
        }
        System.out.print("Status (Applied, Screened, Interviewed): ");
        String status = scanner.nextLine();
        System.out.print("Link to job posting: ");
        String link = scanner.nextLine();
        clear();

        // create Job object
        return new Job(company, role, salary, location, date, status, link);
    }

    public String importJobWindow() {
        clear();
        System.out.println("Import Jobs from .csv File: (Make sure you input file name correctly)");
        System.out.println("===========================================================================");
        System.out.print("Name of .csv file: ");
        String filename = scanner.nextLine();


        clear();
        return filename;
    }

    // used to clear the terminal
    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }





}
