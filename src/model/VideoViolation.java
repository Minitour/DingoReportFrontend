package model;

import com.google.gson.annotations.Expose;


public class VideoViolation extends Violation {

    @Expose
    private int from;

    @Expose
    private int to;

    @Expose
    private String description;

    public VideoViolation(String alphaNum, String evidenceLink, ViolationType type, int from, int to, String description) {
        super(alphaNum, evidenceLink, type);
        this.from = from;
        this.to = to;
        this.description = description;
        setClassType(1);
    }



    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
