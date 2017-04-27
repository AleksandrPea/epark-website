package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.DISPLAY_USER_URI;

public class DisplayCurrentUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        int currentUserId = ControllerUtils.getCurrentUserId(request);
        return FORWARD + DISPLAY_USER_URI + "/"+currentUserId;
    }
}
