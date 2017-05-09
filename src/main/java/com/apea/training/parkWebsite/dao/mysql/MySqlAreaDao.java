package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.AreaDao;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.domain.Area;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlAreaDao implements AreaDao {

    MySqlAreaDao() {}

    @Override
    public void create(Area area) {
        String sqlStatement = "INSERT INTO area (name, description, taskmasterId) VALUES (?,?,?)";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setObject(3, area.getTaskmasterId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating area failed: no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new DaoException("Creating area failed: no id obtained.");
            }
            Integer id = generatedKeys.getInt(1);
            area.setId(id);
            generatedKeys.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't create area", e);
        }
    }

    @Override
    public Area getById(Integer id) {
        String sqlStatement = "SELECT * FROM area WHERE id = ?";
        Area area;
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DaoException("Area with id " + id + " doesn't exist");
            }
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Integer taskmasterId = resultSet.getInt("taskmasterId");
            if (resultSet.wasNull()) {taskmasterId = null;}
            area = new Area.Builder().setId(id).setName(name).setDescription(description)
                    .setTaskmasterId(taskmasterId).build();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get area", e);
        }
        return area;
    }

    @Override
    public void update(Area area) {
        String sqlStatement = "UPDATE area SET name = ?, description = ?, taskmasterId = ? WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setString(1, area.getName());
            statement.setString(2, area.getDescription());
            statement.setInt(3, area.getTaskmasterId());
            statement.setInt(4, area.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Updating area failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't update area", e);
        }
    }

    @Override
    public void delete(Area area) {
        String sqlStatement = "DELETE FROM area WHERE id = ?";
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            statement.setInt(1, area.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting area failed: no rows affected.");
            }
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't delete area", e);
        }
    }

    @Override
    public List<Area> getAll() {
        String sqlStatement = "SELECT * FROM area";
        List<Area> areas = new ArrayList<>();
        try (DaoConnection connection = MySqlTransactionHelper.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Integer taskmasterId = resultSet.getInt("taskmasterId");
                if (resultSet.wasNull()) {taskmasterId = null;}
                Area area = new Area.Builder().setId(id).setName(name).setDescription(description)
                        .setTaskmasterId(taskmasterId).build();
                areas.add(area);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new DaoException("Can't get all areas", e);
        }
        return areas;
    }
}