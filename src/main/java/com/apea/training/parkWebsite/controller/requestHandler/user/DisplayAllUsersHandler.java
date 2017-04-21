package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppResources.*;

public class DisplayAllUsersHandler implements RequestHandler {

    private UserService userService = UserFactory.getInstance().getUserService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(ALL_USERS_ATTR_NAME, userService.getAll());
        return FORWARD + USER_LIST_PAGE;
    }
}
