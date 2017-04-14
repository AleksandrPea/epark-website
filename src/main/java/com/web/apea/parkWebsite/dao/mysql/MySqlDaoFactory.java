package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.*;

import java.sql.Connection;

public class MySqlDaoFactory implements DaoFactory {

    private static MySqlDaoFactory instance = new MySqlDaoFactory();

    private MySqlDaoFactory() {}

    public static MySqlDaoFactory getInstance() {
        return instance;
    }

    public AreaDao getAreaDao(Connection connection) {
        checkConnection(connection);
        return new MySqlAreaDao(connection);
    }

    public PlantDao getPlantDao(Connection connection) {
        checkConnection(connection);
        return new MySqlPlantDao(connection);
    }

    public ReportDao getReportDao(Connection connection) {
        checkConnection(connection);
        return new MySqlReportDao(connection);
    }

    public TaskDao getTaskDao(Connection connection) {
        checkConnection(connection);
        return new MySqlTaskDao(connection);
    }

    public UserDao getUserDao(Connection connection) {
        checkConnection(connection);
        return new MySqlUserDao(connection);
    }

    private void checkConnection(Connection connection) {
        if (connection == null) {
            throw new DaoException("null connection");
        }
    }
}
