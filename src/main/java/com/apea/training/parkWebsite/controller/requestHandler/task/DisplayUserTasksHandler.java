package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.TaskService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayUserTasksHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        request.setAttribute(assets.get("CURRENT_USER_TASKS_ATTR_NAME"), taskService.getUserTasks(currentUser));
        return FORWARD + assets.get("CURRENT_USER_TASKS_LIST_VIEW_NAME");
    }
}