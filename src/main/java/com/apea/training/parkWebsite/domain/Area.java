package com.apea.training.parkWebsite.domain;

public class Area {

    private Integer id;
    private String name;
    private String description = "";
    private Integer taskmasterId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getTaskmasterId() {
        return taskmasterId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskmasterId(Integer taskmasterId) {
        this.taskmasterId = taskmasterId;
    }

    public static class Builder {
        private Area area;

        public Builder() {
            area = new Area();
        }

        public Builder setId(Integer id) {
            area.setId(id);
            return this;
        }

        public Builder setName(String name) {
            area.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            area.setDescription(description);
            return this;
        }

        public Builder setTaskmasterId(Integer taskmasterId) {
            area.setTaskmasterId(taskmasterId);
            return this;
        }

        public Area build() {
            return area;
        }
    }
}
