package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.ALL_AREAS_ATTR_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.AREA_LIST_VIEW_NAME;

public class DisplayAllAreasHandler implements RequestHandler {

    private AreaService areaService = ServiceFactory.getInstance().getAreaService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ALL_AREAS_ATTR_NAME, areaService.getAll());
        return FORWARD + AREA_LIST_VIEW_NAME;
    }
}
