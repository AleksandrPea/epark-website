package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.AreaDao;
import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.domain.Area;

import java.sql.*;

public class MySqlAreaDao implements AreaDao {

    private Connection connection;

    MySqlAreaDao(Connection connection) {
        this.connection = connection;
    }

    public Area getByName(String name) {
        Area area;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM area " +
                    "WHERE name='"+name+"'");
            if (!resultSet.next()) {
                throw new DaoException("Area with name " + name + " doesn't exist");
            }
            Integer id = resultSet.getInt("id");
            area = new Area(id, name);
            String description = resultSet.getString("description");
            area.setDescription(description);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return area;
    }

    public Area getById(Integer id) {
        Area area;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM area " +
                    "WHERE id=" + id);
            if (!resultSet.next()) {
                throw new DaoException("Area with id " + id + " doesn't exist");
            }
            String name = resultSet.getString("name");
            area = new Area(id, name);
            String description = resultSet.getString("description");
            area.setDescription(description);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return area;
    }

    public void update(Area area) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE area " +
                    "SET name = ?, description = ? WHERE id = ?");
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setInt(3, area.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating area failed.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
