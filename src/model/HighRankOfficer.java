package model;

public class HighRankOfficer extends Officer {

    public HighRankOfficer(String ID, String EMAIL, String badgeNum, String name, String phoneExtension, int rank) {
        super(ID, EMAIL, badgeNum, name, phoneExtension, rank);
        setROLE_ID(1);
    }

    public HighRankOfficer(String ID, String EMAIL, String password, String badgeNum, String name, String phoneExtension, int rank) {
        super(ID, EMAIL, password, badgeNum, name, phoneExtension, rank);
        setROLE_ID(1);
    }
}
