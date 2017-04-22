package com.apea.training.parkWebsite.controller.requestHandler;

import javax.servlet.http.HttpServletRequest;

public class HandlerProviderImpl implements HandlerProvider {

    private static HandlerProviderImpl instance = new HandlerProviderImpl();

    private HandlerProviderImpl() {}

    public static HandlerProviderImpl getInstance() {
        return instance;
    }

    @Override
    public RequestHandler getRequestHandler(HttpServletRequest request) {
        return null;
    }
}
