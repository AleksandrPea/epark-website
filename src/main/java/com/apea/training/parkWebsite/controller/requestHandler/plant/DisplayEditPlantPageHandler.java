package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEditPlantPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}

        setFormAttributes(request);
        request.setAttribute(assets.get("ALL_AREAS_ATTR_NAME"),
                ServiceFactoryImpl.getInstance().getAreaService().getAll());
        request.setAttribute(assets.get("IS_CREATING_PLANT_ATTR_NAME"), false);
        return FORWARD + assets.get("CREATE_PLANT_VIEW_NAME");
    }

    private void setFormAttributes(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Plant plant = ServiceFactoryImpl.getInstance().getPlantService().getById(id);
        Area area = ServiceFactoryImpl.getInstance().getAreaService().getById(plant.getAreaId());
        request.setAttribute(assets.get("PLANT_ID_ATTR_NAME"), id);
        request.setAttribute(assets.get("PLANT_NAME_ATTR_NAME"), plant.getName());
        request.setAttribute(assets.get("PLANT_DESCRIPTION_ATTR_NAME"), plant.getDescription());
        request.setAttribute(assets.get("PLANT_IMG_PATH_ATTR_NAME"), plant.getImgPath());
        request.setAttribute(assets.get("PLANT_STATE_ATTR_NAME"), plant.getState().toString());
        request.setAttribute(assets.get("AREA_ATTR_NAME"), area);
    }
}