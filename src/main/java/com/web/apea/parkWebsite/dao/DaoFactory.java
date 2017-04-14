package com.web.apea.parkWebsite.dao;

import java.sql.Connection;

public interface DaoFactory {

    AreaDao getAreaDao(Connection connection);
    PlantDao getPlantDao(Connection connection);
    ReportDao getReportDao(Connection connection);
    TaskDao getTaskDao(Connection connection);
    UserDao getUserDao(Connection connection);
}
