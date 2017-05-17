package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;
import com.apea.training.parkWebsite.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayCreateTaskPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}

        request.setAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME"),
                ControllerUtils.getCurrentUserPlants(request));
        request.setAttribute(assets.get("SUBORDINATES_ATTR_NAME"),
                ControllerUtils.getCurrentUserSubordinateLogins(request));
        return FORWARD + assets.get("CREATE_TASK_VIEW_NAME");
    }
}