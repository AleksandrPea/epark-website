package com.web.apea.parkWebsite.mysql;

import com.web.apea.parkWebsite.dao.AreaDao;
import com.web.apea.parkWebsite.domain.Area;

import java.sql.*;

public class MySqlAreaDao implements AreaDao {

    private Connection connection;

    MySqlAreaDao(Connection connection) {
        this.connection = connection;
    }

    public Area getByName(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("null name");
        }
        Area area;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM area " +
                "WHERE name='"+name+"'");
        if (!resultSet.next()) {
            throw new SQLException("area with name " + name + " doesn't exist");
        }
        Integer id = resultSet.getInt("id");
        area = new Area(id, name);
        String description = resultSet.getString("description");
        area.setDescription(description);
        resultSet.close();
        return area;
    }

    public Area getById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("null id");
        }
        Area area;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM area " +
                "WHERE id="+id);
        if (!resultSet.next()) {
            throw new SQLException("area with id " + id + " doesn't exist");
        }
        String name = resultSet.getString("name");
        area = new Area(id, name);
        String description = resultSet.getString("description");
        area.setDescription(description);
        resultSet.close();
        return area;
    }

    public void update(Area area) throws SQLException {
        if (area == null) {
            throw new IllegalArgumentException("null area");
        }
        PreparedStatement statement = connection.prepareStatement("UPDATE area " +
                "SET name = ?, description = ? WHERE id = ?");
        statement.setString(1, area.getName());
        statement.setString(2, area.getDescription());
        statement.setInt(3, area.getId());
        statement.executeUpdate();

    }
}
