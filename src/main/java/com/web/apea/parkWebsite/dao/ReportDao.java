package com.web.apea.parkWebsite.dao;

import com.web.apea.parkWebsite.domain.Report;

public interface ReportDao {

    Report create(Report report);
    Report getById(Long id);
    void update(Report report);
    void delete(Report report);
}
