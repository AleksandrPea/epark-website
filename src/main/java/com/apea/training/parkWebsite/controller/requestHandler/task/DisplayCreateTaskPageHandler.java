package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateTaskPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        request.setAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME"),
                ControllerUtils.getCurrentUserPlants(request));
        return FORWARD + assets.get("CREATE_TASK_VIEW_NAME");
    }
}