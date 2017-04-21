package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.UserDao;
import com.apea.training.parkWebsite.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {

    private Connection connection;

    MySqlUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        String sqlStatement = "INSERT INTO user (firstName, lastName, email, role, info, superiorId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().toString());
            statement.setString(5, user.getInfo());
            statement.setInt(6, user.getSuperiorId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating user failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating user failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            user.setId(id);
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create user", e);
        }
    }

    @Override
    public User getById(Integer id) {
        String sqlStatement = "SELECT * FROM user WHERE id = ?";
        User user;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("User with id " + id + " doesn't exist");
            }
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String role = resultSet.getString("role");
            String info = resultSet.getString("info");
            Integer superiorId = resultSet.getInt("superiorId");
            user = new User.Builder().setId(id).setFirstName(firstName).setLastName(lastName)
                    .setEmail(email).setRole(User.Role.valueOf(role)).setInfo(info)
                    .setSuperiorId(superiorId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get user", e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        String sqlStatement = "UPDATE user SET firstName = ?, lastName = ?, email = ?, " +
                "role = ?, info = ?, superiorId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().toString());
            statement.setString(5, user.getInfo());
            statement.setInt(6, user.getSuperiorId());
            statement.setInt(7, user.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating user failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public void delete(User user) {
        String sqlStatement = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, user.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting user failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete user", e);
        }
    }

    @Override
    public List<User> getAllSubordinatesOf(User user) {
        String sqlStatement = "SELECT * FROM user WHERE superiorId = ?";
        List<User> subordinates = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String info = resultSet.getString("info");
                User subordinate = new User.Builder().setId(id).setFirstName(firstName).setLastName(lastName)
                        .setEmail(email).setRole(User.Role.valueOf(role)).setInfo(info)
                        .setSuperiorId(user.getId()).build();
                subordinates.add(subordinate);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return subordinates;
    }

    @Override
    public User getOwner() {
        String sqlStatement = "SELECT * FROM user WHERE role = 'OWNER'";
        User user;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            ResultSet resultSet = statement.executeQuery();
            Integer id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            String info = resultSet.getString("info");
            Integer superiorId = resultSet.getInt("superiorId");
            user = new User.Builder().setId(id).setFirstName(firstName).setLastName(lastName)
                    .setEmail(email).setRole(User.Role.OWNER).setInfo(info)
                    .setSuperiorId(superiorId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get owner", e);
        }
        return user;
    }

}