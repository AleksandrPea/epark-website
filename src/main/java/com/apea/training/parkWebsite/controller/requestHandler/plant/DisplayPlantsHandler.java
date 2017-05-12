package com.apea.training.parkWebsite.controller.requestHandler.plant;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DisplayPlantsHandler implements RequestHandler {

    private final Integer PAGE_ROW_NUM = 3;
    private final Integer PAGE_COLUMN_NUM = 3;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        String areaId = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        String page = request.getParameter(assets.get("PAGE_PARAM_NAME"));
        if (areaId == null || page == null) {return REDIRECT + assets.get("HOME_PAGE");}

        List<Plant> areaPlants = ServiceFactoryImpl.getInstance().getPlantService().getAllOn(Integer.valueOf(areaId));
        request.setAttribute(assets.get("PLANTS_ATTR_NAME"), makePlantPage(Integer.valueOf(page), areaPlants));
        request.setAttribute(assets.get("MAX_PLANT_PAGES_ATTR_NAME"), calcMaxPage(areaPlants));
        request.setAttribute(assets.get("CURRENT_PLANT_PAGE_ATTR_NAME"), page);
        request.setAttribute(assets.get("AREA_ATTR_NAME"),
                ServiceFactoryImpl.getInstance().getAreaService().getById(Integer.valueOf(areaId)));
        return FORWARD + assets.get("PLANT_LIST_VIEW_NAME");
    }

    private Plant[][] makePlantPage(int page, List<Plant> areaPlants) {
        int index = (page-1)*PAGE_ROW_NUM*PAGE_COLUMN_NUM;
        int restPlants = areaPlants.size() - index;
        int rowNum = restPlants >= PAGE_ROW_NUM*PAGE_COLUMN_NUM ? PAGE_ROW_NUM :
                (int)Math.ceil((double)restPlants / PAGE_COLUMN_NUM);
        Plant[][] plants = new Plant[rowNum][];
        for (int i = 0; i < rowNum; i++) {
            int columnNum = restPlants < PAGE_COLUMN_NUM ? restPlants : PAGE_COLUMN_NUM;
            plants[i] = new Plant[columnNum];
            for (int j = 0; j < columnNum; j++) {
                plants[i][j] = areaPlants.get(index++);
            }
            restPlants -= PAGE_COLUMN_NUM;
        }
        return plants;
    }

    private int calcMaxPage(List<Plant> areaPlants) {
        return (int) Math.ceil((double)areaPlants.size() / (PAGE_ROW_NUM*PAGE_COLUMN_NUM));
    }
}