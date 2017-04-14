package com.web.apea.parkWebsite.connectionPool;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();
}
