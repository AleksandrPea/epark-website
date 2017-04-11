package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.User;

import java.sql.SQLException;

public interface UserDao {

    void persistNew(User user) throws SQLException;
    User getByLogin(String login);
    void update(User user) throws SQLException;
    void deleteByLogin(String login) throws SQLException;
}
