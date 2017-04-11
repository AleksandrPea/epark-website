package com.web.apea.parkWebsite.mysql;

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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public AreaDao getAreaDao(Connection connection) {
        return null;
    }

    public PlantDao getPlantDao(Connection connection) {
        return null;
    }

    public ReportDao getReportDao(Connection connection) {
        return null;
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
