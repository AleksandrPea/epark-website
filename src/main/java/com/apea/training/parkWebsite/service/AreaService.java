package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Area;

import java.util.List;

public interface AreaService {

    Area getByName(String name);

    Area getById(Integer id);

    void update(Area area);

    List<Area> getAll();

}
