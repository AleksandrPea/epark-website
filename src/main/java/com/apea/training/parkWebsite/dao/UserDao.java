package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.User;

import java.util.List;

public interface UserDao {

    void create(User user);

    User getById(Integer id);

    void update(User user);

    void delete(User report);

    List<User> getAllSubordinatesOf(User user);

    User getOwner();
}
