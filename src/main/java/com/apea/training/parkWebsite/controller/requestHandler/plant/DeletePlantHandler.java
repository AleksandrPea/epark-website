package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
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
        List<FrontendMessage> generalMessages = new ArrayList<>();
        Integer areaId = deletePlant(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"/"+areaId+"/1";
    }

    /** @return area id */
    private Integer deletePlant(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        PlantService plantService = ServiceFactoryImpl.getInstance().getPlantService();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Plant plant = plantService.getById(id);
        plantService.delete(plant);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_PLANT_SUCCESS")));
        return plant.getAreaId();
    }
}