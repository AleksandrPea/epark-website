package com.web.apea.parkWebsite.domain;

public class Report {

    private Integer id;
    private String comment;
    private String imgPath;

    public Report(Integer id) {
        this.id = id;
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
}
