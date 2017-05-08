package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayAllAreasHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        request.setAttribute(assets.get("ALL_AREAS_ATTR_NAME"),
                ServiceFactoryImpl.getInstance().getAreaService().getAll());
        request.setAttribute(assets.get("CURRENT_USER_ATTR_NAME"),
                ControllerUtils.getCurrentUser(request));
        return FORWARD + assets.get("AREA_LIST_VIEW_NAME");
    }
}