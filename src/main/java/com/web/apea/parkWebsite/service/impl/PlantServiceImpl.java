package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.TaskDao;
import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;
import com.web.apea.parkWebsite.service.PlantService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class PlantServiceImpl implements PlantService {
    private ConnectionPool<AbstractConnectionImpl> pool;
    private DaoFactory factory;

    PlantServiceImpl(ConnectionPool<AbstractConnectionImpl> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Plant createOn(Integer areaId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnection(connection)).createOn(areaId);
        }
    }

    @Override
    public Plant getById(Integer id) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnection(connection)).getById(id);
        }
    }

    @Override
    public List<Task> getAssociatedTasks(Integer plantId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            Connection sqlConn = pool.getSqlConnection(connection);
            TaskDao taskDao = factory.getTaskDao(sqlConn);
            return factory.getPlantTasksDao(sqlConn).getAssociatedTasksIds(plantId)
                    .stream()
                    .map(taskDao::getById)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void update(Plant plant) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getPlantDao(pool.getSqlConnection(connection)).update(plant);
        }
    }

    @Override
    public void delete(Plant plant) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            connection.beginTransaction();
            Connection sqlConn = pool.getSqlConnection(connection);
            factory.getPlantTasksDao(sqlConn).deleteAssociationsForPlant(plant.getId());
            factory.getPlantDao(sqlConn).delete(plant);
            connection.commitTransaction();
        }
    }

    @Override
    public List<Plant> getAllOn(Integer areaId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnection(connection)).getAllOn(areaId);
        }
    }
}