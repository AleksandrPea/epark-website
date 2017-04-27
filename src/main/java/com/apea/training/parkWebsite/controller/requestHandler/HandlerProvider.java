package com.apea.training.parkWebsite.controller.requestHandler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerProvider {

    RequestHandler getRequestHandler(HttpServletRequest request);
}
