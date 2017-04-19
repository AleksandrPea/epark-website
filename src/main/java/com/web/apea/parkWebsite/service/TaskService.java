package com.web.apea.parkWebsite.service;

import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;

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
