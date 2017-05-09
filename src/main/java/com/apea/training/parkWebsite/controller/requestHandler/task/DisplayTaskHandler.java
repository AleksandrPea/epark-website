package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.TaskService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayTaskHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();
        CredentialService credentialService = ServiceFactoryImpl.getInstance().getCredentialService();
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Task task = taskService.getById(taskId);
        request.setAttribute(assets.get("TASK_ATTR_NAME"), task);
        request.setAttribute(assets.get("TASK_REPORTS_ATTR_NAME"),
                ServiceFactoryImpl.getInstance().getReportService().getAllOn(taskId));
        request.setAttribute(assets.get("TASK_RECEIVER_ATTR_NAME"),
                credentialService.getByUserId(task.getReceiverId()));
        request.setAttribute(assets.get("TASK_SENDER_ATTR_NAME"),
                credentialService.getByUserId(task.getSenderId()));
        request.setAttribute(assets.get("TASK_PLANTS_ATTR_NAME"),
                taskService.getAssociatedPlants(task));
        return FORWARD + assets.get("DISPLAY_TASK_VIEW_NAME");
    }
}