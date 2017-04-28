package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Task;

import java.util.List;

public interface TaskDao {

    void create(Task task);

    /** @return true if association was created */
    boolean associate(Task task, Integer plantId);

    Task getById(Integer id);

    void updateState(Task task);

    void delete(Task task);

    List<Task> getUserTasks(Integer userId);

    List<Integer> getAssociatedPlantIds(Task task);
}