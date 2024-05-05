
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class JobTracker {

    // Singleton Design Pattern
    private static JobTracker instance;

    private JobTracker() {}

    public static JobTracker getInstance() {
        if (instance == null) {
            instance = new JobTracker();
        }
        return instance;
    }

    public void runApp(DataManager dm, WindowManager wm) {
        //test job
        dm.addJob(new Job("OpenAI", "Developer", 120000, "San Francisco", new Date(), "Applied", "http://openai.com/jobs/dev"));
        dm.addJob(new Job("OpenAI", "Manager", 130000, "New York", new Date(), "Interview", "http://openai.com/jobs/mgr"));

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

            String optionChosen = wm.showMainOptionsWindow();

            try{
                Integer.parseInt(optionChosen);
            }catch (NumberFormatException exception){
                System.out.println("Not a valid option!");
                continue;
            }
            switch(Integer.parseInt(optionChosen)) {
                case 1: {
                    // Display all my applications
                    ArrayList<Job> jobsList = dm.getJobs();
                    wm.printJobsTable(jobsList);

                    // Filter by Status


                }
                break;

                case 2: {
                    // Search for job application (Company name, role, location)
                    System.out.println("Case 2");

                }
                break;

                case 3: {
                    // Add new Job Application
                    Job j = wm.addJobWindow();
                    // call dm to save job
                    dm.addJob(j);
                    System.out.println("Job Application Saved!");

                }
                break;

                case 4: {
                    // Import Job Applications by csv file
                    String filename = wm.importJobWindow();
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

    }

    public static void main(String[] args) {

        DataManager dm = DataManager.getInstance();
        WindowManager wm = WindowManager.getInstance();
        JobTracker jt = JobTracker.getInstance();
        jt.runApp(dm, wm); // Main App loop

    }

}

