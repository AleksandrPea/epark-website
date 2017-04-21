package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.Credentials;
import com.apea.training.parkWebsite.service.CredentialsService;

public class CredentialsServiceImpl implements CredentialsService {
    private DaoFactory factory;

    CredentialsServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Credentials credentials) {
        try(DaoConnection connection = factory.getDaoConnection()) {
            factory.getCredentialsDao(connection).create(credentials);
        }
    }

    @Override
    public Credentials getByUserId(Integer userId) {
        try(DaoConnection connection = factory.getDaoConnection()) {
            return factory.getCredentialsDao(connection).getByUserId(userId);
        }
    }

    @Override
    public Credentials getByLogin(String login) {
        try(DaoConnection connection = factory.getDaoConnection()) {
            return factory.getCredentialsDao(connection).getByLogin(login);
        }
    }

    @Override
    public void update(Credentials credentials) {
        try(DaoConnection connection = factory.getDaoConnection()) {
            factory.getCredentialsDao(connection).update(credentials);
        }
    }
}
