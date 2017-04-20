package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Plant;

import java.util.List;

public interface PlantDao {

    void create(Plant plant);

    Plant getById(Integer id);

    void update(Plant plant);

    void updateState(Plant plant, Plant.State newState);

    void delete(Plant plant);

    List<Plant> getAllOn(Integer areaId);

    List<Integer> getAssociatedTaskIds(Plant plant);
}
