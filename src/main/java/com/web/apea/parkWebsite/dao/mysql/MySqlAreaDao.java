package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.AreaDao;
import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.domain.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAreaDao implements AreaDao {

    private Connection connection;

    MySqlAreaDao(Connection connection) {
        this.connection = connection;
    }

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
            area = new Area(id, name);
            String description = resultSet.getString("description");
            area.setDescription(description);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get area by name "+name, e);
        }
        return area;
    }

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
            area = new Area(id, name);
            String description = resultSet.getString("description");
            area.setDescription(description);
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get area", e);
        }
        return area;
    }

    public void update(Area area) {
        String sqlStatement = "UPDATE area SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setInt(3, area.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating area failed.");
            }
        } catch (SQLException e) {
            throw new DaoException("Can't update area", e);
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
                Area area = new Area(id, name);
                String description = resultSet.getString("description");
                area.setDescription(description);
                areas.add(area);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return areas;
    }
}
