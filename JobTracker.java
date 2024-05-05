import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

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
        wm.clear();
        System.out.println("Welcome to the Job Application Tracker!");

        forever:
        while (true) {

            String opt = wm.showMainOptionsWindow();

            if (!validateOption(opt , 5)) {
                wm.clear();
                System.out.println("Not a valid option!");
                continue;
            }
            switch(Integer.parseInt(opt)) {
                case 1: {
                    // Display all my applications
                    ArrayList<Job> jobsList = dm.getJobs();
                    opt = wm.showBrowseOptionsWindow(jobsList);

                    if (!validateOption(opt, 3)) {
                        wm.clear();
                        System.out.println("Not a valid option!");
                        continue;
                    }
                    switch(Integer.parseInt(opt)) {
                        case 1: {
                            // Filter by Status
                            opt = wm.showFilterJobsWindow();
                            if (!validateOption(opt, 3)) {
                                wm.clear();
                                System.out.println("Not a valid option!");
                                continue;
                            }
                            ArrayList<Job> filteredJobList = dm.filterJobsByStatus(Integer.parseInt(opt));
                            // print filtered list
                            wm.printJobsTable(filteredJobList);

                        } break;

                        case 2: {
                            opt = wm.showEditJobWindow();
                            if (!validateOption(opt, Job.getLastId())) {
                                wm.clear();
                                System.out.println("Not a valid option!");
                                continue;
                            }
                            // return the Job with this ID
                            Job jobToEdit = dm.getJobById(Integer.parseInt(opt));
                            if (jobToEdit != null) {
                                // open the menu to edit the jobs
                                String[] editConfig = wm.showEditSelectionWindow(jobToEdit);
                                // editConfig = [field to edit, new value]
                                int field = Integer.parseInt(editConfig[0]);
                                String newValue = editConfig[1];
                                // check if flagged for delete
                                if (field == 8) {
                                    dm.deleteJob(jobToEdit);
                                    System.out.println("Job Deleted!");
                                } else {
                                    // call dm to edit job
                                    dm.editJob(jobToEdit, field, newValue);
                                }

                            } else {
                                System.out.println("This job does not exist!");
                                continue;
                            }
                            wm.printJobsTable(dm.getJobs());


                        } break;

                        default : {
                            wm.clear();
                        }
                    }
                }
                break;

                case 2: {
                    // Search for job application (Company name, role, location)
                    String searchKey = wm.showSearchJobWindow();

                    // use dm to search for jobs that match this key
                    ArrayList<Job> searchResults = dm.searchJobs(searchKey);
                    wm.printJobsTable(searchResults);

                    opt = wm.showPostSearchOptions();
                    if (!validateOption(opt, 2)) {
                        wm.clear();
                        System.out.println("Not a valid option!");
                        continue;
                    }
                    if (Integer.parseInt(opt) == 1) {
                        // TODO: Maybe make this edit into a function since its used twice
                        opt = wm.showEditJobWindow();
                        if (!validateOption(opt, Job.getLastId())) {
                            wm.clear();
                            System.out.println("Not a valid option!");
                            continue;
                        }
                        // return the Job with this ID
                        Job jobToEdit = dm.getJobById(Integer.parseInt(opt));
                        if (jobToEdit != null) {
                            // open the menu to edit the jobs
                            String[] editConfig = wm.showEditSelectionWindow(jobToEdit);
                            // editConfig = [field to edit, new value]
                            int field = Integer.parseInt(editConfig[0]);
                            String newValue = editConfig[1];
                            // check if flagged for delete
                            if (field == 8) {
                                dm.deleteJob(jobToEdit);
                                System.out.println("Job Deleted!");
                            } else {
                                // call dm to edit job
                                dm.editJob(jobToEdit, field, newValue);
                            }

                        } else {
                            System.out.println("This job does not exist!");
                            continue;
                        }
                        wm.printJobsTable(dm.getJobs());
                    }

                }
                break;

                case 3: {
                    // Add new Job Application
                    Job j = wm.showAddJobWindow();
                    // call dm to save job
                    dm.addJob(j);
                    System.out.println("Job Application Saved!");

                }
                break;

                case 4: {
                    // Import Job Applications by csv file
                    String filename = wm.showImportJobWindow();
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
            // Serialize and Save Jobs list
            dm.serializeAndSave();
        }

    }

    public boolean validateOption(String opt, int range) {
        try{
            Integer.parseInt(opt);
        }catch (NumberFormatException exception){
            return false;
        }
        int parsed = Integer.parseInt(opt);
        return parsed > 0 && parsed <= range;
    }


    public static void main(String[] args) {

        DataManager dm = DataManager.getInstance();
        WindowManager wm = WindowManager.getInstance();
        JobTracker jt = JobTracker.getInstance();
        jt.runApp(dm, wm); // Main App loop

    }

}

