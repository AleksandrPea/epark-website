package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateUserPageHandler implements RequestHandler{

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        return FORWARD + AppAssets.getInstance().get("CREATE_USER_VIEW_NAME");
    }
}