import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class DataManager {

    private static DataManager instance;
    private ArrayList<Job> jobs;

    private DataManager() {
        jobs = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    public boolean importFromCSV(String filename){
        try{
            ArrayList<Job> temp = new ArrayList<Job>();
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine(); // Skip header line
            String line;
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);


            while ((line = bufferedReader.readLine()) != null){
                String[] field = line.split(",");
                Double parsedSalary;
                if (field[2] == ""){
                    parsedSalary = 0.0;
                }else{
                    parsedSalary  = Double.parseDouble(field[2]);
                }
                Date parsedApplicationDate = dateFormat.parse(field[4]);
                Job newJob = new Job(field[0], field[1],parsedSalary, field[3],parsedApplicationDate, field[5], field[6]);
                // TODO: might need to remove add/remove fields. Also notice the format for date is a long of milliseconds
                temp.add(newJob);
            }
            bufferedReader.close();
            jobs.addAll(temp);
            return true;
        }
        catch (IOException e){
            System.out.println("Error while reading the file " + filename);
            return false;
        }
        catch (ParseException e) {
            System.out.println("Invalid date format within imported CSV. Please enter the date in MM/DD/YYYY format.");
            return false;
        }
    }

    public void addJob(Job j) {
        jobs.add(j);
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void deleteJob(Job job) {
        int id = job.getId();
        // delete job from the list
        for (Job j : jobs) {
            if (j.getId() == id) {
                jobs.remove(j);
                return;
            }
        }
    }

    public void editJob(Job job, int field, String newValue) {
        // Assume that the valid values are passed through here
        switch (field) {
            case 1: // Company
                job.setCompany(newValue);
                break;
            case 2: // Role
                job.setRole(newValue);
                break;
            case 3: // Salary
                double salary = Double.parseDouble(newValue);
                job.setSalary(salary);
                break;
            case 4: // Location
                job.setLocation(newValue);
                break;
            case 5: // Date Applied
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date = dateFormat.parse(newValue);
                    job.setDateApplied(date);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter the date in MM/DD/YYYY format.");
                }
                break;
            case 6: // Status
                job.setStatus(newValue);
                break;
            case 7: // Link to Posting
                job.setLinkToPosting(newValue);
                break;
            default:
                System.out.println("Invalid option selected.");
                break;

        }
    }

    public ArrayList<Job> searchJobs(String query) {
        ArrayList<Job> searchResults = new ArrayList<>();

        for (Job job : jobs) {
            // Check if the query is a substring of any of the specified fields
            if (job.getCompany().toLowerCase().contains(query.toLowerCase()) ||
                    job.getRole().toLowerCase().contains(query.toLowerCase()) ||
                    job.getLocation().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(job);
            }
        }

        return searchResults;
    }

    public Job getJobById(int id) {

        for (Job j : jobs) {
            if (j.getId() == id) {
                return j;
            }
        }

        return null;
    }

    public ArrayList<Job> filterJobsByStatus(int option) {
        String status = "";
        ArrayList<Job> filteredJobs = new ArrayList<>();
        switch(option) {
            case 1 : {
                // by Applied
                status = "Applied";
            } break;
            case 2 : {
                // by Screened
                status = "Screened";
            } break;
            case 3 : {
                status = "Interviewed";
            } break;

        }

        for (Job j : jobs) {
            if (j.getStatus().equalsIgnoreCase(status)) {
                filteredJobs.add(j);
            }
        }

        return filteredJobs;
    }

    public static void saveJob(Job j) {
        // TODO: Save this into the bin file.
        System.out.println("Job Application Saved!");
    }

}
