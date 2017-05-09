package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEditAreaPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        setFormAttributes(request);
        request.setAttribute(assets.get("IS_CREATING_AREA_ATTR_NAME"), false);
        return FORWARD + assets.get("CREATE_AREA_VIEW_NAME");
    }

    private void setFormAttributes(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Area area = ServiceFactoryImpl.getInstance().getAreaService().getById(id);
        request.setAttribute(assets.get("AREA_ID_ATTR_NAME"), area.getId());
        request.setAttribute(assets.get("AREA_NAME_ATTR_NAME"), area.getName());
        request.setAttribute(assets.get("AREA_DESCRIPTION_ATTR_NAME"), area.getDescription());
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(area.getTaskmasterId());
        request.setAttribute(assets.get("TASKMASTER_LOGIN_ATTR_NAME"),credential.getLogin());
    }
}
