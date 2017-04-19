package com.web.apea.parkWebsite.dao;

import java.util.List;

public interface PlantTasksDao {

    /** @return true if created */
    boolean createAssociation(Integer taskId, Integer plantId);

    /** @return number of deleted associations */
    int deleteAssociationsForTask(Integer taskId);

    /** @return number of deleted associations */
    int deleteAssociationsForPlant(Integer plantId);

    /** @return ids of related tasks */
    List<Integer> getAssociatedTasksIds(Integer plantId);

    /** @return ids of related plants */
    List<Integer> getAssociatedPlantsIds(Integer taskId);
}
