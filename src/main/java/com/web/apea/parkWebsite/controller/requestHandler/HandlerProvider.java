package com.web.apea.parkWebsite.controller.requestHandler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerProvider {

    RequestHandler getRequestHandler(HttpServletRequest request);
}
