package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CreateAreaHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        createArea(request);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_CREATE_AREA_SUCCESS")));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("AREA_LIST_URI");
    }

    private void createArea(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String name = request.getParameter(assets.get("AREA_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("AREA_DESCRIPTION_PARAM_NAME"));
        Area area = new Area.Builder().setName(name).setDescription(description).build();
        ServiceFactoryImpl.getInstance().getAreaService().create(area);
    }
}