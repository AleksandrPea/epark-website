package com.apea.training.parkWebsite.controller.listener;

import com.apea.training.parkWebsite.controller.AppAssets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("assets", AppAssets.getInstance());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}