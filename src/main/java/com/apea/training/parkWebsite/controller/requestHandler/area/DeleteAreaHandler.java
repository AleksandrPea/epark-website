package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.AreaService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteAreaHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) != User.Role.OWNER) {throw new AccessDeniedException("User is not the owner");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        tryToDeleteArea(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("AREA_LIST_URI");
    }

    private void tryToDeleteArea(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        Integer areaId = Integer.valueOf(request.getParameter(assets.get("ID_PARAM_NAME")));
        List<Plant> areaPlants = ServiceFactoryImpl.getInstance().getPlantService().getAllOn(areaId);
        if (!areaPlants.isEmpty()) {
            generalMessages.add(FrontMessageFactory.getInstance().getError(assets.get("MSG_DELETE_AREA_ERROR")));
        } else {
            AreaService areaService = ServiceFactoryImpl.getInstance().getAreaService();
            Area area = areaService.getById(areaId);
            areaService.delete(area);
            generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_AREA_SUCCESS")));
        }
    }
}
