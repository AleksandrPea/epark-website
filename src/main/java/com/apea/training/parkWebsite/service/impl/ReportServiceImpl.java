package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    ReportServiceImpl() {}

    @Override
    public void create(Report report) {
        MySqlDaoFactory.getInstance().getReportDao().create(report);
    }

    @Override
    public Report getById(Integer id) {
        return MySqlDaoFactory.getInstance().getReportDao().getById(id);
    }

    @Override
    public void delete(Report report) {
        MySqlDaoFactory.getInstance().getReportDao().delete(report);
    }

    @Override
    public List<Report> getAllOn(Integer taskId) {
        return MySqlDaoFactory.getInstance().getReportDao().getAllOn(taskId);
    }
}
