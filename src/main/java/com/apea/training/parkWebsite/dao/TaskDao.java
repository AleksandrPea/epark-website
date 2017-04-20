package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Task;

public interface TaskDao {

    void create(Task task);

    Task getById(Integer id);

    void updateState(Integer taskId, Task.State newState);

    void delete(Task task);
}
