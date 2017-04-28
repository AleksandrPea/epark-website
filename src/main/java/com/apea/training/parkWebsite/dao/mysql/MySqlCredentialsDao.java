package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.CredentialsDao;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.domain.Credentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCredentialsDao implements CredentialsDao {

    private Connection connection;

    MySqlCredentialsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Credentials credentials) {
        String sqlStatement = "INSERT INTO credentials (userId, login, password) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, credentials.getUserId());
            statement.setString(2, credentials.getLogin());
            statement.setString(3, credentials.getPassword());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating credentials failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create credentials", e);
        }
    }

    @Override
    public Credentials getByUserId(Integer userId) {
        String sqlStatement = "SELECT * FROM credentials WHERE userId = ?";
        Credentials credentials;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Credentials with user id " + userId + " don't exist");
            }
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            credentials = new Credentials(userId, login, password);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get credentials", e);
        }
        return credentials;
    }

    @Override
    public Credentials getByLogin(String login) {
        String sqlStatement = "SELECT * FROM credentials WHERE login = ?";
        Credentials credentials = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String password = resultSet.getString("password");
                credentials = new Credentials(userId, login, password);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get credentials", e);
        }
        return credentials;
    }

    @Override
    public List<Credentials> getAll() {
        String sqlStatement = "SELECT * FROM credentials";
        List<Credentials> credentialsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                Credentials credentials = new Credentials(userId, login, password);
                credentialsList.add(credentials);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return credentialsList;
    }

    @Override
    public void update(Credentials credentials) {
        String sqlStatement = "UPDATE credentials SET login = ?, password = ? WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, credentials.getLogin());
            statement.setString(2, credentials.getPassword());
            statement.setInt(3, credentials.getUserId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating credentials failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update credentials", e);
        }
    }
}
