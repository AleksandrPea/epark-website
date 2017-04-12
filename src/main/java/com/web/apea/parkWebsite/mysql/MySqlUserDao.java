package com.web.apea.parkWebsite.mysql;

import com.web.apea.parkWebsite.dao.UserDao;
import com.web.apea.parkWebsite.domain.User;

import java.sql.*;

public class MySqlUserDao implements UserDao {

    private Connection connection;

    MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

    public void persistNew(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        PreparedStatement statement = connection.prepareStatement("INSERT INTO user " +
                "(login, password) VALUES (?, ?)");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
    }

    public User getByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("null login");
        }
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user " +
                    "WHERE login='"+login+"'");
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                user = new User(login, password);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace(); // LOGGER
        }
        return user;
    }

    public void update(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("null user");
        }
        PreparedStatement statement = connection.prepareStatement("UPDATE user " +
                "SET password = ? WHERE login = ?");
        statement.setString(1, user.getPassword());
        statement.setString(2, user.getLogin());
        statement.executeUpdate();
    }


    public void deleteByLogin(String login) throws SQLException {
        if (login == null) {
            throw new IllegalArgumentException("null login");
        }
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user " +
                "WHERE login = ?");
        statement.setString(1, login);
        statement.executeUpdate();
    }
}
