package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Task;

public interface TaskDao {

    Task create();
    Task getById(Integer id);
    void update(Task task);
    void delete(Task task);
}
