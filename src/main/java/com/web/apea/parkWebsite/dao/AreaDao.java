package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Area;

import java.sql.SQLException;

public interface AreaDao {

    Area getByName(String name) throws SQLException;

    Area getById(Integer id) throws SQLException;

    void update(Area area) throws SQLException;
}
