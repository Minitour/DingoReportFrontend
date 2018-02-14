package model;

import com.google.gson.annotations.Expose;


import java.util.Collection;


/**
 * Created by Antonio Zaitoun on 23/12/2017.
 */
public class Team{

    @Expose
    private String teamNum;

    @Expose
    private Officer leader;

    @Expose
    private Collection<Officer> officers;

    public Team(String teamNum) {
        this.teamNum = teamNum;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
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
}
