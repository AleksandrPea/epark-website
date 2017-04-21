package com.apea.training.parkWebsite.controller.requestHandler.user;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppResources.*;

public class SignOutHandler implements RequestHandler {

    SignOutHandler() {}

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(CURRENT_USER_ATTR_NAME);
        request.getSession().invalidate();

        return REDIRECT + LOGIN_PAGE;
    }
}
