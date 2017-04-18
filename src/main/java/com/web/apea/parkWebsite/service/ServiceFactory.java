package com.web.apea.parkWebsite.service;

public interface ServiceFactory {

    AreaService getAreaService();
    PlantService getPlantService();
    ReportService getReportService();
    TaskService getTaskService();
    UserService getUserService();
}
