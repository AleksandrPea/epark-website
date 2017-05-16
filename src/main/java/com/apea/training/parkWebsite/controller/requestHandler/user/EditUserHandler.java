package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditUserHandler extends CreateUserHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        User userToEdit = getUser(request);
        if (!ifCurrentUserHasRights(request, userToEdit)) {throw new AccessDeniedException("Current user doesn't have" +
                " rights to edit user with id " + userToEdit.getId());}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String abstractViewName;
        boolean isUserEdited = tryToEditUser(request, userToEdit, formMessages);
        if (isUserEdited) {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_EDIT_USER_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
            abstractViewName = REDIRECT + assets.get("DISPLAY_USER_URI") +"?"+assets.get("ID_PARAM_NAME")+
                    "="+ServiceFactoryImpl.getInstance().getCredentialService().getByLogin(login).getUserId();
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_USER_ATTR_NAME"), false);
            abstractViewName = FORWARD + assets.get("CREATE_USER_VIEW_NAME");
        }
        return abstractViewName;
    }

    private User getUser(HttpServletRequest request) {
        String login = request.getParameter(AppAssets.getInstance().get("LOGIN_PARAM_NAME"));
        return ServiceFactoryImpl.getInstance().getUserService().getByLogin(login);
    }

    private boolean ifCurrentUserHasRights(HttpServletRequest request, User userToEdit) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        if (currentUser.getRole() == User.Role.OWNER) {
            return true;
        }
        if (userToEdit.getId().equals(currentUser.getId())) {
            return true;
        }
        if (currentUser.getRole() == User.Role.FORESTER) {
            return false;
        }

        if (userToEdit.getSuperiorId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToEditUser(HttpServletRequest request, User user, Map<String, FrontendMessage> formMessages) {
        if (areParametersInvalid(request, formMessages)) {return false;}
        AppAssets assets = AppAssets.getInstance();
        UserService userService = ServiceFactoryImpl.getInstance().getUserService();
        String superiorLogin = request.getParameter(assets.get("SUPERIOR_LOGIN_PARAM_NAME"));
        if(user.getRole() == User.Role.OWNER &&
                checkOwnerSuperiorIsNotEmpty(superiorLogin, formMessages)) {return false;}

        User superior = null;
        if (user.getRole() != User.Role.OWNER) {superior = userService.getByLogin(superiorLogin);}

        if (user.getRole() != User.Role.OWNER &&
                checkSuperiorIsNotExists(superior, formMessages)) {return false;}
        Integer superiorId = superior != null ? superior.getId() : null;

        User.Role role = User.Role.valueOf(request.getParameter(assets.get("ROLE_PARAM_NAME")));
        if (isRoleInvalid(role, superior, formMessages)) {return false;}

        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));
        String firstName = request.getParameter(assets.get("FIRSTNAME_PARAM_NAME"));
        String lastName = request.getParameter(assets.get("LASTNAME_PARAM_NAME"));
        String email = request.getParameter(assets.get("EMAIL_PARAM_NAME"));
        String info = request.getParameter(assets.get("USER_INFO_PARAM_NAME"));

        String login = request.getParameter(AppAssets.getInstance().get("LOGIN_PARAM_NAME"));
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByLogin(login);
        credential.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setInfo(info);
        user.setSuperiorId(superiorId);
        userService.update(user, credential);
        return true;
    }

    @Override
    protected boolean isRoleInvalid(User.Role role, User superior, Map<String, FrontendMessage> formMessages) {
        return superior != null && super.isRoleInvalid(role, superior, formMessages);
    }

    private boolean checkOwnerSuperiorIsNotEmpty(String superiorLogin, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (!superiorLogin.isEmpty()) {
            formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_OWNER_SUPERIOR_IS_NOT_EMPTY")));
            return true;
        }
        return false;
    }

}