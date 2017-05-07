package com.apea.training.parkWebsite.domain;

import java.time.Instant;
import java.util.Date;

public class Report {

    private Integer id;
    private String comment = "";
    private String imgPath = "";
    private Instant creationTimestamp;
    private Integer taskId;

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Date getCreationDate() {
        return Date.from(creationTimestamp);
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public static class Builder {

        private Report report;

        public Builder() {
            report = new Report();
        }

        public Builder setId(Integer id) {
            report.setId(id);
            return this;
        }

        public Builder setComment(String comment) {
            report.setComment(comment);
            return this;
        }

        public Builder setImgPath(String imgPath) {
            report.setImgPath(imgPath);
            return this;
        }

        public Builder setCreationDate(Instant creationDate) {
            report.setCreationTimestamp(creationDate);
            return this;
        }

        public Builder setTaskId(Integer taskId) {
            report.setTaskId(taskId);
            return this;
        }

        public Report build() {
            return report;
        }
    }
}
