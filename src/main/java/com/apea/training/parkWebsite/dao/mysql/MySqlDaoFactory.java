package com.apea.training.parkWebsite.dao.mysql;

import com.apea.training.parkWebsite.dao.*;

public class MySqlDaoFactory implements DaoFactory {

    private static MySqlDaoFactory instance = new MySqlDaoFactory();

    private AreaDao areaDao;
    private PlantDao plantDao;
    private ReportDao reportDao;
    private TaskDao taskDao;
    private UserDao userDao;
    private CredentialDao credentialDao;

    private MySqlDaoFactory() {}

    public static MySqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public AreaDao getAreaDao() {
        if (areaDao == null) {
            areaDao = new MySqlAreaDao();
        }
        return areaDao;
    }

    @Override
    public PlantDao getPlantDao() {
        if (plantDao == null) {
            plantDao = new MySqlPlantDao();
        }
        return plantDao;
    }

    @Override
    public ReportDao getReportDao() {
        if (reportDao == null) {
            reportDao = new MySqlReportDao();
        }
        return reportDao;
    }

    @Override
    public TaskDao getTaskDao() {
        if (taskDao == null) {
            taskDao = new MySqlTaskDao();
        }
        return taskDao;
    }

    @Override
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new MySqlUserDao();
        }
        return userDao;
    }

    @Override
    public CredentialDao getCredentialDao() {
        if (credentialDao == null) {
            credentialDao = new MySqlCredentialDao();
        }
        return credentialDao;
    }
}