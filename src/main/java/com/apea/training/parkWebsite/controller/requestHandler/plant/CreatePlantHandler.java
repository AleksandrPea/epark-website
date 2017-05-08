package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CreatePlantHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        createPlant(request);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_CREATE_PLANT_SUCCESS")));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        Integer areaId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        return REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"/"+areaId+"/1";
    }

    private void createPlant(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String name = request.getParameter(assets.get("PLANT_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("PLANT_DESCRIPTION_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("PLANT_IMG_PATH_PARAM_NAME"));
        String state = request.getParameter(assets.get("PLANT_STATE_PARAM_NAME"));
        Integer areaId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        Plant plant = new Plant.Builder().setName(name).setDescription(description)
                .setImgPath(imgPath).setState(Plant.State.valueOf(state)).setAreaId(areaId).build();
        ServiceFactoryImpl.getInstance().getPlantService().create(plant);
    }
}
