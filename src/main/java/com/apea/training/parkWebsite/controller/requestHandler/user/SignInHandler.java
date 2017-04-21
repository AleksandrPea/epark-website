package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.apea.training.parkWebsite.controller.AppResources.*;

public class SignInHandler implements RequestHandler {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    // private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    SignInHandler() {}

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        //Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;

        if (isCredentialCorrect(request)) {
            redirectUri = signInUserAndGetRedirectUri(request);
        } else {
            //addErrorMessages(messages);
            redirectUri = LOGIN_PAGE;
        }

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
}
