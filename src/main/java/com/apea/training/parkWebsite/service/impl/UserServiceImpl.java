package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.connection.AbstractConnectionImpl;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.service.UserService;

public class UserServiceImpl implements UserService {

    private ConnectionPool<AbstractConnectionImpl> pool;
    private DaoFactory factory;

    UserServiceImpl(ConnectionPool<AbstractConnectionImpl> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public User getByLogin(String login) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getUserDao(pool.getSqlConnection(connection)).getByLogin(login);
        }
    }

    @Override
    public void changePassword(String login, String newPassword) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getUserDao(pool.getSqlConnection(connection)).update(new User(login, newPassword));
        }
    }
}
