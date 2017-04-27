package com.apea.training.parkWebsite.view;


public interface ViewResolver {

    String resolvePrivateViewName(String viewName);

    String resolvePublicViewName(String viewName);
}
