package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateUserPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        request.setAttribute(assets.get("IS_CREATING_USER_ATTR_NAME"), true);
        Integer id = ControllerUtils.getCurrentUserId(request);
        request.setAttribute(assets.get("CURRENT_USER_LOGIN_ATTR_NAME"),
                ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(id).getLogin());
        return FORWARD + assets.get("CREATE_USER_VIEW_NAME");
    }
}