package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.dao.PlantTasksDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPlantTasksDao implements PlantTasksDao {

    private Connection connection;

    MySqlPlantTasksDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createAssociation(Integer taskId, Integer plantId) {
        boolean isCreated = false;
        String sqlStatement = "INSERT INTO plant_task (taskId, plantId) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, taskId);
            statement.setInt(2, plantId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 0) {
                isCreated = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isCreated;
    }

    @Override
    public void deleteAssociation(Integer taskId, Integer plantId) {
        String sqlStatement = "DELETE FROM plant_task WHERE taskId = ? AND plantId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, taskId);
            statement.setInt(2, plantId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting relation failed.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Integer> getAssociatedTasksIds(Integer plantId) {
        String sqlStatement = "SELECT * FROM plant_task WHERE plantId = ?";
        List<Integer> taskIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, plantId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer taskId = resultSet.getInt("taskId");
                taskIds.add(taskId);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return taskIds;
    }

    @Override
    public List<Integer> getAssociatedPlantsIds(Integer taskId) {
        String sqlStatement = "SELECT * FROM plant_task WHERE taskId = ?";
        List<Integer> plantIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer plantId = resultSet.getInt("plantId");
                plantIds.add(plantId);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return plantIds;
    }
}
