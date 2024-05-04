
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class JobTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dm = DataManager.getInstance();

        System.out.println("Welcome to the Job Application Tracker!");
        /*
        try{
            FileInputStream fileStream = new FileInputStream("data.bin");
            ObjectInputStream deserializer = new ObjectInputStream(fileStream);
            contactList = (ArrayList<Contact>) deserializer.readObject();
            int contactsCreated = (int) deserializer.readObject();
            Contact.setContactsCreatedAmount(contactsCreated);
            deserializer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }*/
        forever:
        while (true) {
            System.out.println("==============JOB TRACKER===============");
            System.out.println("Choose one of the following options:");
            System.out.println("(1) Browse all Job Applications.");
            System.out.println("(2) Search for Job Application");
            System.out.println("(3) Add new Job Application");
            System.out.println("(4) Import Job Applications (from csv file)");
            System.out.println("(5) Quit");
            System.out.println("Enter your choice:");


            String optionChosen = scanner.nextLine();
            try{
                Integer.parseInt(optionChosen);
            }catch (NumberFormatException exception){
                System.out.println("Not a valid option!");
                continue;
            }
            switch(Integer.parseInt(optionChosen)) {
                case 1: {
                    // Test job
                    dm.addJob(new Job("OpenAI", "Developer", 120000, "San Francisco", new Date(), "Applied", "http://openai.com/jobs/dev"));
                    // Display all my applications
                    ArrayList<Job> jobsList = dm.getJobs();
                    printJobsTable(jobsList);
                }
                break;

                case 2: {
                    // Search for job application (Company name, role, location)
                    System.out.println("Case 2");

                }
                break;

                case 3: {
                    // Add new Job Application
                    Job j = addJobWindow(scanner);
                    // call dm to save job
                    dm.addJob(j);

                }
                break;

                case 4: {
                    // Import Job Applications by csv file
                    String filename = importJobWindow(scanner);
                    boolean importResult = dm.importFromCSV(filename);
                    if (importResult){
                        System.out.println("Successfully imported job applications from .csv file!");
                    }else{
                        System.out.println("Job importing failed, look for error messages above, fix the CSV file, and retry please!");
                    }

                }
                break;

                case 5: {
                    System.out.println("Quitting...");
                    break forever;
                }


            }
        }
        scanner.close();

    }

    public static void printJobsTable(ArrayList<Job> jobsList) {
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-20s %-10s %-20s %-15s %-15s %-30s\n", "Company", "Role", "Salary", "Location", "Date Applied", "Status", "Link");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (Job job : jobsList) {
            System.out.printf("%-20s %-20s $%-9.2f %-20s %-15s %-15s %-30s\n",
                    job.getCompany(),
                    job.getRole(),
                    job.getSalary(),
                    job.getLocation(),
                    job.getDateApplied(),
                    job.getStatus(),
                    job.getLinkToPosting());
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------");
    }

    public static Job addJobWindow(Scanner scanner) {
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
            // TODO: parse date into date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);

            try {
                date = dateFormat.parse(dateString);
                System.out.println("Parsed Date: " + date);
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

    public static String importJobWindow(Scanner scanner) {
        clear();
        System.out.println("Import Jobs from .csv File: (Make sure you input file name correctly)");
        System.out.println("===========================================================================");
        System.out.print("Name of .csv file: ");
        String filename = scanner.nextLine();

        
        clear();
        return filename;
    }

    // used to clear the terminal
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

