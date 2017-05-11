package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.CredentialService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import java.util.Map;

public class DisplayUserHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}

        CredentialService credentialService = ServiceFactoryImpl.getInstance().getCredentialService();
        UserService userService = ServiceFactoryImpl.getInstance().getUserService();
        int userId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        User user = userService.getById(userId);
        request.setAttribute(assets.get("USER_ATTR_NAME"), user);
        request.setAttribute(assets.get("CREDENTIAL_ATTR_NAME"),
                credentialService.getByUserId(userId));
        if (user.getSuperiorId() != null) {
            request.setAttribute(assets.get("SUPERIOR_LOGIN_ATTR_NAME"),
                    credentialService.getByUserId(user.getSuperiorId()).getLogin());
        }
        if (user.getRole() != User.Role.FORESTER) {
            Map<Integer, String> subordinatesMap = userService.getAllSubordinatesOf(user)
                    .stream()
                    .collect(Collectors.toMap(User::getId,
                            subor -> credentialService.getByUserId(subor.getId()).getLogin()));
            request.setAttribute(assets.get("SUBORDINATES_ATTR_NAME"),subordinatesMap);
        }
        return FORWARD + assets.get("DISPLAY_USER_VIEW_NAME");
    }
}