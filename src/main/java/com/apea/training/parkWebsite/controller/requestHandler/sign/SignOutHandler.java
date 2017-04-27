package com.apea.training.parkWebsite.controller.requestHandler.sign;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.apea.training.parkWebsite.controller.AppAssets.CURRENT_USER_ATTR_NAME;
import static com.apea.training.parkWebsite.controller.AppAssets.LOGIN_PAGE;

public class SignOutHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(CURRENT_USER_ATTR_NAME);
        session.invalidate();
        return REDIRECT + LOGIN_PAGE;
    }
}