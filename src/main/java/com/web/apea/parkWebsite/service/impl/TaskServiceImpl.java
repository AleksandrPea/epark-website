package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connectionPool.ConnectionPool;
import com.web.apea.parkWebsite.connectionPool.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoException;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.PlantDao;
import com.web.apea.parkWebsite.dao.PlantTasksDao;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;
import com.web.apea.parkWebsite.service.ServiceException;
import com.web.apea.parkWebsite.service.TaskService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private ConnectionPool pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

    @Override
    public Task createBlank() {
        try (Connection connection = pool.getConnection()) {
            return factory.getTaskDao(connection).create();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Task getById(Integer id) {
        try (Connection connection = pool.getConnection()) {
            return factory.getTaskDao(connection).getById(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Plant> getAssociatedPlants(Integer taskId) {
        try (Connection connection = pool.getConnection()) {
            PlantDao plantDao = factory.getPlantDao(connection);
            return factory.getPlantTasksDao(connection).getAssociatedPlantsIds(taskId)
                    .stream()
                    .map(plantDao::getById)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Task task) {
        try (Connection connection = pool.getConnection()) {
            factory.getTaskDao(connection).update(task);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateAssociatedPlants(Integer taskId, List<Plant> newPlants) {
        Connection connection = pool.getConnection();
        try {
            connection.setAutoCommit(false);
            PlantTasksDao plantTasksDao = factory.getPlantTasksDao(connection);
            newPlants.forEach(plant -> plantTasksDao.createAssociation(taskId, plant.getId()));
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw new ServiceException(e);
        } catch (DaoException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw e;
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void delete(Task task) {

    }
}
