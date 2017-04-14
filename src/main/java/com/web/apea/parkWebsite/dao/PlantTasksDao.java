package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Plant;
import com.web.apea.parkWebsite.domain.Task;

import java.util.List;

public interface PlantTasksDao {

    void createRelation(Integer taskId, Integer plantId);
    void deleteRelation(Integer taskId, Integer plantId);

    /** @return ids of related tasks */
    List<Integer> getRelations(Plant plant);

    /** @return ids of related plants */
    List<Integer> getRelations(Task task);
}
