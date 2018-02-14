package model;

import com.google.gson.annotations.Expose;

public class Officer extends Account {

    @Expose
    private String badgeNum;

    @Expose
    private String name;

    @Expose
    private String phoneExtension;

    @Expose
    private int rank;

    @Expose
    private Team team;

    public Officer(String ID, String EMAIL, String badgeNum, String name, String phoneExtension, int rank) {
        super(ID, EMAIL, 2);
        this.badgeNum = badgeNum;
        this.name = name;
        this.phoneExtension = phoneExtension;
        this.rank = rank;
    }

    public Officer(String ID, String EMAIL, String password, String badgeNum, String name, String phoneExtension, int rank) {
        super(ID, EMAIL, 2, password);
        this.badgeNum = badgeNum;
        this.name = name;
        this.phoneExtension = phoneExtension;
        this.rank = rank;
    }

    public String getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(String badgeNum) {
        this.badgeNum = badgeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
