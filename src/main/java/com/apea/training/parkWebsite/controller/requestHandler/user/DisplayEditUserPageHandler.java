package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEditUserPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        setFormAttributes(request);
        request.setAttribute(assets.get("IS_CREATING_USER_ATTR_NAME"), false);
        return FORWARD + assets.get("CREATE_USER_VIEW_NAME");
    }

    private void setFormAttributes(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        User user = ServiceFactoryImpl.getInstance().getUserService().getById(id);
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(id);
        request.setAttribute(assets.get("LOGIN_ATTR_NAME"), credential.getLogin());
        request.setAttribute(assets.get("PASSWORD_ATTR_NAME"), credential.getPassword());
        request.setAttribute(assets.get("FIRSTNAME_ATTR_NAME"), user.getFirstName());
        request.setAttribute(assets.get("LASTNAME_ATTR_NAME"), user.getLastName());
        request.setAttribute(assets.get("EMAIL_ATTR_NAME"), user.getEmail());
        request.setAttribute(assets.get("ROLE_ATTR_NAME"), user.getRole().toString());
        request.setAttribute(assets.get("USER_INFO_ATTR_NAME"), user.getInfo());
        if (user.getRole() != User.Role.OWNER) {
            request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"),
                    ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(user.getSuperiorId()).getLogin());
        }
    }
}
