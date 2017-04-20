package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.service.AreaService;

import java.util.List;

public class AreaServiceImpl implements AreaService {

    private ConnectionPool<MySqlDaoConnection> pool;
    private DaoFactory factory;

    AreaServiceImpl(ConnectionPool<MySqlDaoConnection> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Area getByName(String name) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getAreaDao(pool.getSqlConnectionFrom(connection)).getByName(name);
        }
    }

    @Override
    public Area getById(Integer id) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getAreaDao(pool.getSqlConnectionFrom(connection)).getById(id);
        }
    }

    @Override
    public void update(Area area) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            factory.getAreaDao(pool.getSqlConnectionFrom(connection)).update(area);
        }
    }

    @Override
    public List<Area> getAll() {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getAreaDao(pool.getSqlConnectionFrom(connection)).getAll();
        }
    }
}
