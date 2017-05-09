package com.apea.training.parkWebsite.controller.requestHandler;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.requestHandler.area.*;
import com.apea.training.parkWebsite.controller.requestHandler.plant.*;
import com.apea.training.parkWebsite.controller.requestHandler.report.CreateReportHandler;
import com.apea.training.parkWebsite.controller.requestHandler.report.DeleteReportHandler;
import com.apea.training.parkWebsite.controller.requestHandler.sign.SignInHandler;
import com.apea.training.parkWebsite.controller.requestHandler.sign.SignOutHandler;
import com.apea.training.parkWebsite.controller.requestHandler.task.*;
import com.apea.training.parkWebsite.controller.requestHandler.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HandlerProviderImpl implements HandlerProvider {

    private static AppAssets assets = AppAssets.getInstance();

    public final String POST_SIGN_IN_REQUEST_PATTERN = "POST:" + assets.get("SIGN_IN_URI");
    public final String GET_ALL_USERS_REQUEST_PATTERN = "GET:" + assets.get("USER_LIST_URI") +"/?";
    public final String GET_CURRENT_USER_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_CURRENT_USER_URI") +"/?";
    public final String GET_ONE_USER_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_USER_URI") +"/\\d/?";
    public final String GET_EDIT_USER_REQUEST_PATTERN = "GET:" + assets.get("EDIT_USER_URI") +"/\\d/?";
    public final String POST_EDIT_USER_REQUEST_PATTERN = "POST:" + assets.get("EDIT_USER_URI") +"/?";
    public final String GET_DELETE_USER_REQUEST_PATTERN = "GET:" + assets.get("DELETE_USER_URI") +"/\\d/?";
    public final String GET_CREATE_USER_REQUEST_PATTERN = "GET:" + assets.get("CREATE_USER_URI") +"/?";
    public final String POST_CREATE_USER_REQUEST_PATTERN = "POST:" + assets.get("CREATE_USER_URI") +"/?";

    public final String GET_ONE_TASK_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_TASK_URI") +"/\\d+/?";
    public final String GET_USER_TASKS_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_USER_TASKS_URI") +"/?";
    public final String GET_DELETE_TASK_REQUEST_PATTERN = "GET:" + assets.get("DELETE_TASK_URI") +"/\\d/?";
    public final String GET_CREATE_TASK_REQUEST_PATTERN = "GET:" + assets.get("CREATE_TASK_URI") +"/?";
    public final String POST_CREATE_TASK_REQUEST_PATTERN = "POST:" + assets.get("CREATE_TASK_URI") +"/?";

    public final String GET_DELETE_REPORT_REQUEST_PATTERN = "GET:" + assets.get("DELETE_REPORT_URI") +"/\\d/?";
    public final String POST_CREATE_REPORT_REQUEST_PATTERN = "POST:" + assets.get("CREATE_REPORT_URI") +"/\\d+/?";

    public final String GET_ALL_AREAS_REQUEST_PATTERN = "GET:" + assets.get("AREA_LIST_URI") +"/?";
    public final String GET_EDIT_AREA_REQUEST_PATTERN = "GET:" + assets.get("EDIT_AREA_URI") +"/\\d/?";
    public final String POST_EDIT_AREA_REQUEST_PATTERN = "POST:" + assets.get("EDIT_AREA_URI") +"/?";
    public final String GET_DELETE_AREA_REQUEST_PATTERN = "GET:" + assets.get("DELETE_AREA_URI") +"/\\d/?";
    public final String GET_CREATE_AREA_REQUEST_PATTERN = "GET:" + assets.get("CREATE_AREA_URI") + "/?";
    public final String POST_CREATE_AREA_REQUEST_PATTERN = "POST:" + assets.get("CREATE_AREA_URI") + "/?";

    public final String GET_PLANTS_REQUEST_PATTERN = "GET:" + assets.get("DISPLAY_PLANTS_URI") +"/\\d/\\d/?";
    public final String GET_EDIT_PLANT_REQUEST_PATTERN = "GET:" + assets.get("EDIT_PLANT_URI") +"/\\d/?";
    public final String POST_EDIT_PLANT_REQUEST_PATTERN = "POST:" + assets.get("EDIT_PLANT_URI") +"/?";
    public final String GET_DELETE_PLANT_REQUEST_PATTERN = "GET:" + assets.get("DELETE_PLANT_URI") +"/\\d/?";
    public final String GET_CREATE_PLANT_REQUEST_PATTERN = "GET:" + assets.get("CREATE_PLANT_URI") +"/\\d/?";
    public final String POST_CREATE_PLANT_REQUEST_PATTERN = "POST:" + assets.get("CREATE_PLANT_URI") +"/?";

    public static final String GET_SIGN_OUT_REQUEST_PATTERN = "GET:" + assets.get("SIGN_OUT_URI") +"/?";

    private static HandlerProviderImpl instance = new HandlerProviderImpl();

    private Map<String, RequestHandler> requestMapping = new HashMap<>();

    private HandlerProviderImpl() {}

    {
        requestMapping.put(POST_SIGN_IN_REQUEST_PATTERN,        new SignInHandler());
        requestMapping.put(GET_ALL_USERS_REQUEST_PATTERN,       new DisplayAllUsersHandler());
        requestMapping.put(GET_CURRENT_USER_REQUEST_PATTERN,    new DisplayCurrentUserHandler());
        requestMapping.put(GET_ONE_USER_REQUEST_PATTERN,        new DisplayUserHandler());
        requestMapping.put(GET_EDIT_USER_REQUEST_PATTERN,       new DisplayEditUserPageHandler());
        requestMapping.put(POST_EDIT_USER_REQUEST_PATTERN,      new EditUserHandler());
        requestMapping.put(GET_DELETE_USER_REQUEST_PATTERN,     new DeleteUserHandler());
        requestMapping.put(GET_CREATE_USER_REQUEST_PATTERN,     new DisplayCreateUserPageHandler());
        requestMapping.put(POST_CREATE_USER_REQUEST_PATTERN,    new CreateUserHandler());

        requestMapping.put(GET_ONE_TASK_REQUEST_PATTERN,        new DisplayTaskHandler());
        requestMapping.put(GET_USER_TASKS_REQUEST_PATTERN,      new DisplayUserTasksHandler());
        requestMapping.put(GET_DELETE_TASK_REQUEST_PATTERN,     new DeleteTaskHandler());
        requestMapping.put(GET_CREATE_TASK_REQUEST_PATTERN,     new DisplayCreateTaskPageHandler());
        requestMapping.put(POST_CREATE_TASK_REQUEST_PATTERN,    new CreateTaskHandler());

        requestMapping.put(GET_DELETE_REPORT_REQUEST_PATTERN,   new DeleteReportHandler());
        requestMapping.put(POST_CREATE_REPORT_REQUEST_PATTERN,  new CreateReportHandler());

        requestMapping.put(GET_ALL_AREAS_REQUEST_PATTERN,       new DisplayAllAreasHandler());
        requestMapping.put(GET_EDIT_AREA_REQUEST_PATTERN,       new DisplayEditAreaPageHandler());
        requestMapping.put(POST_EDIT_AREA_REQUEST_PATTERN,      new EditAreaHandler());
        requestMapping.put(GET_DELETE_AREA_REQUEST_PATTERN,     new DeleteAreaHandler());
        requestMapping.put(GET_CREATE_AREA_REQUEST_PATTERN,     new DisplayCreateAreaPageHandler());
        requestMapping.put(POST_CREATE_AREA_REQUEST_PATTERN,    new CreateAreaHandler());

        requestMapping.put(GET_PLANTS_REQUEST_PATTERN,          new DisplayPlantsHandler());
        requestMapping.put(GET_EDIT_PLANT_REQUEST_PATTERN,      new DisplayEditPlantPageHandler());
        requestMapping.put(POST_EDIT_PLANT_REQUEST_PATTERN,     new EditPlantHandler());
        requestMapping.put(GET_DELETE_PLANT_REQUEST_PATTERN,    new DeletePlantHandler());
        requestMapping.put(GET_CREATE_PLANT_REQUEST_PATTERN,    new DisplayCreatePlantPageHandler());
        requestMapping.put(POST_CREATE_PLANT_REQUEST_PATTERN,   new CreatePlantHandler());
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