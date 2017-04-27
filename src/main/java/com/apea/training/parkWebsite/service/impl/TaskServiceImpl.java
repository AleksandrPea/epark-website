package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.PlantDao;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private DaoFactory factory;

    TaskServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void createNewAndAssociate(Task task, List<Plant> plants) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            connection.beginTransaction();
            TaskDao taskDao = factory.getTaskDao(connection);
            taskDao.create(task);
            plants.forEach(plant -> taskDao.associate(task, plant.getId()));
            connection.commitTransaction();
        }
    }

    @Override
    public Task getById(Integer id) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getTaskDao(connection).getById(id);
        }
    }

    @Override
    public List<Task> getUserTasks(User user) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getTaskDao(connection).getUserTasks(user.getId());
        }
    }

    @Override
    public void delete(Task task) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getTaskDao(connection).delete(task);
        }
    }

    @Override
    public List<Plant> getAssociatedPlants(Task task) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            PlantDao plantDao = factory.getPlantDao(connection);
            return factory.getTaskDao(connection).getAssociatedPlantIds(task)
                    .stream()
                    .map(plantDao::getById)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void finishAndUpdatePlantStates(Task task, List<Plant> plants) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            connection.beginTransaction();
            task.setState(Task.State.FINISHED);
            factory.getTaskDao(connection).updateState(task);
            PlantDao plantDao = factory.getPlantDao(connection);
            plants.forEach(plantDao::update);
            connection.commitTransaction();
        }
    }

    @Override
    public void abort(Task task) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            task.setState(Task.State.INCOMPLETED);
            factory.getTaskDao(connection).updateState(task);
        }
    }

    @Override
    public void confirmRecieving(Task task) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            task.setState(Task.State.RUNNING);
            factory.getTaskDao(connection).updateState(task);
        }
    }
}
