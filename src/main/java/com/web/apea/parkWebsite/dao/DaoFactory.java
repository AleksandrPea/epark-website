package com.web.apea.parkWebsite.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DaoFactory {

    Connection getConnection() throws SQLException;
    AreaDao getAreaDao(Connection connection);
    PlantDao getPlantDao(Connection connection);
    ReportDao getReportDao(Connection connection);
    TaskDao getTaskDao(Connection connection);
    UserDao getUserDao(Connection connection);
}
