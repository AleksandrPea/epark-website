package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditAreaHandler extends CreateAreaHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String abstractViewName;
        boolean isUserEdited = tryToEditArea(request, formMessages);
        if (isUserEdited) {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_EDIT_AREA_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            abstractViewName = REDIRECT + assets.get("AREA_LIST_URI");
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_AREA_ATTR_NAME"), false);
            abstractViewName = FORWARD + assets.get("CREATE_AREA_VIEW_NAME");
        }
        return abstractViewName;
    }

    private boolean tryToEditArea(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String taskmasterLogin = request.getParameter(assets.get("TASKMASTER_LOGIN_PARAM_NAME"));
        User taskmaster = ServiceFactoryImpl.getInstance().getUserService().getByLogin(taskmasterLogin);
        if (userIsNotExists(taskmaster, formMessages)) {return false;}
        if (isTaskmasterInvalid(taskmaster, formMessages)) {return false;}
        String id = request.getParameter(assets.get("AREA_ID_PARAM_NAME"));
        String name = request.getParameter(assets.get("AREA_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("AREA_DESCRIPTION_PARAM_NAME"));
        Area area = new Area.Builder().setId(Integer.valueOf(id)).setName(name)
                .setDescription(description).setTaskmasterId(taskmaster.getId()).build();
        ServiceFactoryImpl.getInstance().getAreaService().update(area);
        return true;
    }
}