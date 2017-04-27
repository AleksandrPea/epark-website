package com.apea.training.parkWebsite.view;


public interface ViewResolver {

    /** Constructs full path to specified private view */
    String resolvePrivateViewName(String viewName);

    /** Constructs full path to specified public view */
    String resolvePublicViewName(String viewName);
}
