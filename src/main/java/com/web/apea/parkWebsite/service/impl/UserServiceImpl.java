package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.connection.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.domain.User;
import com.web.apea.parkWebsite.service.UserService;

public class UserServiceImpl implements UserService {

    private ConnectionPool<AbstractConnectionImpl> pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

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
