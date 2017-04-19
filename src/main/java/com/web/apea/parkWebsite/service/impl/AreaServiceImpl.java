package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.domain.Area;
import com.web.apea.parkWebsite.service.AreaService;

import java.util.List;

public class AreaServiceImpl implements AreaService {

    private ConnectionPool<AbstractConnectionImpl> pool;
    private DaoFactory factory;

    AreaServiceImpl(ConnectionPool<AbstractConnectionImpl> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Area getByName(String name) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getAreaDao(pool.getSqlConnection(connection)).getByName(name);
        }
    }

    @Override
    public Area getById(Integer id) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getAreaDao(pool.getSqlConnection(connection)).getById(id);
        }
    }

    @Override
    public void update(Area area) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getAreaDao(pool.getSqlConnection(connection)).update(area);
        }
    }

    @Override
    public List<Area> getAll() {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getAreaDao(pool.getSqlConnection(connection)).getAll();
        }
    }
}
