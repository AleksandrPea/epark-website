package com.apea.training.parkWebsite.controller.requestHandler.sign;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SignInHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private CredentialService credentialsService = ServiceFactoryImpl.getInstance().getCredentialsSerice();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> messages = new HashMap<>();
        String redirectUri;
        if (isCredentialCorrect(request)) {
            redirectUri = signInUserAndGetForwardUri(request);
        } else {
            addErrorMessages(messages);
            redirectUri = assets.get("LOGIN_PAGE");
        }
        setSessionAttributes(request, messages);

        return REDIRECT + redirectUri;
    }

    private boolean isCredentialCorrect(HttpServletRequest request) {
        String login = request.getParameter(assets.get("SIGN_IN_LOGIN_PARAM_NAME"));
        Credential credential = credentialsService.getByLogin(login);
        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));

        return credential != null && password.equals(credential.getPassword());
    }

    private String signInUserAndGetForwardUri(HttpServletRequest request) {
        String login = request.getParameter(assets.get("SIGN_IN_LOGIN_PARAM_NAME"));
        User currentUser = userService.getByLogin(login);
        HttpSession session = request.getSession();
        session.setAttribute(assets.get("CURRENT_USER_ATTR_NAME"), currentUser);

        return assets.get("DISPLAY_CURRENT_USER_URI");
    }

    private void addErrorMessages(Map<String, FrontendMessage> messages) {
        messages.put(assets.get("SIGN_IN_LOGIN_PARAM_NAME"),
                messageFactory.getError(assets.get("MSG_CREDENTIALS_ARE_NOT_CORRECT")));
        messages.put(assets.get("PASSWORD_PARAM_NAME"),
                messageFactory.getError(assets.get("MSG_CREDENTIALS_ARE_NOT_CORRECT")));
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> messages) {
        HttpSession session = request.getSession();
        String login = request.getParameter(assets.get("SIGN_IN_LOGIN_PARAM_NAME"));
        session.setAttribute(assets.get("SIGN_IN_LOGIN_ATTR_NAME"), login);
        session.setAttribute(assets.get("MESSAGES_ATTR_NAME"), messages);
    }
}