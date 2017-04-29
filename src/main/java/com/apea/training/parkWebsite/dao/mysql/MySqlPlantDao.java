package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.domain.Plant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlPlantDao implements PlantDao {

    MySqlPlantDao() {
    }

    @Override
    public void create(Plant plant) {
        String sqlStatement = "INSERT INTO plant (name, state, imgPath, description, areaId)" +
                "VALUES (?, ?, ?, ?, ?)";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, plant.getName());
            statement.setString(2, plant.getState().toString());
            statement.setString(3, plant.getImgPath());
            statement.setString(4, plant.getDescription());
            statement.setInt(5, plant.getAreaId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating plant failed: no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating plantarea failed: no id obtained.");
            }
            Integer id = generatedKeys.getInt(1);
            plant.setId(id);
            generatedKeys.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create plant", e);
        }
    }

    @Override
    public Plant getById(Integer id) {
        String sqlStatement = "SELECT * FROM plant WHERE id = ?";
        Plant plant;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get plant", e);
        }
        return plant;
    }

    @Override
    public void update(Plant plant) {
        String sqlStatement = "UPDATE plant SET name = ?, state = ?, imgPath = ?, description = ?," +
                "areaId = ? WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, plant.getName());
            statement.setString(2, plant.getState().toString());
            statement.setString(3, plant.getImgPath());
            statement.setString(4, plant.getDescription());
            statement.setInt(5, plant.getAreaId());
            statement.setInt(6, plant.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating plant failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't update plant", e);
        }
    }

    public void delete(Plant plant) {
        String sqlStatement = "DELETE FROM plant WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, plant.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting report failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't delete plant", e);
        }
    }

    @Override
    public List<Plant> getAll() {
        String sqlStatement = "SELECT * FROM plant";
        List<Plant> plants = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String state = resultSet.getString("state");
                String imgPath = resultSet.getString("imgPath");
                String description = resultSet.getString("description");
                Integer areaId = resultSet.getInt("areaId");
                Plant plant = new Plant.Builder().setId(id).setName(name).setState(Plant.State.valueOf(state))
                        .setImgPath(imgPath).setDescription(description).setAreaId(areaId).build();
                plants.add(plant);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get all plants.", e);
        }
        return plants;
    }

    @Override
    public List<Plant> getAllOn(Integer areaId) {
        String sqlStatement = "SELECT * FROM plant WHERE areaId = ?";
        List<Plant> plants = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
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
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get all plants on area with id " + areaId, e);
        }
        return plants;
    }

    @Override
    public List<Integer> getAssociatedTaskIds(Plant plant) {
        String sqlStatement = "SELECT * FROM plant_task WHERE plantId = ?";
        List<Integer> associatedTaskIds = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, plant.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                associatedTaskIds.add(resultSet.getInt("taskId"));
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get associated task ids", e);
        }
        return associatedTaskIds;
    }
}