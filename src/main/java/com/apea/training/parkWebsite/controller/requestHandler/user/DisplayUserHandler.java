package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}

        CredentialService credentialService = ServiceFactoryImpl.getInstance().getCredentialService();
        int userId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        User user = ServiceFactoryImpl.getInstance().getUserService().getById(userId);
        request.setAttribute(assets.get("USER_ATTR_NAME"), user);
        request.setAttribute(assets.get("CREDENTIAL_ATTR_NAME"),
                credentialService.getByUserId(userId));
        if (user.getSuperiorId() != null) {
            request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"),
                    credentialService.getByUserId(user.getSuperiorId()).getLogin());
        }
        return FORWARD + assets.get("DISPLAY_USER_VIEW_NAME");
    }
}