package com.web.apea.parkWebsite.connection;


import java.sql.Connection;

/**
 *
 * @param <T> type of connection
 */
public interface ConnectionPool<T extends AbstractConnection> {

    T getConnection();
    Connection getSqlConnection(T connection);
}
