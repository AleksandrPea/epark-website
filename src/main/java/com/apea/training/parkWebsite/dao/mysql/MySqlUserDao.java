package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserDao implements UserDao {

    private Connection connection;

    MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

    public User getByLogin(String login) {
        String sqlStatement = "SELECT * FROM user WHERE login = ?";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                user = new User(login, password);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get user by login " + login, e);
        }
        return user;
    }

    public void update(User user) {
        String sqlStatement = "UPDATE user SET password = ? WHERE login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating user failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

}
