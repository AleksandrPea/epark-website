package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.TaskDao;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.PlantService;

import java.util.List;
import java.util.stream.Collectors;

public class PlantServiceImpl implements PlantService {

    PlantServiceImpl() {
    }

    @Override
    public void createNew(Plant plant) {
        plant.setState(Plant.State.SEEDLING);
        MySqlDaoFactory.getInstance().getPlantDao().create(plant);
    }

    @Override
    public Plant getById(Integer id) {
        return MySqlDaoFactory.getInstance().getPlantDao().getById(id);
    }

    @Override
    public void update(Plant plant) {
        MySqlDaoFactory.getInstance().getPlantDao().update(plant);
    }

    @Override
    public void delete(Plant plant) {
        MySqlDaoFactory.getInstance().getPlantDao().delete(plant);
    }

    @Override
    public List<Plant> getAll() {
        return MySqlDaoFactory.getInstance().getPlantDao().getAll();
    }

    @Override
    public List<Plant> getAllOn(Area area) {
        return MySqlDaoFactory.getInstance().getPlantDao().getAllOn(area.getId());
    }

    @Override
    public List<Task> getAssociatedTasks(Plant plant) {
        TaskDao taskDao = MySqlDaoFactory.getInstance().getTaskDao();
        return MySqlDaoFactory.getInstance().getPlantDao().getAssociatedTaskIds(plant)
                .stream()
                .map(taskDao::getById)
                .collect(Collectors.toList());
    }
}