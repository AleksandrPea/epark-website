package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCurrentUserHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();

    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private CredentialService credentialsService = ServiceFactoryImpl.getInstance().getCredentialsSerice();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        int currentUserId = ControllerUtils.getCurrentUserId(request);
        request.setAttribute(assets.get("USER_ATTR_NAME"), userService.getById(currentUserId));
        request.setAttribute(assets.get("CREDENTIALS_ATTR_NAME"), credentialsService.getByUserId(currentUserId));
        return FORWARD + assets.get("DISPLAY_USER_VIEW_NAME");
    }
}
