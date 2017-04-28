package com.apea.training.parkWebsite.controller.requestHandler;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.area.DisplayAllAreasHandler;
import com.apea.training.parkWebsite.controller.requestHandler.report.CreateReportHandler;
import com.apea.training.parkWebsite.controller.requestHandler.report.DisplayCreateReportPage;
import com.apea.training.parkWebsite.controller.requestHandler.sign.SignInHandler;
import com.apea.training.parkWebsite.controller.requestHandler.sign.SignOutHandler;
import com.apea.training.parkWebsite.controller.requestHandler.task.CreateTaskHandler;
import com.apea.training.parkWebsite.controller.requestHandler.task.DisplayCreateTaskPage;
import com.apea.training.parkWebsite.controller.requestHandler.task.DisplayTaskHandler;
import com.apea.training.parkWebsite.controller.requestHandler.task.DisplayUserTasksHandler;
import com.apea.training.parkWebsite.controller.requestHandler.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HandlerProviderImpl implements HandlerProvider {

    private static AppAssets assets = AppAssets.getInstance();

    public static final String POST_SIGN_IN_REQUEST_PATTERN = "POST:" + assets.get("SIGN_IN_URI");
    public static final String GET_ALL_USERS_REQUEST_PATTERN = "GET:" + assets.get("USER_LIST_URI") +"/?";
    public static final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_CURRENT_USER_URI") +"/?";
    public static final String GET_ONE_USER_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_USER_URI") +"/\\d+/?";
    public static final String GET_CREATE_USER_REQUEST_PATTERN = "GET:" + assets.get("CREATE_USER_URI") +"/?";
    public static final String POST_CREATE_USER_REQUEST_PATTERN = "POST:" + assets.get("CREATE_USER_URI") +"/?";
    public static final String GET_ONE_TASK_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_TASK_URI") +"/\\d+/?";
    public static final String GET_USER_TASKS_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_USER_TASKS_URI") +"/?";
    public static final String GET_CREATE_TASK_REQUEST_PATTERN = "GET:" + assets.get("CREATE_TASK_URI") +"/?";
    public static final String POST_CREATE_TASK_REQUEST_PATTERN = "POST:" + assets.get("CREATE_TASK_URI") +"/?";
    public static final String GET_CREATE_REPORT_REQUEST_PATTERN = "GET:" + assets.get("CREATE_REPORT_URI") +"/\\d+/?";
    public static final String POST_CREATE_REPORT_REQUEST_PATTERN = "POST" + assets.get("CREATE_REPORT_URI") +"/?";
    public static final String GET_ALL_AREAS_REQUEST_PATTERN = "GET:" + assets.get("AREA_LIST_URI") +"/?";

    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:" + assets.get("SIGN_OUT_URI") +"/?";

    private static HandlerProviderImpl instance = new HandlerProviderImpl();

    private Map<String, RequestHandler> requestMapping = new HashMap<>();

    private HandlerProviderImpl() {}

    {
        requestMapping.put(POST_SIGN_IN_REQUEST_PATTERN,        new SignInHandler());
        requestMapping.put(GET_ALL_USERS_REQUEST_PATTERN,       new DisplayAllUsersHandler());
        requestMapping.put(GET_CURRENT_USER_REQUEST_PATTERN,    new DisplayCurrentUserHandler());
        requestMapping.put(GET_ONE_USER_REQUEST_PATTERN,        new DisplayUserHandler());
        requestMapping.put(GET_CREATE_USER_REQUEST_PATTERN,     new DisplayCreateUserPage());
        requestMapping.put(POST_CREATE_USER_REQUEST_PATTERN,    new CreateUserHandler());
        requestMapping.put(GET_ONE_TASK_REQUEST_PATTERN,        new DisplayTaskHandler());
        requestMapping.put(GET_USER_TASKS_REQUEST_PATTERN,      new DisplayUserTasksHandler());
        requestMapping.put(GET_CREATE_TASK_REQUEST_PATTERN,     new DisplayCreateTaskPage());
        requestMapping.put(POST_CREATE_TASK_REQUEST_PATTERN,    new CreateTaskHandler());
        requestMapping.put(GET_CREATE_REPORT_REQUEST_PATTERN,   new DisplayCreateReportPage());
        requestMapping.put(POST_CREATE_REPORT_REQUEST_PATTERN,  new CreateReportHandler());
        requestMapping.put(GET_ALL_AREAS_REQUEST_PATTERN,       new DisplayAllAreasHandler());
        requestMapping.put(GET_SIGN_OUT_REQUEST_PATTERN,        new SignOutHandler());
    }

    public static HandlerProviderImpl getInstance() {
        return instance;
    }

    @Override
    public RequestHandler getRequestHandler(HttpServletRequest request) {
        String reqMethod = request.getMethod().toUpperCase();
        String reqUri = request.getRequestURI();
        Optional<RequestHandler> currentHandler = requestMapping.entrySet()
                .stream()
                .filter(mapping -> reqMethod.equals(mapping.getKey().split(":")[0]))
                .filter(mapping -> reqUri.matches(mapping.getKey().split(":")[1]))
                .map(Map.Entry::getValue)
                .findFirst();
        return currentHandler.orElseThrow(()->
                new NoSuchElementException("There is no mapping for " + reqMethod+":"+reqUri));
    }
}