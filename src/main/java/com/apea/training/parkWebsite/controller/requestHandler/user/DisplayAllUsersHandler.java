package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.domain.Credentials;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialsService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

public class DisplayAllUsersHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private CredentialsService credentialsService = ServiceFactoryImpl.getInstance().getCredentialsSerice();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<Pair<User, Credentials>> users = userService.getAll()
                .stream()
                .map(user -> new Pair<>(user, credentialsService.getByUserId(user.getId())))
                .collect(Collectors.toList());
        request.setAttribute(assets.get("ALL_USERS_ATTR_NAME"), users);
        return FORWARD + assets.get("USER_LIST_VIEW_NAME");
    }
}