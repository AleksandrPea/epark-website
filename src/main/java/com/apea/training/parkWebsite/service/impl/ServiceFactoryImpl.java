package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.ConnectionPool;
import com.apea.training.parkWebsite.connection.MySqlConnectionPool;
import com.apea.training.parkWebsite.connection.MySqlDaoConnection;
import com.apea.training.parkWebsite.dao.DaoFactory;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private ConnectionPool<MySqlDaoConnection> pool = MySqlConnectionPool.getInstance();
    private DaoFactory factory = MySqlDaoFactory.getInstance();

    private AreaService areaService;
    private PlantService plantService;
    private ReportService reportService;
    private TaskService taskService;
    private UserService userService;

    private static ServiceFactoryImpl instance = new ServiceFactoryImpl();

    private ServiceFactoryImpl() {}

    public static ServiceFactoryImpl getInstance() {
        return instance;
    }

    @Override
    public AreaService getAreaService() {
        if (areaService == null) {
            areaService = new AreaServiceImpl(pool, factory);
        }
        return areaService;
    }

    @Override
    public PlantService getPlantService() {
        if (plantService == null) {
            plantService = new PlantServiceImpl(pool, factory);
        }
        return plantService;
    }

    @Override
    public ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportServiceImpl(pool, factory);
        }
        return reportService;
    }

    @Override
    public TaskService getTaskService() {
        if (taskService == null) {
            taskService = new TaskServiceImpl(pool, factory);
        }
        return taskService;
    }

    @Override
    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl(pool, factory);
        }
        return userService;
    }
}
