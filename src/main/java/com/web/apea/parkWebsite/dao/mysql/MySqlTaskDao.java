package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.dao.TaskDao;
import com.web.apea.parkWebsite.domain.Task;

import java.sql.*;
import java.time.Instant;

public class MySqlTaskDao implements TaskDao {

    private Connection connection;

    MySqlTaskDao(Connection connection) {
        this.connection = connection;
    }

    public Task create() {
        String sqlStatement = "INSERT INTO state (state, comment) VALUES ('NEW', '')";
        Task task;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating task failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating task failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            long epochMilli = generatedKeys.getTimestamp("creationDate").getTime();
            task = new Task(id, Task.State.NEW, Instant.ofEpochMilli(epochMilli));
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create task", e);
        }
        return task;
    }

    public Task getById(Integer id) {
        String sqlStatement = "SELECT * FROM task WHERE id = ?";
        Task task;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Task with id " + id + " doesn't exist");
            }
            String comment = resultSet.getString("comment");
            String state = resultSet.getString("state");
            long epochMilli = resultSet.getTimestamp("creationDate").getTime();
            task = new Task(id, Task.State.valueOf(state), Instant.ofEpochMilli(epochMilli));
            task.setComment(comment);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get task", e);
        }
        return task;
    }

    public void update(Task task) {
        String sqlStatement = "UPDATE task SET state = ?, comment = ?, WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, task.getState().toString());
            statement.setString(2, task.getComment());
            statement.setInt(3, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating task failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update task", e);
        }
    }

    public void delete(Task task) {
        String sqlStatement = "DELETE FROM task WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting task failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete task", e);
        }
    }
}
