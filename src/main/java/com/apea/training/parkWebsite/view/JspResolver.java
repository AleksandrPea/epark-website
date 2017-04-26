package com.apea.training.parkWebsite.view;

public class JspResolver implements ViewResolver {

    private static JspResolver instance = new JspResolver();

    private JspResolver() {}

    public static JspResolver getInstance() {
        return instance;
    }

    @Override
    public String resolvePrivateViewName(String viewName) {
        return String.format("/WEB-INF/backend/%s.jsp", viewName);
    }

    @Override
    public String resolvePublicViewName(String viewName) {
        return String.format("/%s.jsp", viewName);
    }
}