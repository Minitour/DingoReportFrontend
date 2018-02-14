package model;

import com.google.gson.annotations.Expose;

import java.util.Collection;

/**
 * Created by Antonio Zaitoun on 20/12/2017.
 */
public abstract class Violation{

    @Expose
    private String alphaNum;

    @Expose
    private Report report;

    @Expose
    private String evidenceLink;

    @Expose
    private ViolationType type;

    @Expose
    private Collection<Decision> decisions;

    @Expose
    private int classType = 0;

    public Violation(String alphaNum, String evidenceLink, ViolationType type) {
        this.alphaNum = alphaNum;
        this.evidenceLink = evidenceLink;
        this.type = type;
    }

    public String getAlphaNum() {
        return alphaNum;
    }

    public void setAlphaNum(String alphaNum) {
        this.alphaNum = alphaNum;
    }

    public String getEvidenceLink() {
        return evidenceLink;
    }

    public void setEvidenceLink(String evidenceLink) {
        this.evidenceLink = evidenceLink;
    }

    public ViolationType getType() {
        return type;
    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    public Collection<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(Collection<Decision> decisions) {
        this.decisions = decisions;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public int getClassType() {
        return classType;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }



}
