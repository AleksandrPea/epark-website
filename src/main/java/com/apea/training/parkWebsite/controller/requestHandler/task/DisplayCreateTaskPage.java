package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayCreateTaskPage implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        request.setAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME"),
                getCurrentUserPlants(request));
        return FORWARD + assets.get("CREATE_TASK_VIEW_NAME");
    }

    private List<Plant> getCurrentUserPlants(HttpServletRequest request) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return ServiceFactoryImpl.getInstance().getPlantService().getAll();
        } else {
            Area attachedArea = ServiceFactoryImpl.getInstance().getUserService().getAttachedArea(currentUser);
            return ServiceFactoryImpl.getInstance().getPlantService().getAllOn(attachedArea);
        }
    }
}