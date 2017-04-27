package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Credentials;
import com.apea.training.parkWebsite.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void create(User user, Credentials credentials);

    User getById(Integer id);

    User getByLogin(String login);

    void update(User user);

    void delete(User user);

    List<User> getAll();

    List<User> getAllSubordinatesOf(User user);

    User getOwner();
}
