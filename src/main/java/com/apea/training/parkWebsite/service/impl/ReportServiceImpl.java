package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ConnectionPool<MySqlDaoConnection> pool;
    private DaoFactory factory;

    ReportServiceImpl(ConnectionPool<MySqlDaoConnection> pool, DaoFactory factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public Report createBlankOn(Integer taskId) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getReportDao(pool.getSqlConnectionFrom(connection)).createOn(taskId);
        }
    }

    @Override
    public Report getById(Integer id) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getReportDao(pool.getSqlConnectionFrom(connection)).getById(id);
        }
    }

    @Override
    public void update(Report report) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            factory.getReportDao(pool.getSqlConnectionFrom(connection)).update(report);
        }
    }

    @Override
    public void delete(Report report) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            factory.getReportDao(pool.getSqlConnectionFrom(connection)).delete(report);
        }
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        try (MySqlDaoConnection connection = pool.getDaoConnection()) {
            return factory.getReportDao(pool.getSqlConnectionFrom(connection)).getAllOn(taskId);
        }
    }
}
