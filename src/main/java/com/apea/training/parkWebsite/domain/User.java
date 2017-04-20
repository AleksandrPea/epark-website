package com.apea.training.parkWebsite.domain;

public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String info = "";
    private Integer superiorId;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getInfo() {
        return info;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public enum Role {
        OWNER, TASKMASTER, FORESTER;
    }

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setId(Integer id) {
            user.setId(id);
            return this;
        }

        public Builder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setRole(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder setInfo(String info) {
            user.setInfo(info);
            return this;
        }

        public Builder setSuperiorId(Integer superiorId) {
            user.setSuperiorId(superiorId);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
