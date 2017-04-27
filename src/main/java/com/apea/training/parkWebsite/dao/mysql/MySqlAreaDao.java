package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.AreaDao;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.domain.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAreaDao implements AreaDao {

    private Connection connection;

    MySqlAreaDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Area area) {
        String sqlStatement = "INSERT INTO area (name, description, taskmasterId) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setInt(3, area.getTaskmasterId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating area failed.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating area failed, no ID obtained.");
            }
            Integer id = generatedKeys.getInt("id");
            area.setId(id);
            generatedKeys.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create area", e);
        }
    }

    @Override
    public Area getByName(String name) {
        String sqlStatement = "SELECT * FROM area WHERE name = ?";
        Area area;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Area with name " + name + " doesn't exist");
            }
            Integer id = resultSet.getInt("id");
            String description = resultSet.getString("description");
            Integer taskmasterId = resultSet.getInt("taskmasterId");
            area = new Area.Builder().setId(id).setName(name).setDescription(description)
                    .setTaskmasterId(taskmasterId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get area by name "+name, e);
        }
        return area;
    }

    @Override
    public Area getById(Integer id) {
        String sqlStatement = "SELECT * FROM area WHERE id = ?";
        Area area;
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Area with id " + id + " doesn't exist");
            }
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Integer taskmasterId = resultSet.getInt("taskmasterId");
            area = new Area.Builder().setId(id).setName(name).setDescription(description)
                    .setTaskmasterId(taskmasterId).build();
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get area", e);
        }
        return area;
    }

    @Override
    public void update(Area area) {
        String sqlStatement = "UPDATE area SET name = ?, description = ?, taskmasterId = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setInt(3, area.getTaskmasterId());
            statement.setInt(4, area.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating area failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update area", e);
        }
    }

    @Override
    public void delete(Area area) {
        String sqlStatement = "DELETE FROM area WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, area.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting area failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't delete area", e);
        }
    }

    @Override
    public List<Area> getAll() {
        String sqlStatement = "SELECT * FROM area";
        List<Area> areas = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Integer taskmasterId = resultSet.getInt("taskmasterId");
                Area area = new Area.Builder().setId(id).setName(name).setDescription(description)
                        .setTaskmasterId(taskmasterId).build();
                areas.add(area);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return areas;
    }
}
