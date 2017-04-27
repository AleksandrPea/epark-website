package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.service.ReportService;
import com.apea.training.parkWebsite.service.TaskService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayTaskHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();
    private ReportService reportService = ServiceFactoryImpl.getInstance().getReportService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(assets.get("TASK_ATTR_NAME"), taskService.getById(taskId));
        request.setAttribute(assets.get("TASK_REPORTS_ATTR_NAME"), reportService.getAllOn(taskId));
        return FORWARD + assets.get("DISPLAY_TASK_VIEW_NAME");
    }
}