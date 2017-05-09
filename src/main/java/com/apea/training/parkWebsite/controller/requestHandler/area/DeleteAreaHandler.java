package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Plant;
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
        List<FrontendMessage> generalMessages = new ArrayList<>();
        tryToDeleteArea(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("AREA_LIST_URI");
    }

    private void tryToDeleteArea(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        List<Plant> areaPlants = ServiceFactoryImpl.getInstance().getPlantService().getAllOn(id);
        if (!areaPlants.isEmpty()) {
            generalMessages.add(FrontMessageFactory.getInstance().getError(assets.get("MSG_DELETE_AREA_ERROR")));
        } else {
            AreaService areaService = ServiceFactoryImpl.getInstance().getAreaService();
            Area area = areaService.getById(id);
            areaService.delete(area);
            generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_AREA_SUCCESS")));
        }
    }
}
