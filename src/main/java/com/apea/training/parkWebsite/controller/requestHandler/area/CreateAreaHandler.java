package com.apea.training.parkWebsite.controller.requestHandler.area;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateAreaHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) != User.Role.OWNER) {throw new AccessDeniedException("User is not the owner");}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isAreaCreated = tryToCreateArea(request, formMessages);
        String abstractViewName;
        if (isAreaCreated) {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_CREATE_AREA_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            abstractViewName = REDIRECT + assets.get("AREA_LIST_URI");
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_AREA_ATTR_NAME"), true);
            abstractViewName = FORWARD + assets.get("CREATE_AREA_VIEW_NAME");
        }

        return abstractViewName;
    }

    private boolean tryToCreateArea(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        if (areParametersInvalid(request, formMessages)) { return false; }
        AppAssets assets = AppAssets.getInstance();
        String taskmasterLogin = request.getParameter(assets.get("TASKMASTER_LOGIN_PARAM_NAME"));
        User taskmaster = ServiceFactoryImpl.getInstance().getUserService().getByLogin(taskmasterLogin);
        if (userIsNotExists(taskmaster, formMessages)) {return false;}
        if (isTaskmasterInvalid(taskmaster, formMessages)) {return false;}

        String name = request.getParameter(assets.get("AREA_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("AREA_DESCRIPTION_PARAM_NAME"));
        Area area = new Area.Builder().setName(name).setDescription(description)
                .setTaskmasterId(taskmaster.getId()).build();
        ServiceFactoryImpl.getInstance().getAreaService().create(area);
        return true;
    }

    protected boolean areParametersInvalid(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        Set<FrontendMessage> validationMessages = new HashSet<>();
        ControllerUtils.validateName(request.getParameter(assets.get("TASKMASTER_LOGIN_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("TASKMASTER_LOGIN_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateName(request.getParameter(assets.get("AREA_NAME_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("AREA_NAME_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateText(request.getParameter(assets.get("AREA_DESCRIPTION_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("AREA_DESCRIPTION_PARAM_NAME"), msg); validationMessages.add(msg);});

        return !validationMessages.isEmpty();
    }

    protected boolean isTaskmasterInvalid(User taskmaster,Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (taskmaster.getRole() != User.Role.TASKMASTER) {
            formMessages.put(assets.get("TASKMASTER_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_IS_NOT_TASKMASTER")));
            return true;
        }
        return false;
    }

    protected boolean userIsNotExists(User user, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (user == null) {
            formMessages.put(assets.get("TASKMASTER_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_TASKMASTER_LOGIN_IS_INVALID")));
            return true;
        }
        return false;
    }

    protected void setFormAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String taskmasterLogin = request.getParameter(assets.get("TASKMASTER_LOGIN_PARAM_NAME"));
        String name = request.getParameter(assets.get("AREA_NAME_PARAM_NAME"));
        String description = request.getParameter(assets.get("AREA_DESCRIPTION_PARAM_NAME"));
        request.setAttribute(assets.get("TASKMASTER_LOGIN_ATTR_NAME"), taskmasterLogin);
        request.setAttribute(assets.get("AREA_NAME_ATTR_NAME"), name);
        request.setAttribute(assets.get("AREA_DESCRIPTION_ATTR_NAME"), description);

        request.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }
}