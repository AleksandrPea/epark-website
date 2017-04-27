package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.PlantService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayCreateTaskPage implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private PlantService plantService = ServiceFactoryImpl.getInstance().getPlantService();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME"),
                getCurrentUserPlants(request));
        return FORWARD + assets.get("CREATE_TASK_VIEW_NAME");
    }

    private List<Plant> getCurrentUserPlants(HttpServletRequest request) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return plantService.getAll();
        } else {
            Area attachedArea = userService.getAttachedArea(currentUser);
            return plantService.getAllOn(attachedArea);
        }
    }
}