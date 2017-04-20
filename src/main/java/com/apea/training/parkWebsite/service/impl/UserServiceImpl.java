package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.service.UserService;

public class UserServiceImpl implements UserService {

    private ConnectionPool<MySqlDaoConnection> pool;
    private DaoFactory factory;

    UserServiceImpl(ConnectionPool<MySqlDaoConnection> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public User getByLogin(String login) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getUserDao(pool.getSqlConnectionFrom(connection)).getByLogin(login);
        }
    }

    @Override
    public void changePassword(String login, String newPassword) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            factory.getUserDao(pool.getSqlConnectionFrom(connection)).update(new User(login, newPassword));
        }
    }
}
