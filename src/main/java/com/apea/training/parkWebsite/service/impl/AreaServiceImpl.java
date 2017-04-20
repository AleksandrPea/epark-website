package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.service.AreaService;

import java.util.List;

public class AreaServiceImpl implements AreaService {

    private DaoFactory factory;

    AreaServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Area area) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getAreaDao(connection).create(area);
        }
    }

    @Override
    public Area getByName(String name) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getAreaDao(connection).getByName(name);
        }
    }

    @Override
    public Area getById(Integer id) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getAreaDao(connection).getById(id);
        }
    }

    @Override
    public void update(Area area) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getAreaDao(connection).update(area);
        }
    }

    @Override
    public void delete(Area area) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getAreaDao(connection).delete(area);
        }
    }

    @Override
    public List<Area> getAll() {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getAreaDao(connection).getAll();
        }
    }
}