package model;

import com.google.gson.annotations.Expose;

/**
 * Created by Antonio Zaitoun on 20/12/2017.
 */
public class Decision{
    @Expose
    private Officer officer;

    @Expose
    private Violation violation;

    @Expose
    private int decision;

    public Decision(int decision) {
        this.decision = decision;
    }

    public Officer getOfficer() {
        return officer;
    }

    public void setOfficer(Officer officer) {
        this.officer = officer;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }
}
