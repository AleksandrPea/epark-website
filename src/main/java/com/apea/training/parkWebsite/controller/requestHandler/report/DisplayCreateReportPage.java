package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.CREATE_REPORT_VIEW_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.TASK_ID_ATTR_NAME;

public class DisplayCreateReportPage implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(TASK_ID_ATTR_NAME, taskId);
        return FORWARD + CREATE_REPORT_VIEW_NAME;
    }
}