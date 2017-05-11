package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;
import com.apea.training.parkWebsite.domain.Area;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DisplayAllAreasHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}

        List<Area> allAreas = ServiceFactoryImpl.getInstance().getAreaService().getAll();
        Map<Integer, String> taskmastersMap = allAreas
                .stream()
                .collect(Collectors.toMap(Area::getId, area -> ServiceFactoryImpl.getInstance()
                        .getCredentialService().getByUserId(area.getTaskmasterId()).getLogin()));
        request.setAttribute(assets.get("ALL_AREAS_ATTR_NAME"), allAreas);
        request.setAttribute(assets.get("AREA_TASKMASTERS_ATTR_NAME"), taskmastersMap);

        return FORWARD + assets.get("AREA_LIST_VIEW_NAME");
    }
}