package com.web.apea.parkWebsite.dao.mysql;

import com.web.apea.parkWebsite.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDaoFactory implements DaoFactory {

    private static MySqlDaoFactory instance = new MySqlDaoFactory();

    private String user = "root";
    private String password = "secret";
    private String url = "jdbc:mysql://localhost:3306/parkdb";
    private String driver = "com.mysql.jdbc.Driver";

    private MySqlUserDao mySqlUserDao;
    private MySqlAreaDao mySqlAreaDao;
    private MySqlReportDao mySqlReportDao;

    private MySqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // LOGGER
        }
    }

    public static MySqlDaoFactory getInstance() {
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public AreaDao getAreaDao(Connection connection){
        if (mySqlAreaDao == null) {
            mySqlAreaDao = new MySqlAreaDao(connection);
        }
        return mySqlAreaDao;
    }

    public PlantDao getPlantDao(Connection connection) {
        return null;
    }

    public ReportDao getReportDao(Connection connection) {
        if (mySqlReportDao == null) {
            mySqlReportDao = new MySqlReportDao(connection);
        }
        return mySqlReportDao;
    }

    public TaskDao getTaskDao(Connection connection) {
        return null;
    }

    public UserDao getUserDao(Connection connection) {
        if (mySqlUserDao == null) {
            mySqlUserDao = new MySqlUserDao(connection);
        }
        return mySqlUserDao;
    }
}
