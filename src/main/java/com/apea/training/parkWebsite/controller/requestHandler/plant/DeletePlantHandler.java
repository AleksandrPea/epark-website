package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.PlantService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeletePlantHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        Integer areaId = deletePlant(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"?"+assets.get("AREA_ID_PARAM_NAME")+
                "="+areaId+"&"+assets.get("PAGE_PARAM_NAME")+"=1";
    }

    /** @return area id */
    private Integer deletePlant(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        PlantService plantService = ServiceFactoryImpl.getInstance().getPlantService();
        Integer plantId = Integer.valueOf(request.getParameter(assets.get("ID_PARAM_NAME")));
        Plant plant = plantService.getById(plantId);
        plantService.delete(plant);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_PLANT_SUCCESS")));
        return plant.getAreaId();
    }
}