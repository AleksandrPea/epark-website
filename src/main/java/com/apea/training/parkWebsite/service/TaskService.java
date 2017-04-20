package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.Plant;

import java.util.List;

public interface TaskService {

    Task createBlank();
    Task getById(Integer id);
    List<Plant> getAssociatedPlants(Integer taskId);
    void update(Task task);
    void updateAssociationsFor(Integer taskId, List<Plant> newPlants);

    /** Deletes task and its associations with plants */
    void delete(Task task);
}
