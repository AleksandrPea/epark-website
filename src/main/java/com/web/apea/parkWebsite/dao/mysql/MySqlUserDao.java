package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.dao.UserDao;
import com.web.apea.parkWebsite.domain.User;

import java.sql.*;

public class MySqlUserDao implements UserDao {

    private Connection connection;

    MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

    public User getByLogin(String login) {
        User user;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user " +
                    "WHERE login='"+login+"'");
            if (!resultSet.next()) {
                throw new DaoException("User with login " + login + " doesn't exist");
            }
            String password = resultSet.getString("password");
            user = new User(login, password);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE user " +
                    "SET password = ? WHERE login = ?");
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating user failed.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

//
//    public void deleteByLogin(String login) throws SQLException {
//        if (login == null) {
//            throw new IllegalArgumentException("null login");
//        }
//        PreparedStatement statement = connection.prepareStatement("DELETE FROM user " +
//                "WHERE login = ?");
//        statement.setString(1, login);
//        statement.executeUpdate();
//    }
}
