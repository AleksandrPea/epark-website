package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateUserPage implements RequestHandler{

    private AppAssets assets = AppAssets.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        return FORWARD + assets.get("CREATE_USER_VIEW_NAME");
    }
}
