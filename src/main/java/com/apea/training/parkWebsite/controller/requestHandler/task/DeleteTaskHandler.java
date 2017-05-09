package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.service.TaskService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteTaskHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        deleteTask(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_USER_TASKS_URI");
    }

    private void deleteTask(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();
        Task task = taskService.getById(id);
        taskService.delete(task);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_TASK_SUCCESS")));
    }
}
