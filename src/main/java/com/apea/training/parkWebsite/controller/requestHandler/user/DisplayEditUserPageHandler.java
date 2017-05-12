package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayEditUserPageHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        User userToEdit = getUser(request);
        if (!ifCurrentUserHasRights(request, userToEdit)) {throw new AccessDeniedException("Current user doesn't have" +
                " rights to edit user with id " + userToEdit.getId());}

        setFormAttributes(request, userToEdit);
        request.setAttribute(assets.get("IS_CREATING_USER_ATTR_NAME"), false);
        return FORWARD + assets.get("CREATE_USER_VIEW_NAME");
    }

    private User getUser(HttpServletRequest request) {
        String userId = request.getParameter(AppAssets.getInstance().get("ID_PARAM_NAME"));
        return ServiceFactoryImpl.getInstance().getUserService().getById(Integer.valueOf(userId));
    }

    private boolean ifCurrentUserHasRights(HttpServletRequest request, User userToEdit) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return true;
        }
        if (currentUser.getRole() == User.Role.FORESTER) {
            return false;
        }
        if (userToEdit.getId().equals(currentUser.getId())) {
            return true;
        }
        if (userToEdit.getSuperiorId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }

    private void setFormAttributes(HttpServletRequest request, User userToEdit) {
        AppAssets assets = AppAssets.getInstance();
        String userId = request.getParameter(assets.get("ID_PARAM_NAME"));
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByUserId(Integer.valueOf(userId));
        request.setAttribute(assets.get("LOGIN_ATTR_NAME"), credential.getLogin());
        request.setAttribute(assets.get("PASSWORD_ATTR_NAME"), credential.getPassword());
        request.setAttribute(assets.get("FIRSTNAME_ATTR_NAME"), userToEdit.getFirstName());
        request.setAttribute(assets.get("LASTNAME_ATTR_NAME"), userToEdit.getLastName());
        request.setAttribute(assets.get("EMAIL_ATTR_NAME"), userToEdit.getEmail());
        request.setAttribute(assets.get("ROLE_ATTR_NAME"), userToEdit.getRole().toString());
        request.setAttribute(assets.get("USER_INFO_ATTR_NAME"), userToEdit.getInfo());
        if (userToEdit.getRole() != User.Role.OWNER) {
            request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"),
                    ServiceFactoryImpl.getInstance().getCredentialService()
                            .getByUserId(userToEdit.getSuperiorId()).getLogin());
        }
    }
}
