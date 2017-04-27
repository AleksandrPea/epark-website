package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.CREATE_USER_VIEW_NAME;

public class DisplayCreateUserPage implements RequestHandler{

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        return FORWARD + CREATE_USER_VIEW_NAME;
    }
}
