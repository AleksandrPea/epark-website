package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Report;

import java.util.List;

public interface ReportDao {

    Report createOn(Integer taskId);
    Report getById(Integer id);
    void update(Report report);
    void delete(Report report);
    List<Report> getAllOn(Integer taskId);
    /** @return deleted count */
    int deleteAllOn(Integer taskId);
}
