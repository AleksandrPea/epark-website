package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.*;

public class DisplayTaskHandler implements RequestHandler {

    private TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();
    private ReportService reportService = ServiceFactoryImpl.getInstance().getReportService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(TASK_ATTR_NAME, taskService.getById(taskId));
        request.setAttribute(TASK_REPORTS_ATTR_NAME, reportService.getAllOn(taskId));
        return FORWARD + DISPLAY_TASK_VIEW_NAME;
    }
}
