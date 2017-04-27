package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;

import java.util.List;

public interface TaskService {

    void createNewAndAssociate(Task task, List<Plant> plants);

    Task getById(Integer id);

    void delete(Task task);

    List<Task> getUserTasks(User user);

    List<Plant> getAssociatedPlants(Task task);

    void finishAndUpdatePlantStates(Task task, List<Plant> plants);

    void abort(Task task);

    void confirmRecieving(Task task);

}
