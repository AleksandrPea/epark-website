package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.MySqlConnectionPool;
import com.apea.training.parkWebsite.dao.*;

import java.sql.Connection;

public class MySqlDaoFactory implements DaoFactory {

    private static MySqlDaoFactory instance = new MySqlDaoFactory();

    private ConnectionPool pool = MySqlConnectionPool.getInstance();

    private MySqlDaoFactory() {}

    public static MySqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public DaoConnection getDaoConnection() {
        return pool.getDaoConnection();
    }

    @Override
    public AreaDao getAreaDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlAreaDao(sqlConn);
    }

    @Override
    public PlantDao getPlantDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlPlantDao(sqlConn);
    }

    @Override
    public ReportDao getReportDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlReportDao(sqlConn);
    }

    @Override
    public TaskDao getTaskDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlTaskDao(sqlConn);
    }

    @Override
    public UserDao getUserDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlUserDao(sqlConn);
    }

    @Override
    public CredentialsDao getCredentialsDao(DaoConnection connection) {
        Connection sqlConn = pool.getSqlConnectionFrom(connection);
        checkConnection(sqlConn);
        return new MySqlCredentialsDao(sqlConn);
    }

    private void checkConnection(Connection connection) {
        if (connection == null) {
            throw new DaoException("null connection");
        }
    }
}
