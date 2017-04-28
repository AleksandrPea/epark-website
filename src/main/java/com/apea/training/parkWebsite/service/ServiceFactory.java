package com.apea.training.parkWebsite.service;

public interface ServiceFactory {

    AreaService getAreaService();

    PlantService getPlantService();

    ReportService getReportService();

    TaskService getTaskService();

    UserService getUserService();

    CredentialService getCredentialsSerice();
}
