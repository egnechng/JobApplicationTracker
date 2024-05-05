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
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }

    public String showBrowseOptionsWindow(ArrayList<Job> jobsList) {
        clear();
        printJobsTable(jobsList); // First list all of the jobs
        // Give options coming out of Browse
        System.out.println("Choose one of the following options: ");
        System.out.println("(1) Filter Applications by Status");
        System.out.println("(2) Edit Job Application");
        System.out.println("(3) Return to Main Window");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }

    public String showFilterJobsWindow() {
        clear();
        System.out.println("Choose which status to filter by: ");
        System.out.println("(1) Applied");
        System.out.println("(2) Screened");
        System.out.println("(3) Interviewed");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }

    public void printJobsTable(ArrayList<Job> jobsList) {
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

    }

    public String showEditJobWindow() {
        // ask to enter the job id to edit
        System.out.println("--------------------------------------------------");
        System.out.println("Edit Job Application");
        System.out.print("Enter the ID of the job to edit: ");

        return scanner.nextLine();

    }

    public String[] showEditSelectionWindow(Job job) {
        // show the job
        clear();
        System.out.println("Job Details:");
        System.out.println("--------------------------------------------------");
        System.out.println("Company: " + job.getCompany());
        System.out.println("Role: " + job.getRole());
        System.out.printf("Salary: $%.2f\n", job.getSalary()); // Prints with a dollar sign
        System.out.println("Location: " + job.getLocation());
        System.out.println("Date Applied: " + job.getDateAppliedFormatted());
        System.out.println("Status: " + job.getStatus());
        System.out.println("Link to Posting: " + job.getLinkToPosting());
        System.out.println("--------------------------------------------------");

        System.out.println("Enter the field of the job to edit:");
        System.out.println("(1) Company");
        System.out.println("(2) Role");
        System.out.println("(3) Salary");
        System.out.println("(4) Location");
        System.out.println("(5) Date Applied");
        System.out.println("(6) Status");
        System.out.println("(7) Link to Posting");
        System.out.println("(8) DELETE JOB");
        System.out.print("Enter your choice: ");
        String[] result = new String[2];
        String field = scanner.nextLine();
        result[0] = field;
        result[1] = "";
        if (Integer.parseInt(field) == 5) {
            // Date
            System.out.print("Enter the new value (MM/DD/YYYY): ");
            result[1] = scanner.nextLine();
        } else if (Integer.parseInt(field) != 8) {
            System.out.print("Enter the new value: ");
            result[1] = scanner.nextLine();
        } else {
            clear();
            System.out.println("Deleting Job...");
        }
        // return [field to edit, new value]
        return result;


    }

    public String showSearchJobWindow() {
        clear();
        System.out.println("Search for Job Application");
        System.out.println("--------------------------------------------------");
        System.out.print("Enter the search key (Company, Role, or Location): ");
        String key = scanner.nextLine();
        clear();
        System.out.println("Showing all results matching: " + key);
        return key;
    }

    public String showPostSearchOptions() {
        System.out.println("Choose one of the following options: ");
        System.out.println("(1) Edit Job Application");
        System.out.println("(2) Return to Main Window");
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
    }


    public Job showAddJobWindow() {
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

    public String showImportJobWindow() {
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
