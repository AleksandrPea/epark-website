package com.apea.training.parkWebsite.service;

import com.apea.training.parkWebsite.domain.Report;

import java.util.List;

public interface ReportService {

    void create(Report report);

    Report getById(Integer id);

    void delete(Report report);

    List<Report> getAllOn(Integer taskId);
}
