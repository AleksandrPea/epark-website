package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.PlantDao;
import com.web.apea.parkWebsite.dao.PlantTasksDao;
import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;
import com.web.apea.parkWebsite.service.TaskService;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private ConnectionPool<AbstractConnectionImpl> pool;
    private DaoFactory factory;

    TaskServiceImpl(ConnectionPool<AbstractConnectionImpl> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Task createBlank() {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getTaskDao(pool.getSqlConnection(connection)).create();
        }
    }

    @Override
    public Task getById(Integer id) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getTaskDao(pool.getSqlConnection(connection)).getById(id);
        }
    }

    @Override
    public List<Plant> getAssociatedPlants(Integer taskId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            Connection sqlConn = pool.getSqlConnection(connection);
            PlantDao plantDao = factory.getPlantDao(sqlConn);
            return factory.getPlantTasksDao(sqlConn).getAssociatedPlantsIds(taskId)
                    .stream()
                    .map(plantDao::getById)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void update(Task task) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getTaskDao(pool.getSqlConnection(connection)).update(task);
        }
    }

    @Override
    public void updateAssociationsFor(Integer taskId, List<Plant> newPlants) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            connection.beginTransaction();
            PlantTasksDao plantTasksDao = factory.getPlantTasksDao(pool.getSqlConnection(connection));
            newPlants.forEach(plant -> plantTasksDao.createAssociation(taskId, plant.getId()));
            connection.commitTransaction();
        }
    }

    @Override
    public void delete(Task task) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            connection.beginTransaction();
            Connection sqlConn = pool.getSqlConnection(connection);
            factory.getPlantTasksDao(sqlConn).deleteAssociationsForTask(task.getId());
            factory.getTaskDao(sqlConn).delete(task);
            connection.commitTransaction();
        }
    }
}