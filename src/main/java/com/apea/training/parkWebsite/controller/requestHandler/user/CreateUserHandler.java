package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        if (ControllerUtils.getCurrentUserRole(request) == User.Role.FORESTER) {throw new AccessDeniedException("User is not the owner or a taskmaster");}

        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String abstractViewName;
        boolean isNewUserCreated = tryToCreateUser(request, formMessages);
        if (isNewUserCreated) {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_CREATE_USER_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            abstractViewName = REDIRECT + assets.get("USER_LIST_URI");
        } else {
            setFormAttributes(request, formMessages);
            request.setAttribute(assets.get("IS_CREATING_USER_ATTR_NAME"), true);
            abstractViewName = FORWARD + assets.get("CREATE_USER_VIEW_NAME");
        }
        return abstractViewName;
    }

    protected boolean areParametersInvalid(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        Set<FrontendMessage> validationMessages = new HashSet<>();
        ControllerUtils.validateName(request.getParameter(assets.get("LOGIN_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("LOGIN_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateName(request.getParameter(assets.get("SUPERIOR_LOGIN_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validatePassword(request.getParameter(assets.get("PASSWORD_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("PASSWORD_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateName(request.getParameter(assets.get("FIRSTNAME_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("FIRSTNAME_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateName(request.getParameter(assets.get("LASTNAME_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("LASTNAME_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateEmail(request.getParameter(assets.get("EMAIL_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("EMAIL_PARAM_NAME"), msg); validationMessages.add(msg);});

        ControllerUtils.validateText(request.getParameter(assets.get("USER_INFO_PARAM_NAME")))
                .ifPresent(msg -> {formMessages.put(assets.get("USER_INFO_PARAM_NAME"), msg); validationMessages.add(msg);});

        return !validationMessages.isEmpty();
    }

    private boolean tryToCreateUser(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        if (areParametersInvalid(request, formMessages)) {return false;}
        AppAssets assets = AppAssets.getInstance();
        UserService userService = ServiceFactoryImpl.getInstance().getUserService();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        if (checkUserExists(login, formMessages)) {return false;}

        String superiorLogin = request.getParameter(assets.get("SUPERIOR_LOGIN_PARAM_NAME"));
        User superior = userService.getByLogin(superiorLogin);
        if (checkSuperiorIsNotExists(superior, formMessages)) {return false;}

        User.Role role = User.Role.valueOf(request.getParameter(assets.get("ROLE_PARAM_NAME")));
        if (isRoleInvalid(role, superior, formMessages)) {return false;}

        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));
        String firstName = request.getParameter(assets.get("FIRSTNAME_PARAM_NAME"));
        String lastName = request.getParameter(assets.get("LASTNAME_PARAM_NAME"));
        String email = request.getParameter(assets.get("EMAIL_PARAM_NAME"));
        String info = request.getParameter(assets.get("USER_INFO_PARAM_NAME"));


        Credential credential = new Credential(login, password);
        User user = new User.Builder().setFirstName(firstName).setLastName(lastName).setEmail(email)
                .setRole(role).setInfo(info).setSuperiorId(superior.getId()).build();
        userService.create(user, credential);
        return true;
    }

    private boolean checkUserExists(String login, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        User user = ServiceFactoryImpl.getInstance().getUserService().getByLogin(login);
        if (user != null) {
            formMessages.put(assets.get("LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_USERNAME_IS_NOT_UNIQUE")));
            return true;
        }
        return false;

    }

    protected boolean checkSuperiorIsNotExists(User superior, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (superior == null) {
            formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_SUPERIOR_LOGIN_IS_INVALID")));
            return true;
        }
        return false;
    }

    protected boolean isRoleInvalid(User.Role role, User superior, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();
        if (role == User.Role.FORESTER && superior.getRole() != User.Role.TASKMASTER) {
            formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_SUPERIOR_FOR_FORESTER_IS_INVALID")));
            return true;
        }
        if (role == User.Role.TASKMASTER && superior.getRole() != User.Role.OWNER) {
            formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_SUPERIOR_FOR_TASKMASTER_IS_INVALID")));
            return true;
        }
        return false;
    }

    protected void setFormAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));
        String firstName = request.getParameter(assets.get("FIRSTNAME_PARAM_NAME"));
        String lastName = request.getParameter(assets.get("LASTNAME_PARAM_NAME"));
        String email = request.getParameter(assets.get("EMAIL_PARAM_NAME"));
        User.Role role = User.Role.valueOf(request.getParameter(assets.get("ROLE_PARAM_NAME")));
        String info = request.getParameter(assets.get("USER_INFO_PARAM_NAME"));
        String superiorLogin = request.getParameter(assets.get("SUPERIOR_LOGIN_PARAM_NAME"));
        request.setAttribute(assets.get("LOGIN_ATTR_NAME"), login);
        request.setAttribute(assets.get("PASSWORD_ATTR_NAME"), password);
        request.setAttribute(assets.get("FIRSTNAME_ATTR_NAME"), firstName);
        request.setAttribute(assets.get("LASTNAME_ATTR_NAME"), lastName);
        request.setAttribute(assets.get("EMAIL_ATTR_NAME"), email);
        request.setAttribute(assets.get("ROLE_ATTR_NAME"), role);
        request.setAttribute(assets.get("USER_INFO_ATTR_NAME"), info);
        request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"), superiorLogin);

        request.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }
}