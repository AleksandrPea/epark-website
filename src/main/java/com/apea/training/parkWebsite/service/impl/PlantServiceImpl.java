package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.PlantService;

import java.util.List;
import java.util.stream.Collectors;

public class PlantServiceImpl implements PlantService {
    private DaoFactory factory;

    PlantServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void createNew(Plant plant) {
        plant.setState(Plant.State.SEEDLING);
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getPlantDao(connection).create(plant);
        }
    }

    @Override
    public Plant getById(Integer id) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getPlantDao(connection).getById(id);
        }
    }

    @Override
    public void update(Plant plant) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getPlantDao(connection).update(plant);
        }
    }

    @Override
    public void delete(Plant plant) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getPlantDao(connection).delete(plant);
        }
    }

    @Override
    public List<Plant> getAll() {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getPlantDao(connection).getAll();
        }
    }

    @Override
    public List<Plant> getAllOn(Area area) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getPlantDao(connection).getAllOn(area.getId());
        }
    }

    @Override
    public List<Task> getAssociatedTasks(Plant plant) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            TaskDao taskDao = factory.getTaskDao(connection);
            return factory.getPlantDao(connection).getAssociatedTaskIds(plant)
                    .stream()
                    .map(taskDao::getById)
                    .collect(Collectors.toList());
        }
    }
}