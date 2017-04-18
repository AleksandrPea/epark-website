package com.web.apea.parkWebsite.connection;

public interface AbstractConnection extends AutoCloseable {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    /**
     * Have to rollback transaction before closing
     * if it has been begun and is not commited.
     */
    @Override
    void close();

}
