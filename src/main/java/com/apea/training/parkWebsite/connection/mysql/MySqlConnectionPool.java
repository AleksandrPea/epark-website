package com.apea.training.parkWebsite.connection.mysql;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.dao.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnectionPool implements ConnectionPool {

    private final String DB_CONFIG_FILENAME = "webProject/config/dbConfig.properties";
    private final String DB_CONFIG_PARAM_URL = "database.url";
    private final String DB_CONFIG_PARAM_DB_NAME = "database.dbName";
    private final String DB_CONFIG_PARAM_USER_NAME = "database.userName";
    private final String DB_CONFIG_PARAM_USER_PASSWORD = "database.userPassword";
    private final String DB_CONFIG_PARAM_DRIVER = "database.driver";
    private final String DB_CONFIG_PARAM_MAX_CONNECTIONS = "database.maxConnections";
    private final String DB_CONFIG_PARAM_CONNECTION_PROPERITES="database.connectionProperties";

    private static MySqlConnectionPool instance = new MySqlConnectionPool();

    private BasicDataSource connectionPool;

    {
        Properties props = new Properties();
        connectionPool = null;
        try (InputStream is =
                MySqlConnectionPool.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILENAME)){
            props.load(is);
            String dbUrl = props.getProperty(DB_CONFIG_PARAM_URL) +"/"+ props.getProperty(DB_CONFIG_PARAM_DB_NAME);
            connectionPool = new BasicDataSource();
            connectionPool.setDriverClassName(props.getProperty(DB_CONFIG_PARAM_DRIVER));
            connectionPool.setUrl(dbUrl);
            connectionPool.setUsername(props.getProperty(DB_CONFIG_PARAM_USER_NAME));
            connectionPool.setPassword(props.getProperty(DB_CONFIG_PARAM_USER_PASSWORD));
            connectionPool.setMaxTotal(Integer.parseInt(props.getProperty(DB_CONFIG_PARAM_MAX_CONNECTIONS)));
            connectionPool.setConnectionProperties(props.getProperty(DB_CONFIG_PARAM_CONNECTION_PROPERITES));
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    private MySqlConnectionPool() {}

    public static MySqlConnectionPool getInstance() {
        return instance;
    }

    @Override
    public MySqlDaoConnection getConnection() {
        try {
            return new MySqlDaoConnection(connectionPool.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Can't get dao connection", e);
        }
    }
}