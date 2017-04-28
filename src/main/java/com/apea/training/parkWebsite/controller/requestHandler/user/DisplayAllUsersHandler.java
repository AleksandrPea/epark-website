package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DisplayAllUsersHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private CredentialService credentialsService = ServiceFactoryImpl.getInstance().getCredentialsSerice();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userService.getAll();
        Map<Integer, Credential> credentialsMap = credentialsService.getAll()
                .stream()
                .collect(Collectors.toMap(Credential::getUserId, Function.identity()));
        request.setAttribute(assets.get("ALL_USERS_ATTR_NAME"), users);
        request.setAttribute(assets.get("ALL_CREDENTIALS_ATTR_NAME"), credentialsMap);
        return FORWARD + assets.get("USER_LIST_VIEW_NAME");
    }
}