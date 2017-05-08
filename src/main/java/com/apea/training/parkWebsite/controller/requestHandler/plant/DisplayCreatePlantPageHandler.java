package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreatePlantPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(assets.get("AREA_ID_ATTR_NAME"), taskId);
        return FORWARD + assets.get("CREATE_PLANT_VIEW_NAME");
    }
}