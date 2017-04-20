package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Plant;

import java.util.List;

public interface PlantDao {

    Plant createOn(Integer areaId);
    Plant getById(Integer id);
    void update(Plant plant);
    void delete(Plant plant);
    List<Plant> getAllOn(Integer areaId);
}
