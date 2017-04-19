package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Area;

import java.util.List;

public interface AreaDao {

    Area getByName(String name);

    Area getById(Integer id);

    void update(Area area);

    List<Area> getAll();
}
