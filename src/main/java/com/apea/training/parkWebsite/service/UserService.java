package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;

import java.util.List;

public interface UserService {

    void create(User user, Credential credential);

    User getById(Integer id);

    User getByLogin(String login);

    void update(User user);

    void delete(User user);

    List<User> getAll();

    List<User> getAllSubordinatesOf(User user);

    User getOwner();

    Area getAttachedArea(User user);
}
