package com.apea.training.parkWebsite.controller.requestHandler.sign;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOutHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        HttpSession session = request.getSession();
        session.removeAttribute(assets.get("CURRENT_USER_ID_ATTR_NAME"));
        session.removeAttribute(assets.get("CURRENT_USER_ROLE_ATTR_NAME"));
        session.invalidate();
        return REDIRECT + assets.get("LOGIN_PAGE");
    }
}