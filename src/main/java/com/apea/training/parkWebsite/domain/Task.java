package com.apea.training.parkWebsite.domain;

import java.time.Instant;

public class Task {
    private Integer id;
    private State state;
    private String title;
    private String comment;
    private Instant creationDate;
    private Integer senderId;
    private Integer recieverId;

    public Integer getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getRecieverId() {
        return recieverId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setRecieverId(Integer recieverId) {
        this.recieverId = recieverId;
    }

    public enum State {
        NEW, RUNNING, FINISHED, INCOMPLETED;
    }

    public static class Builder {

        private Task task;

        public Builder() {
            task = new Task();
        }

        public Builder setId(Integer id) {
            task.setId(id);
            return this;
        }

        public Builder setState(State state) {
            task.setState(state);
            return this;
        }

        public Builder setTitle(String title) {
            task.setTitle(title);
            return this;
        }

        public Builder setComment(String comment) {
            task.setComment(comment);
            return this;
        }

        public Builder setCreationDate(Instant creationDate) {
            task.setCreationDate(creationDate);
            return this;
        }

        public Builder setSenderId(Integer senderId) {
            task.setSenderId(senderId);
            return this;
        }

        public Builder setRecieverId(Integer recieverId) {
            task.setRecieverId(recieverId);
            return this;
        }

        public Task build() {
            return task;
        }
    }
}
