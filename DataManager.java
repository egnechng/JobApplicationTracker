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
            System.out.println("Invalid date format within . Please enter the date in MM/DD/YYYY format.");
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

    /**
     * Searches jobs based on the company, location, or role.
     * @param query The search criterion.
     * @param type The type of the filter: "company", "location", or "role".
     * @return A list of jobs that match the query based on the specified type.
     */
    public ArrayList<Job> searchJobs(String query, String type) {
        return jobs.stream()
                .filter(job -> {
                    return switch (type.toLowerCase()) {
                        case "company" -> job.getCompany().equalsIgnoreCase(query);
                        case "location" -> job.getLocation().equalsIgnoreCase(query);
                        case "role" -> job.getRole().equalsIgnoreCase(query);
                        default -> false; // if type does not match, return no jobs
                    };
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void saveJob(Job j) {
        System.out.println("Job Application Saved!");
    }

}
