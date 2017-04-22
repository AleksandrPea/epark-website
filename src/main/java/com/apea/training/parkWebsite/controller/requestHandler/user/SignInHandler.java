package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static com.apea.training.parkWebsite.controller.AppResources.*;

public class SignInHandler implements RequestHandler {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    SignInHandler() {}

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;
        if (isCredentialCorrect(request)) {
            redirectUri = signInUserAndGetRedirectUri(request);
        } else {
            addErrorMessages(messages);
            redirectUri = LOGIN_PAGE;
        }
        setSessionAttributes(request, messages);

        return REDIRECT + redirectUri;
    }

    private boolean isCredentialCorrect(HttpServletRequest request) {
        String login = request.getParameter(SIGN_IN_LOGIN_PARAM_NAME);
        Credential credential = serviceFactory.getCredentialsService().getByLogin(login);
        String password = request.getParameter(USER_PASSWORD_PARAM_NAME);

        return credential != null && password.equals(credential.getPassword());
    }

    private String signInUserAndGetRedirectUri(HttpServletRequest request) {
        String login = request.getParameter(SIGN_IN_LOGIN_PARAM_NAME);
        User currentUser = serviceFactory.getUserService().getByLogin(login);
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_USER_ATTR_NAME, currentUser);

        return CURRENT_USER_ACCOUNT_URI;
    }

    private void addErrorMessages(Map<String, FrontendMessage> messages) {
        messages.put(SIGN_IN_LOGIN_PARAM_NAME,
                messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));
        messages.put(USER_PASSWORD_PARAM_NAME, messageFactory.getError(MSG_CREDENTIALS_ARE_NOT_CORRECT));
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> messages) {
        HttpSession session = request.getSession();
        String login = request.getParameter(SIGN_IN_LOGIN_PARAM_NAME);
        session.setAttribute(USER_LOGIN_ATTR_NAME, login);
        session.setAttribute(MESSAGES_ATTR_NAME, messages);
    }
}
