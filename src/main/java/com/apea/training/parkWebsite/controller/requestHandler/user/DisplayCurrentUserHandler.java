package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCurrentUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}

        CredentialService credentialService = ServiceFactoryImpl.getInstance().getCredentialService();
        User currentUser = ControllerUtils.getCurrentUser(request);
        request.setAttribute(assets.get("USER_ATTR_NAME"), currentUser);
        request.setAttribute(assets.get("CREDENTIAL_ATTR_NAME"),
                credentialService.getByUserId(currentUser.getId()));
        if (currentUser.getSuperiorId() != null) {
            request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"),
                    credentialService.getByUserId(currentUser.getSuperiorId()).getLogin());
        }
        request.setAttribute(assets.get("CURRENT_USER_ATTR_NAME"),currentUser);
        return FORWARD + assets.get("DISPLAY_USER_VIEW_NAME");
    }
}
