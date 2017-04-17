package com.web.apea.parkWebsite.controller.requestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {
    String FORWARD = "forward:";
    String REDIRECT = "redirect:";
    String NO_ACTION = "noAction:noUri";

    String handle(HttpServletRequest request, HttpServletResponse response);
}
