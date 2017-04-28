package com.apea.training.parkWebsite.dao;

public interface DaoFactory {

    AreaDao getAreaDao();

    PlantDao getPlantDao();

    ReportDao getReportDao();

    TaskDao getTaskDao();

    UserDao getUserDao();

    CredentialDao getCredentialDao();
}