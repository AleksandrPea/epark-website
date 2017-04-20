package com.apea.training.parkWebsite.domain;

public class Plant {
    private Integer id;
    private String name;
    private State state;
    private String imgPath = "";
    private String description = "";
    private Integer areaId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public enum State {
        SEEDLING, NORMAL, SICK, EXTRACTED;
    }

    public static class Builder {
        private Plant plant;

        public Builder() {
            plant = new Plant();
        }

        public Builder setId(Integer id) {
            plant.setId(id);
            return this;
        }

        public Builder setName(String name) {
            plant.setName(name);
            return this;
        }

        public Builder setState(State state) {
            plant.setState(state);
            return this;
        }

        public Builder setImgPath(String imgPath) {
            plant.setImgPath(imgPath);
            return this;
        }

        public Builder setDescription(String description) {
            plant.setDescription(description);
            return this;
        }

        public Builder setAreaId(Integer areaId) {
            plant.setAreaId(areaId);
            return this;
        }

        public Plant build() {
            return plant;
        }
    }
}
