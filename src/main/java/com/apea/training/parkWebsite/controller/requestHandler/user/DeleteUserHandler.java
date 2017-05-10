package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
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

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        User userToDelete = getUser(request);
        if (!ifCurrentUserHasRights(request, userToDelete)) {throw new AccessDeniedException("Current user doesn't have" +
                " rights to delete user with id " + userToDelete.getId());}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isUserDeleted = tryToDeleteUser(userToDelete, generalMessages);
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

    private User getUser(HttpServletRequest request) {
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        return ServiceFactoryImpl.getInstance().getUserService().getById(id);
    }

    private boolean ifCurrentUserHasRights(HttpServletRequest request, User userToDelete) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return true;
        }
        if (currentUser.getRole() == User.Role.FORESTER) {
            return false;
        }
        if (userToDelete.getSuperiorId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToDeleteUser(User user, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        UserService userService = ServiceFactoryImpl.getInstance().getUserService();
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