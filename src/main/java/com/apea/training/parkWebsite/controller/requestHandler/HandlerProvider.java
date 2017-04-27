package com.apea.training.parkWebsite.controller.requestHandler;

import javax.servlet.http.HttpServletRequest;

public interface HandlerProvider {

    /** @return certain handler for request */
    RequestHandler getRequestHandler(HttpServletRequest request);
}
