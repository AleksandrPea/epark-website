package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.domain.Plant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlPlantDao implements PlantDao {

    private Connection connection;

    MySqlPlantDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Plant plant) {
        String sqlStatement = "INSERT INTO plant (name, state, imgPath, description, areaId)" +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, plant.getName());
            statement.setString(2, plant.getState().toString());
            statement.setString(3, plant.getImgPath());
            statement.setString(4, plant.getDescription());
            statement.setInt(5, plant.getAreaId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating plant failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating plant failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            plant.setId(id);
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create plant", e);
        }
    }

    @Override
    public Plant getById(Integer id) {
        String sqlStatement = "SELECT * FROM plant WHERE id = ?";
        Plant plant;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Plant with id " + id + " doesn't exist");
            }
            String name = resultSet.getString("name");
            String state = resultSet.getString("state");
            String imgPath = resultSet.getString("imgPath");
            String description = resultSet.getString("description");
            Integer areaId = resultSet.getInt("areaId");
            plant = new Plant.Builder().setId(id).setName(name).setState(Plant.State.valueOf(state))
                    .setImgPath(imgPath).setDescription(description).setAreaId(areaId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get plant", e);
        }
        return plant;
    }

    @Override
    public void update(Plant plant) {
        String sqlStatement = "UPDATE plant SET name = ?, state = ?, imgPath = ?, description = ?," +
                "areaId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, plant.getName());
            statement.setString(2, plant.getState().toString());
            statement.setString(3, plant.getImgPath());
            statement.setString(4, plant.getDescription());
            statement.setInt(5, plant.getAreaId());
            statement.setInt(6, plant.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating plant failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update plant", e);
        }
    }

    public void delete(Plant plant) {
        String sqlStatement = "DELETE FROM plant WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, plant.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting report failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete plant", e);
        }
    }

    @Override
    public List<Plant> getAllOn(Integer areaId) {
        String sqlStatement = "SELECT * FROM plant WHERE areaId = ?";
        List<Plant> plants = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String state = resultSet.getString("state");
                String imgPath = resultSet.getString("imgPath");
                String description = resultSet.getString("description");
                Plant plant = new Plant.Builder().setId(id).setName(name).setState(Plant.State.valueOf(state))
                        .setImgPath(imgPath).setDescription(description).setAreaId(areaId).build();
                plants.add(plant);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return plants;
    }

    @Override
    public List<Integer> getAssociatedTaskIds(Plant plant) {
        String sqlStatement = "SELECT * FROM plant_task WHERE plantId = ?";
        List<Integer> associatedTaskIds = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, plant.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                associatedTaskIds.add(resultSet.getInt("taskId"));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get associated task ids", e);
        }
        return associatedTaskIds;
    }
}