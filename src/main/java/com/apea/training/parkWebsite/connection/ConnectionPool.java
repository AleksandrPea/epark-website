package com.apea.training.parkWebsite.connection;


import java.sql.Connection;

/**
 *
 * @param <T> type of connection
 */
public interface ConnectionPool<T extends DaoConnection> {

    T getConnection();
    Connection getSqlConnectionFrom(T connection);
}
