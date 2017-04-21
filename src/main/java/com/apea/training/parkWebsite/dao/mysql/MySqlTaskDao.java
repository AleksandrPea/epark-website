package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.domain.Task;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MySqlTaskDao implements TaskDao {

    private Connection connection;

    MySqlTaskDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Task task) {
        String sqlStatement = "INSERT INTO task (state, comment, senderId, recieverId) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getState().toString());
            statement.setString(2, task.getComment());
            statement.setInt(3, task.getSenderId());
            statement.setInt(4, task.getRecieverId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating task failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating task failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            long creationDate = generatedKeys.getTimestamp("creationDate").getTime();
            task.setId(id);
            task.setCreationDate(Instant.ofEpochMilli(creationDate));
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create task", e);
        }
    }

    @Override
    public List<Integer> getAssociatedPlantIds(Task task) {
        String sqlStatement = "SELECT * FROM plant_task WHERE taskId = ?";
        List<Integer> associatedPlantIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, task.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                associatedPlantIds.add(resultSet.getInt("plantId"));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get associated plant ids", e);
        }
        return associatedPlantIds;
    }

    @Override
    public boolean associate(Task task, Integer plantId) {
        String sqlStatement = "INSERT INTO plant_task (taskId, plantId) VALUES (?, ?)";
        boolean isCreated;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, task.getId());
            statement.setInt(2, plantId);
            isCreated = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Can't associate task with plant", e);
        }
        return isCreated;
    }

    @Override
    public Task getById(Integer id) {
        String sqlStatement = "SELECT * FROM task WHERE id = ?";
        Task task;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Task with id " + id + " doesn't exist");
            }
            String state = resultSet.getString("state");
            String comment = resultSet.getString("comment");
            long creationDate = resultSet.getTimestamp("creationDate").getTime();
            Integer senderId = resultSet.getInt("senderId");
            Integer recieverId = resultSet.getInt("recieverId");
            task = new Task.Builder().setId(id).setState(Task.State.valueOf(state)).setComment(comment)
                    .setCreationDate(Instant.ofEpochMilli(creationDate)).setSenderId(senderId)
                    .setRecieverId(recieverId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get task", e);
        }
        return task;
    }

    @Override
    public void updateState(Task task) {
        String sqlStatement = "UPDATE task SET state = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, task.getState().toString());
            statement.setInt(2, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating state failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update state", e);
        }
    }

    @Override
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
