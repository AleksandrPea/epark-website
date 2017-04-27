package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.DISPLAY_USER_VIEW_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.USER_ATTR_NAME;

public class DisplayUserHandler implements RequestHandler {

    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        int userId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        request.setAttribute(USER_ATTR_NAME, userService.getById(userId));

        return FORWARD + DISPLAY_USER_VIEW_NAME;
    }
}
