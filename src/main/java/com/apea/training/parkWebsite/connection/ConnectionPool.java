package com.apea.training.parkWebsite.connection;


public interface ConnectionPool {

    DaoConnection getConnection();
}
