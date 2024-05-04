import java.io.Serializable;
import java.util.Date;

/**
 * Represents a job application with details about the job and the application status.
 */
public class Job implements Serializable{
    private String company;
    private String role;
    private double salary;
    private String location;
    private Date dateApplied;
    private String status;
    private String linkToPosting;

    /**
     * Constructor to initialize the Job object with all fields.
     * @param company the company name
     * @param role the role or position
     * @param salary the salary offered
     * @param location the job location
     * @param dateApplied the date when the application was submitted
     * @param status the current status of the application
     * @param linkToPosting the URL link to the job posting
     */
    public Job(String company, String role, double salary, String location, Date dateApplied, String status, String linkToPosting) {
        this.company = company;
        this.role = role;
        this.salary = salary;
        this.location = location;
        this.dateApplied = dateApplied;
        this.status = status;
        this.linkToPosting = linkToPosting;
    }

    /**
     * Gets the company name.
     * @return the company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company name.
     * @param company the company name to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets the role for the job.
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role for the job.
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the salary for the job.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary for the job.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the location of the job.
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the job.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the date the job was applied for.
     * @return the dateApplied
     */
    public Date getDateApplied() {
        return dateApplied;
    }

    /**
     * Sets the date the job was applied for.
     * @param dateApplied the date to set when the job was applied
     */
    public void setDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }

    /**
     * Gets the current status of the job application.
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the job application.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the link to the job posting.
     * @return the link to the job posting
     */
    public String getLinkToPosting() {
        return linkToPosting;
    }

    /**
     * Sets the link to the job posting.
     * @param linkToPosting the link to set
     */
    public void setLinkToPosting(String linkToPosting) {
        this.linkToPosting = linkToPosting;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%f,%s,%s,%s)", company, role,salary,location,dateApplied.toString(),status,linkToPosting);
    }
}
