package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCurrentUserHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        int currentUserId = ControllerUtils.getCurrentUserId(request);
        return FORWARD + assets.get("DISPLAY_USER_URI") + "/"+currentUserId;
    }
}
