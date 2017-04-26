package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.ALL_USERS_ATTR_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.USER_LIST_VIEW_NAME;

public class DisplayAllUsersHandler implements RequestHandler {

    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ALL_USERS_ATTR_NAME, userService.getAll());
        return FORWARD + USER_LIST_VIEW_NAME;
    }
}