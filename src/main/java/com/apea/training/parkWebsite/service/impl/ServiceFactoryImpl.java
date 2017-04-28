package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private AreaService areaService;
    private PlantService plantService;
    private ReportService reportService;
    private TaskService taskService;
    private UserService userService;
    private CredentialService credentialsService;

    private static ServiceFactoryImpl instance = new ServiceFactoryImpl();

    private ServiceFactoryImpl() {}

    public static ServiceFactoryImpl getInstance() {
        return instance;
    }

    @Override
    public AreaService getAreaService() {
        if (areaService == null) {
            areaService = new AreaServiceImpl();
        }
        return areaService;
    }

    @Override
    public PlantService getPlantService() {
        if (plantService == null) {
            plantService = new PlantServiceImpl();
        }
        return plantService;
    }

    @Override
    public ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportServiceImpl();
        }
        return reportService;
    }

    @Override
    public TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskServiceImpl();
        }
        return taskService;
    }

    @Override
    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public CredentialService getCredentialsSerice() {
        if (credentialsService == null) {
            credentialsService = new CredentialServiceImpl();
        }
        return credentialsService;
    }
}
