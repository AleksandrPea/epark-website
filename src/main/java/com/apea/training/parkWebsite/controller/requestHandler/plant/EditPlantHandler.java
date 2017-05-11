package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPlantHandler extends CreatePlantHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isPlantEdited = tryToEditPlant(request, formMessages);
        String abstractViewName;
        if (isPlantEdited) {
            generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_EDIT_PLANT_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
            abstractViewName = REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"/"+areaId+"/1";
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_PLANT_ATTR_NAME"), false);
            abstractViewName = FORWARD + assets.get("CREATE_PLANT_VIEW_NAME");
        }
        return abstractViewName;
    }

    private boolean tryToEditPlant(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        if (areParametersInvalid(request, formMessages)) { return false;}
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
        return true;
    }
}