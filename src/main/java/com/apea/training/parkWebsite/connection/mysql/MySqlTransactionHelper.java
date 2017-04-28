package com.apea.training.parkWebsite.connection.mysql;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.connection.DaoConnection;
import com.apea.training.parkWebsite.connection.TransactionHelper;
import com.apea.training.parkWebsite.dao.DaoException;

public class MySqlTransactionHelper implements TransactionHelper {

    private static MySqlTransactionHelper instance = new MySqlTransactionHelper();

    private ConnectionPool pool = MySqlConnectionPool.getInstance();
    private ThreadLocal<DaoConnection> local = new ThreadLocal<>();

    private MySqlTransactionHelper() {}

    public static MySqlTransactionHelper getInstance() {
        return instance;
    }

    @Override
    public void beginTransaction() {
        DaoConnection connection = pool.getConnection();
        connection.setIsInTransaction(true);
        local.set(connection);
    }

    @Override
    public void commitTransaction() {
        DaoConnection connection = local.get();
        if (connection == null) {
            throw new DaoException("Can't commit transaction: it has not been begun");
        }
        connection.commit();
        endTransaction(connection);
    }

    @Override
    public void rollbackTransaction() {
        DaoConnection connection = local.get();
        if (connection == null) {
            throw new DaoException("Can't rollback transaction: it has not been begun");
        }
        connection.rollback();
        endTransaction(connection);
    }

    @Override
    public DaoConnection getConnection() {
        DaoConnection connection = local.get();
        if (connection == null) {
            connection = pool.getConnection();
        }
        return connection;
    }

    private void endTransaction(DaoConnection connection) {
        connection.setIsInTransaction(false);
        connection.close();
        local.set(null);
    }
}