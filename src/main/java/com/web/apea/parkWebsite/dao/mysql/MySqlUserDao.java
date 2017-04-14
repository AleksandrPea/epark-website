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
        String sqlStatement = "SELECT * FROM user WHERE login = ?";
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                user = new User(login, password);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public void update(User user) {
        String sqlStatement = "UPDATE user SET password = ? WHERE login = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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

}
