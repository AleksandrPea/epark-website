package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateReportPage implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(assets.get("TASK_ID_ATTR_NAME"), taskId);
        return FORWARD + assets.get("CREATE_REPORT_VIEW_NAME");
    }
}