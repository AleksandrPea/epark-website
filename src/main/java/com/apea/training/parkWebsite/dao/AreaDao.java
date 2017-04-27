package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Area;

import java.util.List;

public interface AreaDao {

    void create(Area area);

    Area getByName(String name);

    Area getById(Integer id);

    void update(Area area);

    void delete(Area area);

    List<Area> getAll();
}
