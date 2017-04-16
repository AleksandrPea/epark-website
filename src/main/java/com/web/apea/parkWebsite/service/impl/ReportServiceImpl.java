package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connectionPool.ConnectionPool;
import com.web.apea.parkWebsite.connectionPool.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.domain.Report;
import com.web.apea.parkWebsite.service.ReportService;
import com.web.apea.parkWebsite.service.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ConnectionPool pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

    @Override
    public Report createBlankOn(Integer taskId) {
        try (Connection connection = pool.getConnection()) {
            return factory.getReportDao(connection).createOn(taskId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Report getById(Integer id) {
        try (Connection connection = pool.getConnection()) {
            return factory.getReportDao(connection).getById(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Report report) {
        try (Connection connection = pool.getConnection()) {
            factory.getReportDao(connection).update(report);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Report report) {
        try (Connection connection = pool.getConnection()) {
            factory.getReportDao(connection).delete(report);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        try (Connection connection = pool.getConnection()) {
            return factory.getReportDao(connection).getAllOn(taskId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
