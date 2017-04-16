package com.web.apea.parkWebsite.domain;

import java.util.List;

public class Plant {
    private Integer id;
    private String name;
    private State state;
    private String imgPath;
    private String description;
    private Integer areaId;

    public Plant(Integer id, String name, State state, Integer areaId) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.areaId = areaId;
        imgPath = "";
        description = "";
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public enum State {
        SEEDLING, NORMAL, SICK, REMOVED;
    }
}
