package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private DaoFactory factory = MySqlDaoFactory.getInstance();

    private AreaService areaService;
    private PlantService plantService;
    private ReportService reportService;
    private TaskService taskService;
    private UserService userService;
    private CredentialsService credentialsService;

    private static ServiceFactoryImpl instance = new ServiceFactoryImpl();

    private ServiceFactoryImpl() {}

    public static ServiceFactoryImpl getInstance() {
        return instance;
    }

    @Override
    public AreaService getAreaService() {
        if (areaService == null) {
            areaService = new AreaServiceImpl(factory);
        }
        return areaService;
    }

    @Override
    public PlantService getPlantService() {
        if (plantService == null) {
            plantService = new PlantServiceImpl(factory);
        }
        return plantService;
    }

    @Override
    public ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportServiceImpl(factory);
        }
        return reportService;
    }

    @Override
    public TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskServiceImpl(factory);
        }
        return taskService;
    }

    @Override
    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl(factory);
        }
        return userService;
    }

    @Override
    public CredentialsService getCredentialsSerice() {
        if (credentialsService == null) {
            credentialsService = new CredentialsServiceImpl(factory);
        }
        return credentialsService;
    }
}
