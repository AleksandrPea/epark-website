package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isUserDeleted = tryToDeleteUser(request, generalMessages);
        String redirectUri;
        if (isUserDeleted) {
            redirectUri = assets.get("USER_LIST_URI");
        } else {
            redirectUri = assets.get("DISPLAY_USER_URI") +"/"+
                    ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        }
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + redirectUri;
    }

    private boolean tryToDeleteUser(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        UserService userService = ServiceFactoryImpl.getInstance().getUserService();
        User user = userService.getById(id);
        List<User> subordinates = userService.getAllSubordinatesOf(user);
        if (!subordinates.isEmpty()) {
            generalMessages.add(FrontMessageFactory.getInstance().getError(assets.get("MSG_DELETE_TASKMASTER_SUBS_ERROR")));
            return false;
        }
        List<Area> attachedAreas = userService.getAttachedAreas(user);
        if (!attachedAreas.isEmpty()) {
            generalMessages.add(FrontMessageFactory.getInstance().getError(assets.get("MSG_DELETE_TASKMASTER_AREAS_ERROR")));
            return false;
        }
        userService.delete(user);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_USER_SUCCESS")));
        return true;
    }
}