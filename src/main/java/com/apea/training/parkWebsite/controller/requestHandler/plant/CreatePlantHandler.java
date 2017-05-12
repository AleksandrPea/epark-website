package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreatePlantHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        if (areaId == null) {return REDIRECT + assets.get("HOME_PAGE");}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isPlantCreated = tryToCreatePlant(request, formMessages);
        String abstractViewName;
        if (isPlantCreated) {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_CREATE_PLANT_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);

            abstractViewName = REDIRECT + assets.get("DISPLAY_PLANTS_URI")+"?"+assets.get("AREA_ID_PARAM_NAME")+
                    "="+areaId+"&"+assets.get("PAGE_PARAM_NAME")+"=1";
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_PLANT_ATTR_NAME"), true);
            abstractViewName = FORWARD + assets.get("CREATE_PLANT_VIEW_NAME");
        }
        return abstractViewName;
    }

    private boolean tryToCreatePlant(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        if (areParametersInvalid(request, formMessages)) {return false;}
        AppAssets assets = AppAssets.getInstance();
        String name = request.getParameter(assets.get("PLANT_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("PLANT_DESCRIPTION_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("PLANT_IMG_PATH_PARAM_NAME"));
        String state = request.getParameter(assets.get("PLANT_STATE_PARAM_NAME"));
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        Plant plant = new Plant.Builder().setName(name).setDescription(description)
                .setImgPath(imgPath).setState(Plant.State.valueOf(state))
                .setAreaId(Integer.valueOf(areaId)).build();
        ServiceFactoryImpl.getInstance().getPlantService().create(plant);
        return true;
    }

    protected boolean areParametersInvalid(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        Set<FrontendMessage> validationMessages = new HashSet<>();
        ControllerUtils.validateName(request.getParameter(assets.get("PLANT_NAME_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("PLANT_NAME_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateText(request.getParameter(assets.get("PLANT_DESCRIPTION_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("PLANT_DESCRIPTION_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateText(request.getParameter(assets.get("PLANT_IMG_PATH_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("PLANT_IMG_PATH_PARAM_NAME"), msg); validationMessages.add(msg);});

        return !validationMessages.isEmpty();
    }

    protected void setFormAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String name = request.getParameter(assets.get("PLANT_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("PLANT_DESCRIPTION_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("PLANT_IMG_PATH_PARAM_NAME"));
        String state = request.getParameter(assets.get("PLANT_STATE_PARAM_NAME"));
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        request.setAttribute(assets.get("PLANT_NAME_ATTR_NAME"), name);
        request.setAttribute(assets.get("PLANT_DESCRIPTION_ATTR_NAME"), description);
        request.setAttribute(assets.get("PLANT_IMG_PATH_ATTR_NAME"), imgPath);
        request.setAttribute(assets.get("PLANT_STATE_ATTR_NAME"), state);
        request.setAttribute(assets.get("AREA_ID_ATTR_NAME"), areaId);

        request.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }
}