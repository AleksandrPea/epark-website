package com.apea.training.parkWebsite.connection;

import java.sql.PreparedStatement;
import java.sql.Statement;

public interface DaoConnection extends AutoCloseable {

    PreparedStatement prepareStatement(String sql);

    PreparedStatement prepareStatement(String sql, int autoGeneratedKeys);

    Statement createStatement();

    void setIsInTransaction(boolean isInTransaction);

    void commit();

    void rollback();

    /** If is not in transaction does nothing */
    @Override
    void close();
}