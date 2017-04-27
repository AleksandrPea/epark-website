package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.service.AreaService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayAllAreasHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private AreaService areaService = ServiceFactoryImpl.getInstance().getAreaService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(assets.get("ALL_AREAS_ATTR_NAME"), areaService.getAll());
        return FORWARD + assets.get("AREA_LIST_VIEW_NAME");
    }
}