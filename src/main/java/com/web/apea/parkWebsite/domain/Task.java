package com.web.apea.parkWebsite.domain;

import java.util.Date;
import java.util.List;

public class Task {
    private Integer id;
    private State state;
    private String comment;
    private Date creationDate;
    private Report report;
    private List<Plant> plants;

    public Integer getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public enum State {
        NEW, RUNNING, UNCONFIRMED, FINISHED, REJECTED;
    }
}
