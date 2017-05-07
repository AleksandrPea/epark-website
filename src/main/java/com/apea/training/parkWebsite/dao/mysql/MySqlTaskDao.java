package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.domain.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MySqlTaskDao implements TaskDao {

    MySqlTaskDao(){}

    @Override
    public void create(Task task) {
        String sqlStatement = "INSERT INTO task (state, title, comment, senderId, receiverId) VALUES (?,?,?,?,?)";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getState().toString());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getComment());
            statement.setInt(4, task.getSenderId());
            statement.setInt(5, task.getReceiverId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating task failed: no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating task failed: no id obtained.");
            }
            Integer id = generatedKeys.getInt(1);
            task.setId(id);
            generatedKeys.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create task", e);
        }
    }

    @Override
    public List<Integer> getAssociatedPlantIds(Task task) {
        String sqlStatement = "SELECT * FROM plant_task WHERE taskId = ?";
        List<Integer> associatedPlantIds = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, task.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                associatedPlantIds.add(resultSet.getInt("plantId"));
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get associated plant ids", e);
        }
        return associatedPlantIds;
    }

    @Override
    public boolean associate(Task task, Integer plantId) {
        String sqlStatement = "INSERT INTO plant_task (taskId, plantId) VALUES (?, ?)";
        boolean isCreated;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, task.getId());
            statement.setInt(2, plantId);
            isCreated = statement.executeUpdate() != 0;
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't associate task with plant", e);
        }
        return isCreated;
    }

    @Override
    public Task getById(Integer id) {
        String sqlStatement = "SELECT * FROM task WHERE id = ?";
        Task task;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Task with id " + id + " doesn't exist");
            }
            String state = resultSet.getString("state");
            String title = resultSet.getString("title");
            String comment = resultSet.getString("comment");
            long creationDate = resultSet.getTimestamp("creationDate").getTime();
            Integer senderId = resultSet.getInt("senderId");
            if (resultSet.wasNull()) {senderId = null;}
            Integer receiverId = resultSet.getInt("receiverId");
            if (resultSet.wasNull()) {receiverId = null;}
            task = new Task.Builder().setId(id).setState(Task.State.valueOf(state)).setTitle(title)
                    .setComment(comment).setCreationDate(Instant.ofEpochMilli(creationDate))
                    .setSenderId(senderId).setReceiverId(receiverId).build();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get task", e);
        }
        return task;
    }

    @Override
    public void updateState(Task task) {
        String sqlStatement = "UPDATE task SET state = ? WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, task.getState().toString());
            statement.setInt(2, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating state failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't update state", e);
        }
    }

    @Override
    public void delete(Task task) {
        String sqlStatement = "DELETE FROM task WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, task.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting task failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't delete task", e);
        }
    }

    @Override
    public List<Task> getUserTasks(Integer userId) {
        String sqlStatement = "SELECT * FROM task WHERE senderId = ? OR receiverId = ?";
        List<Task> tasks = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String state = resultSet.getString("state");
                String title = resultSet.getString("title");
                String comment = resultSet.getString("comment");
                long creationDate = resultSet.getTimestamp("creationDate").getTime();
                Integer senderId = resultSet.getInt("senderId");
                if (resultSet.wasNull()) {senderId = null;}
                Integer receiverId = resultSet.getInt("receiverId");
                if (resultSet.wasNull()) {receiverId = null;}
                Task task = new Task.Builder().setId(id).setState(Task.State.valueOf(state)).setTitle(title)
                        .setComment(comment).setCreationDate(Instant.ofEpochMilli(creationDate))
                        .setSenderId(senderId).setReceiverId(receiverId).build();
                tasks.add(task);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get all tasks of user with id "+userId, e);
        }
        return tasks;
    }
}