package com.apea.training.parkWebsite.controller.requestHandler.task.stateHandlers;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinishTaskHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (!isCurrentUserSender(request)) {throw new AccessDeniedException("Current user is not a sender.");}

        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        ServiceFactoryImpl.getInstance().getTaskService().setState(id, Task.State.FINISHED);
        return REDIRECT + assets.get("DISPLAY_USER_TASKS_URI");
    }

    private boolean isCurrentUserSender(HttpServletRequest request) {
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Task task = ServiceFactoryImpl.getInstance().getTaskService().getById(taskId);
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (task.getSenderId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
