package com.apea.training.parkWebsite.domain;

import java.time.Instant;

public class Report {

    private Integer id;
    private String comment = "";
    private String imgPath = "";
    private Instant creationDate;
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

    public Instant getCreationDate() {
        return creationDate;
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

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
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
            report.setCreationDate(creationDate);
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
