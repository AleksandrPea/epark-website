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

public class EditPlantHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        editPlant(request);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_EDIT_PLANT_SUCCESS")));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        return REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"/"+areaId+"/1";
    }

    private void editPlant(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String id = request.getParameter(assets.get("PLANT_ID_PARAM_NAME"));
        String name = request.getParameter(assets.get("PLANT_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("PLANT_DESCRIPTION_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("PLANT_IMG_PATH_PARAM_NAME"));
        String state = request.getParameter(assets.get("PLANT_STATE_PARAM_NAME"));
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        Plant plant = new Plant.Builder().setId(Integer.valueOf(id)).setName(name)
                .setDescription(description).setImgPath(imgPath).setState(Plant.State.valueOf(state))
                .setAreaId(Integer.valueOf(areaId)).build();
        ServiceFactoryImpl.getInstance().getPlantService().update(plant);
    }
}