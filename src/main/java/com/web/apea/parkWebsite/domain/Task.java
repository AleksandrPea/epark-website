package com.web.apea.parkWebsite.domain;

import java.time.Instant;
import java.util.List;

public class Task {
    private Integer id;
    private State state;
    private String comment;
    private Instant creationDate;
    private List<Plant> plants;

    public Task(Integer id, State state, Instant creationDate) {
        this.id = id;
        this.state = state;
        this.creationDate = creationDate;
        comment = "";
    }

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

    public Instant getCreationDate() {
        return creationDate;
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
