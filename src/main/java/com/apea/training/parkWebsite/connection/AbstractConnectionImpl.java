package com.apea.training.parkWebsite.connection;

import com.apea.training.parkWebsite.service.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;

public class AbstractConnectionImpl implements AbstractConnection {

    private Connection connection;
    private boolean transactBegun;
    private boolean transactCommitted;

    public AbstractConnectionImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            transactBegun = true;
            transactCommitted = false;
        } catch (SQLException e) {
            throw new ServiceException("Can't begin transaction", e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            transactCommitted = true;
        } catch (SQLException e) {
            throw new ServiceException("Can't commit transaction", e);
        }

    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            transactCommitted = true;
        } catch (SQLException e) {
            throw new ServiceException("Can't rollback transaction", e);
        }
    }

    @Override
    public void close() {
        try {
            if (transactBegun && !transactCommitted) {
                rollbackTransaction();
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Can't close connection", e);
        }
    }

    Connection getConnection() {
        return connection;
    }

}
