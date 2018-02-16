package model;

import com.google.gson.annotations.Expose;


import java.util.Date;
import java.util.List;


/**
 * Created by Antonio Zaitoun on 17/12/2017.
 */
public class Report {

    @Expose
    private Integer reportNum;

    @Expose
    private String description;

    @Expose
    private Date incidentDate;

    @Expose
    private Volunteer volunteer;

    @Expose
    private Vehicle vehicle;

    @Expose
    private Team team;

    @Expose
    private List<Violation> violations;

    public Report(Integer reportNum, String description, Date incidentDate, Volunteer volunteer, Vehicle vehicle) {
        this.reportNum = reportNum;
        this.description = description;
        this.incidentDate = incidentDate;
        this.volunteer = volunteer;
        this.vehicle = vehicle;
    }

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

}
