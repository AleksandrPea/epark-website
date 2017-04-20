package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;

import java.util.List;

public interface PlantService {

    Plant createOn(Integer areaId);
    Plant getById(Integer id);
    List<Task> getAssociatedTasks(Integer plantId);
    void update(Plant plant);

    /** Deletes plant and its associations with tasks */
    void delete(Plant plant);
    List<Plant> getAllOn(Integer areaId);
}
