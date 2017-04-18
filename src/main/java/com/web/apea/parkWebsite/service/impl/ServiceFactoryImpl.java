package com.web.apea.parkWebsite.service.impl;

import com.web.apea.parkWebsite.connection.AbstractConnectionImpl;
import com.web.apea.parkWebsite.connection.ConnectionPool;
import com.web.apea.parkWebsite.connection.MySqlConnectionPool;
import com.web.apea.parkWebsite.dao.DaoFactory;
import com.web.apea.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.web.apea.parkWebsite.service.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private ConnectionPool<AbstractConnectionImpl> pool = MySqlConnectionPool.getInstance();
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
        return null;
    }

    @Override
    public PlantService getPlantService() {
        return null;
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
