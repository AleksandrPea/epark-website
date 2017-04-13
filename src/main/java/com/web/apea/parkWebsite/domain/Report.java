package com.web.apea.parkWebsite.domain;

public class Report {

    private Integer id;
    private String comment;
    private String imgPath;
    private Integer taskId;

    public Report(Integer id, Integer taskId) {
        this.id = id;
        this.taskId = taskId;
        comment = "";
        imgPath = "";
    }

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getTaskId() {
        return taskId;
    }
}
