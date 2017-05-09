package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.service.AreaService;

import java.util.List;

public class AreaServiceImpl implements AreaService {


    AreaServiceImpl() {}

    @Override
    public void create(Area area) {
        MySqlDaoFactory.getInstance().getAreaDao().create(area);
    }

    @Override
    public Area getById(Integer id) {
        return MySqlDaoFactory.getInstance().getAreaDao().getById(id);
    }

    @Override
    public void update(Area area) {
        MySqlDaoFactory.getInstance().getAreaDao().update(area);
    }

    @Override
    public void delete(Area area) {
        MySqlDaoFactory.getInstance().getAreaDao().delete(area);
    }

    @Override
    public List<Area> getAll() {
        return MySqlDaoFactory.getInstance().getAreaDao().getAll();
    }
}