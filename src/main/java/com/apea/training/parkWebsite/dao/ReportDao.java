package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.domain.Report;

import java.util.List;

public interface ReportDao {

    void create(Report report);

    Report getById(Integer id);

    void delete(Report report);

    List<Report> getAllOn(Integer taskId);

}