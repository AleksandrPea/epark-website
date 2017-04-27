package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apea.training.parkWebsite.controller.AppAssets.*;

public class CreateUserHandler implements RequestHandler {

    private UserService userService = ServiceFactory.getInstance().getUserService();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String redirectUri = CREATE_USER_URI;
        boolean isNewUserCreated = tryToCreateUser(request, formMessages);
        if (isNewUserCreated) {
            redirectUri = USER_LIST_URI;
        }
        if (CREATE_USER_URI.equals(redirectUri)) {
            setSessionAttributes(request, formMessages);
        } else {
            removeSessionAttributes(request);
            generalMessages.add(messageFactory.getSuccess(MSG_CREATE_USER_SUCCESS));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        }

        return REDIRECT + redirectUri;
    }

    private boolean tryToCreateUser(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        String login = request.getParameter(LOGIN_PARAM_NAME);
        if (checkUserExists(login, formMessages)) {return false;}

        String superiorLogin = request.getParameter(SUPERIOR_LOGIN_PARAM_NAME);
        User superior = userService.getByLogin(superiorLogin);
        if (checkSuperiorIsNotExists(superior, formMessages)) {return false;}

        User.ROLE role = User.ROLE.valueof(request.getParameter(ROLE_PARAM_NAME));
        if (isRoleInvalid(role, superior, formMessages)) {return false;}

        String password = request.getParameter(PASSWORD_PARAM_NAME);
        String firstName = request.getParameter(FIRSTNAME_PARAM_NAME);
        String lastName = request.getParameter(LASTNAME_PARAM_NAME);
        String email = request.getParameter(EMAIL_PARAM_NAME);
        String info = request.getParameter(USER_INFO_PARAM_NAME);

        Credential credential = new Credential(login, password);
        User user = new User.Builder().setFirstName(firstName).setLastName(lastName).setEmail(email)
                .setRole(User.Role.valueof(role)).setInfo(info).setSuperiorId(superior.getId()).build();
        userService.create(user, credential);
        return true;
    }

    private boolean checkUserExists(String login, Map<String, FrontendMessage> formMessages) {
        User user = userService.getByLogin(login);
        if (user != null) {
            formMessages.put(LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_USERNAME_IS_NOT_UNIQUE));
            return true;
        }
        return false;

    }

    private boolean checkSuperiorIsNotExists(User superior, Map<String, FrontendMessage> formMessages) {
        if (superior == null) {
            formMessages.put(SUPERIOR_LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_SUPERIOR_LOGIN_IS_INVALID));
            return true;
        }
        return false;
    }

    private boolean isRoleInvalid(User.ROLE role, User superior, Map<String, FrontendMessage> formMessages) {
        if (role == User.ROLE.FORESTER && superior.getRole() != User.ROLE.TASKMASTER) {
            formMessages.put(SUPERIOR_LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_SUPERIOR_FOR_FORESTER_IS_INVALID));
            return true;
        }
        if (role == User.ROLE.TASKMASTER && superior.getRole() != User.ROLE.OWNER) {
            formMessages.put(SUPERIOR_LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_SUPERIOR_FOR_TASKMASTER_IS_INVALID));
            return true;
        }
        return false;
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN_PARAM_NAME);
        String password = request.getParameter(PASSWORD_PARAM_NAME);
        String firstName = request.getParameter(FIRSTNAME_PARAM_NAME);
        String lastName = request.getParameter(LASTNAME_PARAM_NAME);
        String email = request.getParameter(EMAIL_PARAM_NAME);
        User.ROLE role = User.ROLE.valueof(request.getParameter(ROLE_PARAM_NAME));
        String info = request.getParameter(USER_INFO_PARAM_NAME);
        String superiorLogin = request.getParameter(SUPERIOR_LOGIN_PARAM_NAME);
        session.setAttribute(LOGIN_ATTR_NAME, login);
        session.setAttribute(PASSWORD_ATTR_NAME, password);
        session.setAttribute(FIRSTNAME_ATTR_NAME, firstName);
        session.setAttribute(LASTNAME_ATTR_NAME, lastName);
        session.setAttribute(EMAIL_ATTR_NAME, email);
        session.setAttribute(ROLE_ATTR_NAME, role);
        session.setAttribute(USER_INFO_ATTR_NAME, info);
        session.setAttribute(SUPERIOR_LOGIN_ATTR_NAME, superiorLogin);

        session.setAttribute(MESSAGES_ATTR_NAME, formMessages);
    }

    private void removeSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(LOGIN_ATTR_NAME);
        session.removeAttribute(PASSWORD_ATTR_NAME);
        session.removeAttribute(FIRSTNAME_ATTR_NAME);
        session.removeAttribute(LASTNAME_ATTR_NAME);
        session.removeAttribute(EMAIL_ATTR_NAME);
        session.removeAttribute(ROLE_ATTR_NAME);
        session.removeAttribute(USER_INFO_ATTR_NAME);
        session.removeAttribute(SUPERIOR_LOGIN_ATTR_NAME);
    }
}