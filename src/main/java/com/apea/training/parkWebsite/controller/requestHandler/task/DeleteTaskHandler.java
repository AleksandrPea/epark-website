package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteTaskHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        Task task = getTask(request);
        if (!isCurrentUserSender(request, task)) {throw new AccessDeniedException("Current user is not a sender.");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        deleteTask(task, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_USER_TASKS_URI");
    }

    private Task getTask(HttpServletRequest request) {
        String taskId = request.getParameter(AppAssets.getInstance().get("ID_PARAM_NAME"));
        return ServiceFactoryImpl.getInstance().getTaskService().getById(Integer.valueOf(taskId));
    }

    private boolean isCurrentUserSender(HttpServletRequest request, Task task) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (task.getSenderId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }

    private void deleteTask(Task task, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        ServiceFactoryImpl.getInstance().getTaskService().delete(task);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_TASK_SUCCESS")));
    }
}
