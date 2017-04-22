package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private DaoFactory factory;

    UserServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(User user) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getUserDao(connection).create(user);
        }
    }

    @Override
    public User getById(Integer id) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getUserDao(connection).getById(id);
        }
    }

    @Override
    public User getByLogin(String login) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getUserDao(connection).getByLogin(login);
        }
    }

    @Override
    public void update(User user) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getUserDao(connection).update(user);
        }
    }

    @Override
    public void delete(User user) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getUserDao(connection).delete(user);
        }
    }

    @Override
    public List<User> getAll() {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getUserDao(connection).getAll();
        }
    }

    @Override
    public List<User> getAllSubordinatesOf(User user) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getUserDao(connection).getAllSubordinatesOf(user);
        }
    }

    @Override
    public User getOwner() {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getUserDao(connection).getOwner();
        }
    }
}