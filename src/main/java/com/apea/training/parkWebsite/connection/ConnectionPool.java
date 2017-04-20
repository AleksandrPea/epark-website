package com.apea.training.parkWebsite.connection;


import java.sql.Connection;

/**
 *
 * @param <T> type of connection
 */
public interface ConnectionPool<T extends DaoConnection> {

    T getDaoConnection();
    Connection getSqlConnectionFrom(T connection);
}
