package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Area;

public interface AreaDao {

    Area getByName(String name);

    Area getById(Integer id);

    void update(Area area);
}
