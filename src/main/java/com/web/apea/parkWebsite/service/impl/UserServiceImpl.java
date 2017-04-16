package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connectionPool.ConnectionPool;
import com.web.apea.parkWebsite.connectionPool.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.domain.User;
import com.web.apea.parkWebsite.service.ServiceException;
import com.web.apea.parkWebsite.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private ConnectionPool pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

    @Override
    public User getByLogin(String login) {
        try (Connection connection = pool.getConnection()) {
            return factory.getUserDao(connection).getByLogin(login);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changePassword(String login, String newPassword) {
        try (Connection connection = pool.getConnection()) {
            factory.getUserDao(connection).update(new User(login, newPassword));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
