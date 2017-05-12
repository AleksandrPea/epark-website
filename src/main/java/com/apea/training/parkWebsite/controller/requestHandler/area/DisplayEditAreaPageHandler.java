package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEditAreaPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        setFormAttributes(request);
        request.setAttribute(assets.get("IS_CREATING_AREA_ATTR_NAME"), false);
        return FORWARD + assets.get("CREATE_AREA_VIEW_NAME");
    }

    private void setFormAttributes(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Integer areaId = Integer.valueOf(request.getParameter(assets.get("ID_PARAM_NAME")));
        Area area = ServiceFactoryImpl.getInstance().getAreaService().getById(areaId);
        request.setAttribute(assets.get("AREA_ID_ATTR_NAME"), area.getId());
        request.setAttribute(assets.get("AREA_NAME_ATTR_NAME"), area.getName());
        request.setAttribute(assets.get("AREA_DESCRIPTION_ATTR_NAME"), area.getDescription());
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(area.getTaskmasterId());
        request.setAttribute(assets.get("TASKMASTER_LOGIN_ATTR_NAME"),credential.getLogin());
    }
}
