package model;

import com.google.gson.annotations.Expose;


import java.util.Collection;


/**
 * Created by Antonio Zaitoun on 23/12/2017.
 */
public class Team{

    @Expose
    private Integer teamNum;

    @Expose
    private Officer leader;

    @Expose
    private Collection<Officer> officers;

    @Expose
    private Collection<Report> reports;

    public Team(Integer teamNum) {
        this.teamNum = teamNum;
    }

    public Integer getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(Integer teamNum) {
        this.teamNum = teamNum;
    }

    public Officer getLeader() {
        return leader;
    }

    public void setLeader(Officer leader) {
        this.leader = leader;
    }

    public Collection<Officer> getOfficers() {
        return officers;
    }

    public void setOfficers(Collection<Officer> officers) {
        this.officers = officers;
    }

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }
}
