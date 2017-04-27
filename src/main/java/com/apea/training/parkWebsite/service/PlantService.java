package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;

import java.util.List;

public interface PlantService {

    void createNew(Plant plant);

    Plant getById(Integer id);

    void update(Plant plant);

    void delete(Plant plant);

    List<Plant> getAll();

    List<Plant> getAllOn(Area area);

    List<Task> getAssociatedTasks(Plant plant);
}
