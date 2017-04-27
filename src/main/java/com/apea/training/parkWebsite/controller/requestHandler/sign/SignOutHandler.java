package com.apea.training.parkWebsite.controller.requestHandler.sign;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOutHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(assets.get("CURRENT_USER_ATTR_NAME"));
        session.invalidate();
        return REDIRECT + assets.get("LOGIN_PAGE");
    }
}