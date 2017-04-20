package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.PlantService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class PlantServiceImpl implements PlantService {
    private ConnectionPool<MySqlDaoConnection> pool;
    private DaoFactory factory;

    PlantServiceImpl(ConnectionPool<MySqlDaoConnection> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Plant createOn(Integer areaId) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnectionFrom(connection)).createOn(areaId);
        }
    }

    @Override
    public Plant getById(Integer id) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnectionFrom(connection)).getById(id);
        }
    }

    @Override
    public List<Task> getAssociatedTasks(Integer plantId) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            Connection sqlConn = pool.getSqlConnectionFrom(connection);
            TaskDao taskDao = factory.getTaskDao(sqlConn);
            return factory.getPlantTasksDao(sqlConn).getAssociatedTasksIds(plantId)
                    .stream()
                    .map(taskDao::getById)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void update(Plant plant) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            factory.getPlantDao(pool.getSqlConnectionFrom(connection)).update(plant);
        }
    }

    @Override
    public void delete(Plant plant) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            connection.beginTransaction();
            Connection sqlConn = pool.getSqlConnectionFrom(connection);
            factory.getPlantTasksDao(sqlConn).deleteAssociationsForPlant(plant.getId());
            factory.getPlantDao(sqlConn).delete(plant);
            connection.commitTransaction();
        }
    }

    @Override
    public List<Plant> getAllOn(Integer areaId) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            return factory.getPlantDao(pool.getSqlConnectionFrom(connection)).getAllOn(areaId);
        }
    }
}