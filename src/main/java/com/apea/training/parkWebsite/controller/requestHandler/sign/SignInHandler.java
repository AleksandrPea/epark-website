package com.apea.training.parkWebsite.controller.requestHandler.sign;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SignInHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        Map<String, FrontendMessage> messages = new HashMap<>();
        String abstractView;
        if (isCredentialCorrect(request)) {
            signInUser(request);
            abstractView = REDIRECT + assets.get("DISPLAY_CURRENT_USER_URI");
        } else {
            addErrorMessages(messages);
            abstractView = FORWARD + assets.get("LOGIN_VIEW_NAME");
        }
        setRequestAttributes(request, messages);

        return abstractView;
    }

    private boolean isCredentialCorrect(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        Credential credential = ServiceFactoryImpl.getInstance().getCredentialService().getByLogin(login);
        String password = request.getParameter(assets.get("PASSWORD_PARAM_NAME"));

        return credential != null && password.equals(credential.getPassword());
    }

    private void signInUser(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        User currentUser = ServiceFactoryImpl.getInstance().getUserService().getByLogin(login);
        HttpSession session = request.getSession();
        session.setAttribute(assets.get("CURRENT_USER_ID_ATTR_NAME"), currentUser.getId());
        session.setAttribute(assets.get("CURRENT_USER_ROLE_ATTR_NAME"), currentUser.getRole());
    }

    private void addErrorMessages(Map<String, FrontendMessage> messages) {
        AppAssets assets = AppAssets.getInstance();
        FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();
        messages.put(assets.get("LOGIN_PARAM_NAME"),
                messageFactory.getError(assets.get("MSG_CREDENTIALS_ARE_NOT_CORRECT")));
        messages.put(assets.get("PASSWORD_PARAM_NAME"),
                messageFactory.getError(assets.get("MSG_CREDENTIALS_ARE_NOT_CORRECT")));
    }

    private void setRequestAttributes(HttpServletRequest request, Map<String, FrontendMessage> messages) {
        AppAssets assets = AppAssets.getInstance();
        String login = request.getParameter(assets.get("LOGIN_PARAM_NAME"));
        request.setAttribute(assets.get("LOGIN_ATTR_NAME"), login);
        request.setAttribute(assets.get("MESSAGES_ATTR_NAME"), messages);
    }
}