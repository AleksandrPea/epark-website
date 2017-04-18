package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.connection.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.domain.Report;
import com.web.apea.parkWebsite.service.ReportService;
;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ConnectionPool<AbstractConnectionImpl> pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

    ReportServiceImpl(ConnectionPool<AbstractConnectionImpl> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Report createBlankOn(Integer taskId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getReportDao(pool.getSqlConnection(connection)).createOn(taskId);
        }
    }

    @Override
    public Report getById(Integer id) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getReportDao(pool.getSqlConnection(connection)).getById(id);
        }
    }

    @Override
    public void update(Report report) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getReportDao(pool.getSqlConnection(connection)).update(report);
        }
    }

    @Override
    public void delete(Report report) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            factory.getReportDao(pool.getSqlConnection(connection)).delete(report);
        }
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        try (AbstractConnectionImpl connection = pool.getConnection()) {
            return factory.getReportDao(pool.getSqlConnection(connection)).getAllOn(taskId);
        }
    }
}
