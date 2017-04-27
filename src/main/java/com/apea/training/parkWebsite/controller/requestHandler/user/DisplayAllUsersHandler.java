package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DisplayAllUsersHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(assets.get("ALL_USERS_ATTR_NAME"), userService.getAll());
        return FORWARD + assets.get("USER_LIST_VIEW_NAME");
    }
}