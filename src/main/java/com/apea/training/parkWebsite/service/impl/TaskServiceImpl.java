package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.TaskService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private ConnectionPool<MySqlDaoConnection> pool;
    private DaoFactory factory;

    TaskServiceImpl(ConnectionPool<MySqlDaoConnection> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Task createBlank() {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            return factory.getTaskDao(pool.getSqlConnectionFrom(connection)).create();
        }
    }

    @Override
    public Task getById(Integer id) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            return factory.getTaskDao(pool.getSqlConnectionFrom(connection)).getById(id);
        }
    }

    @Override
    public List<Plant> getAssociatedPlants(Integer taskId) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            Connection sqlConn = pool.getSqlConnectionFrom(connection);
            PlantDao plantDao = factory.getPlantDao(sqlConn);
            return factory.getPlantTasksDao(sqlConn).getAssociatedPlantsIds(taskId)
                    .stream()
                    .map(plantDao::getById)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void update(Task task) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            factory.getTaskDao(pool.getSqlConnectionFrom(connection)).update(task);
        }
    }

    @Override
    public void updateAssociationsFor(Integer taskId, List<Plant> newPlants) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            connection.beginTransaction();
            PlantTasksDao plantTasksDao = factory.getPlantTasksDao(pool.getSqlConnectionFrom(connection));
            newPlants.forEach(plant -> plantTasksDao.createAssociation(taskId, plant.getId()));
            connection.commitTransaction();
        }
    }

    @Override
    public void delete(Task task) {
        try (MySqlDaoConnection connection = pool.getConnection()) {
            connection.beginTransaction();
            Connection sqlConn = pool.getSqlConnectionFrom(connection);
            factory.getPlantTasksDao(sqlConn).deleteAssociationsForTask(task.getId());
            factory.getTaskDao(sqlConn).delete(task);
            connection.commitTransaction();
        }
    }
}
