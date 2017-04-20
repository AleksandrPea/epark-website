package com.apea.training.parkWebsite.dao;

import com.apea.training.parkWebsite.connection.DaoConnection;

public interface DaoFactory {

    DaoConnection getDaoConnection();

    AreaDao getAreaDao(DaoConnection connection);

    PlantDao getPlantDao(DaoConnection connection);

    ReportDao getReportDao(DaoConnection connection);

    TaskDao getTaskDao(DaoConnection connection);

    UserDao getUserDao(DaoConnection connection);

    CredentialsDao getCredentialsDao(DaoConnection connection);

}
