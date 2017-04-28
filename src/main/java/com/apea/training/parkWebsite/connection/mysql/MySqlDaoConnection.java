package com.apea.training.parkWebsite.connection.mysql;

import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlDaoConnection implements DaoConnection {

    private Connection connection;
    private boolean isInTransaction;

    MySqlDaoConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new DaoException("Can't prepare statement", e);
        }
    }

    @Override
    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new DaoException("Can't create statement", e);
        }
    }

    @Override
    public void setIsInTransaction(boolean isInTransaction) {
        this.isInTransaction = isInTransaction;
        try {
            connection.setAutoCommit(!isInTransaction);
        } catch (SQLException e) {
            throw new DaoException("Can't set is-in-transaction", e);
        }
    }

    @Override
    public void commit() {
        if (!isInTransaction) {
            throw new DaoException("Can't commit: Is not in transaction");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't commit", e);
        }
    }

    @Override
    public void rollback() {
        if (!isInTransaction) {
            throw new DaoException("Can't rollback: Is not in transaction");
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Can't rollback", e);
        }
    }

    @Override
    public void close() {
        try {
            if (!isInTransaction) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Can't close connection", e);
        }
    }
}