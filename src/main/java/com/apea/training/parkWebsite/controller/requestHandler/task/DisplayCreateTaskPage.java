package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.apea.training.parkWebsite.controller.AppAssets.CREATE_TASK_VIEW_NAME;

public class DisplayCreateTaskPage implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        return FORWARD + CREATE_TASK_VIEW_NAME;
    }
}