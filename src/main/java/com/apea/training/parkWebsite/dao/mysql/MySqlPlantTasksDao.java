package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.PlantTasksDao;

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
            throw new DaoException("Can't create plant-task association",e);
        }
        return isCreated;
    }

    @Override
    public int deleteAssociationsForTask(Integer taskId) {
        String sqlStatement = "DELETE FROM plant_task WHERE taskId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, taskId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't delete associations for task", e);
        }
    }

    @Override
    public int deleteAssociationsForPlant(Integer plantId) {
        String sqlStatement = "DELETE FROM plant_task WHERE plantId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, plantId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't delete associations for plant", e);
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
            throw new DaoException("Can't get associations for plant", e);
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
            throw new DaoException("Can't get associations for task", e);
        }
        return plantIds;
    }
}
