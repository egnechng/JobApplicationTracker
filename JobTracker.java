import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class JobTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dm = new DataManager();

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
                    // Display all my applications

                    // Contact c = new Contact(name, email, phone, notes);

                    //  contactList.add(c);

                    System.out.println("Saved sucessfully....Press Enter to go back to the Main Window");
                    scanner.nextLine();
                }
                break;

                case 2: {
                    // Search for job application (Company name, role, location)
                    System.out.println("Case 2");

                }
                break;

                case 3: {
                    // Add new Job Application
                    Job j = addJob(scanner);
                    // call dm to save job
                    dm.saveJob(j);

                }
                break;

                case 4: {
                    // Import Job Applications by csv file
                    System.out.println("Case 4");

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

    public static Job addJob(Scanner scanner) {
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
        Job j = new Job(company, role, salary, location, date, status, link);
        System.out.println("Job Application Saved!");
        return j;
    }
    // used to clear the terminal
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

