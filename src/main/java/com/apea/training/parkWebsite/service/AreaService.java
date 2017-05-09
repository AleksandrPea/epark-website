package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Area;

import java.util.List;

public interface AreaService {

    void create(Area area);

    Area getById(Integer id);

    void update(Area area);

    void delete(Area area);

    List<Area> getAll();

}
