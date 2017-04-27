package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Credentials;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateUserHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();

    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String redirectUri = assets.get("CREATE_USER_URI");
        boolean isNewUserCreated = tryToCreateUser(request, formMessages);
        if (isNewUserCreated) {
            redirectUri = assets.get("USER_LIST_URI");
        }
        if (assets.get("CREATE_USER_URI").equals(redirectUri)) {
            setSessionAttributes(request, formMessages);
        } else {
            removeSessionAttributes(request);
            generalMessages.add(messageFactory.getSuccess(assets.get("MSG_CREATE_USER_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        }

        return REDIRECT + redirectUri;
    }

    private boolean tryToCreateUser(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
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

        Credentials credentials = new Credentials(login, password);
        User user = new User.Builder().setFirstName(firstName).setLastName(lastName).setEmail(email)
                .setRole(role).setInfo(info).setSuperiorId(superior.getId()).build();
        userService.create(user, credentials);
        return true;
    }

    private boolean checkUserExists(String login, Map<String, FrontendMessage> formMessages) {
        User user = userService.getByLogin(login);
        if (user != null) {
            formMessages.put(assets.get("LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_USERNAME_IS_NOT_UNIQUE")));
            return true;
        }
        return false;

    }

    private boolean checkSuperiorIsNotExists(User superior, Map<String, FrontendMessage> formMessages) {
        if (superior == null) {
            formMessages.put(assets.get("SUPERIOR_LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_SUPERIOR_LOGIN_IS_INVALID")));
            return true;
        }
        return false;
    }

    private boolean isRoleInvalid(User.Role role, User superior, Map<String, FrontendMessage> formMessages) {
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

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        HttpSession session = request.getSession();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));
        String firstName = request.getParameter(assets.get("FIRSTNAME_PARAM_NAME"));
        String lastName = request.getParameter(assets.get("LASTNAME_PARAM_NAME"));
        String email = request.getParameter(assets.get("EMAIL_PARAM_NAME"));
        User.Role role = User.Role.valueOf(request.getParameter(assets.get("ROLE_PARAM_NAME")));
        String info = request.getParameter(assets.get("USER_INFO_PARAM_NAME"));
        String superiorLogin = request.getParameter(assets.get("SUPERIOR_LOGIN_PARAM_NAME"));
        session.setAttribute(assets.get("LOGIN_ATTR_NAME"), login);
        session.setAttribute(assets.get("PASSWORD_ATTR_NAME"), password);
        session.setAttribute(assets.get("FIRSTNAME_ATTR_NAME"), firstName);
        session.setAttribute(assets.get("LASTNAME_ATTR_NAME"), lastName);
        session.setAttribute(assets.get("EMAIL_ATTR_NAME"), email);
        session.setAttribute(assets.get("ROLE_ATTR_NAME"), role);
        session.setAttribute(assets.get("USER_INFO_ATTR_NAME"), info);
        session.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"), superiorLogin);

        session.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }

    private void removeSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(assets.get("LOGIN_ATTR_NAME"));
        session.removeAttribute(assets.get("PASSWORD_ATTR_NAME"));
        session.removeAttribute(assets.get("FIRSTNAME_ATTR_NAME"));
        session.removeAttribute(assets.get("LASTNAME_ATTR_NAME"));
        session.removeAttribute(assets.get("EMAIL_ATTR_NAME"));
        session.removeAttribute(assets.get("ROLE_ATTR_NAME"));
        session.removeAttribute(assets.get("USER_INFO_ATTR_NAME"));
        session.removeAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"));
    }
}