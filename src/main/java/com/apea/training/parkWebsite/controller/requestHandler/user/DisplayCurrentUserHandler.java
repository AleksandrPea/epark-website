package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppResources;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCurrentUserHandler implements RequestHandler {

    DisplayCurrentUserHandler() {}

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {

        return FORWARD + AppResources.ONE_USER_PAGE;
    }
}
