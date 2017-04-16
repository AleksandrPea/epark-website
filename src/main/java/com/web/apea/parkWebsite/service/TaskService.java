package com.web.apea.parkWebsite.service;

import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;

import java.util.List;

public interface TaskService {

    Task createBlank();
    Task getById(Integer id);
    List<Plant> getAssociatedPlants(Integer taskId);
    void update(Task task);
    void updateAssociatedPlants(Integer taskId, List<Plant> newPlants);
    void delete(Task task);
}
