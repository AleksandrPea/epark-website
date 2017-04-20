package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Task;

public interface TaskDao {

    Task create();
    Task getById(Integer id);
    void update(Task task);
    void delete(Task task);
}
