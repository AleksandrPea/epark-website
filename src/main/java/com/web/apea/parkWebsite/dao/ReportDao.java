package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Report;

import java.sql.SQLException;

public interface ReportDao {

    Report create() throws SQLException;
    Report getById(Integer id);

    /** @return false if there is no such report with id report.getId() */
    boolean update(Report report);

    /** @return false if there is no such report with id report.getId() */
    boolean delete(Report report);
}
