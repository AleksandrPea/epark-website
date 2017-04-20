package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private DaoFactory factory;

    ReportServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Report report) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getReportDao(connection).create(report);
        }
    }

    @Override
    public Report getById(Integer id) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            return factory.getReportDao(connection).getById(id);
        }
    }

    @Override
    public void delete(Report report) {
        try (DaoConnection connection = factory.getDaoConnection()) {
            factory.getReportDao(connection).delete(report);
        }
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        try (DaoConnection connection = factory.getDaoConnection()) {
           return factory.getReportDao(connection).getAllOn(taskId);
        }
    }
}
