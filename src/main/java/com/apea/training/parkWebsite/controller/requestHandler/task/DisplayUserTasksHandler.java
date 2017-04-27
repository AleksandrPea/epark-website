package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.CURRENT_USER_TASKS_ATTR_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.CURRENT_USER_TASKS_LIST_VIEW_NAME;

public class DisplayUserTasksHandler implements RequestHandler {

    private TaskService taskService = ServiceFactory.getInstance().getTaskService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        request.setAttribute(CURRENT_USER_TASKS_ATTR_NAME, taskService.getUserTasks(currentUser));
        return FORWARD + CURRENT_USER_TASKS_LIST_VIEW_NAME;
    }
}