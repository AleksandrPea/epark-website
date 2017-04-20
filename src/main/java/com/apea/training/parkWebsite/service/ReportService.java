package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Report;

import java.util.List;

public interface ReportService {

    Report createBlankOn(Integer taskId);
    Report getById(Integer id);
    void update(Report report);
    void delete(Report report);
    List<Report> getAllOn(Integer taskId);
}
