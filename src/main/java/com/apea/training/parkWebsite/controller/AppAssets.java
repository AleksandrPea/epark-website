package com.apea.training.parkWebsite.controller;

import java.util.*;

public class AppAssets extends AbstractMap<String, String> {

    private static AppAssets instance = new AppAssets();

    private AppAssets() {}

    public static AppAssets getInstance() {
        return instance;
    }

    private Map<String, String> assetsMap = new HashMap<>();

    {
        //************************* ATTRIBUTES **********************************
        assetsMap.put("MESSAGES_ATTR_NAME", "messages");
        assetsMap.put("GENERAL_MESSAGES_BLOCK_NAME", "generalMessages");
        assetsMap.put("CURRENT_USER_ID_ATTR_NAME", "currentUserId");
        assetsMap.put("CURRENT_USER_ATTR_NAME", "currentUser");
        assetsMap.put("USER_ATTR_NAME", "user");
        assetsMap.put("CREDENTIAL_ATTR_NAME", "credentials");
        assetsMap.put("ALL_USERS_ATTR_NAME", "allUsers");
        assetsMap.put("ALL_CREDENTIALS_ATTR_NAME", "allCredentials");
        assetsMap.put("ALL_AREAS_ATTR_NAME", "allAreas");
        assetsMap.put("CURRENT_USER_TASKS_ATTR_NAME", "currentUserTasks");

        assetsMap.put("LOGIN_ATTR_NAME", "login");
        assetsMap.put("PASSWORD_ATTR_NAME", "password");
        assetsMap.put("FIRSTNAME_ATTR_NAME", "firstName");
        assetsMap.put("LASTNAME_ATTR_NAME", "lastName");
        assetsMap.put("EMAIL_ATTR_NAME", "email");
        assetsMap.put("ROLE_ATTR_NAME", "role");
        assetsMap.put("USER_INFO_ATTR_NAME", "info");
        assetsMap.put("SUPERIOR_LOGIN_ATTR_NAME", "superiorLogin");

        assetsMap.put("TASK_RECIEVER_LOGIN_ATTR_NAME", "recieverLogin");
        assetsMap.put("TASK_TITLE_ATTR_NAME", "taskTitle");
        assetsMap.put("TASK_COMMENT_ATTR_NAME", "taskComment");
        assetsMap.put("TASK_PLANTS_ATTR_NAME", "taskPlants");
        assetsMap.put("ALL_TASK_PLANTS_ATTR_NAME", "allTaskPlants");
        assetsMap.put("TASK_ID_ATTR_NAME", "taskId");
        assetsMap.put("TASK_ATTR_NAME", "task");
        assetsMap.put("TASK_REPORTS_ATTR_NAME", "taskReports");

        //************************* PARAMS **********************************
        assetsMap.put("LOGIN_PARAM_NAME", "login");
        assetsMap.put("PASSWORD_PARAM_NAME", "password");
        assetsMap.put("FIRSTNAME_PARAM_NAME", "firstName");
        assetsMap.put("LASTNAME_PARAM_NAME", "lastName");
        assetsMap.put("EMAIL_PARAM_NAME", "email");
        assetsMap.put("ROLE_PARAM_NAME", "role");
        assetsMap.put("USER_INFO_PARAM_NAME", "info");
        assetsMap.put("SUPERIOR_LOGIN_PARAM_NAME", "superiorLogin");

        assetsMap.put("TASK_RECIEVER_LOGIN_PARAM_NAME", "recieverLogin");
        assetsMap.put("TASK_TITLE_PARAM_NAME", "taskTitle");
        assetsMap.put("TASK_COMMENT_PARAM_NAME", "taskComment");
        assetsMap.put("TASK_PLANTS_PARAM_NAME", "taskPlants");
        assetsMap.put("REPORT_COMMENT_PARAM_NAME", "reportComment");
        assetsMap.put("REPORT_IMG_PATH_PARAM_NAME", "reportImg");

        //************************* VIEWS and URIS **********************************
        assetsMap.put("SIGN_IN_URI", "/backend/signIn");
        assetsMap.put("DISPLAY_CURRENT_USER_URI", "/backend/users/currentUser");
        assetsMap.put("DISPLAY_USER_URI", "/backend/users/user");
        assetsMap.put("CREATE_USER_URI", "/backend/users/newUser");
        assetsMap.put("USER_LIST_URI", "/backend/users");
        assetsMap.put("CREATE_TASK_URI", "/backend/tasks/newTask");
        assetsMap.put("DISPLAY_USER_TASKS_URI", "/backend/tasks");
        assetsMap.put("DISPLAY_TASK_URI", "/backend/tasks/task");
        assetsMap.put("CREATE_REPORT_URI", "/backend/reports/newReport");
        assetsMap.put("AREA_LIST_URI", "/backend/areas");
        assetsMap.put("SIGN_OUT_URI", "/backend/signOut");

        assetsMap.put("DISPLAY_USER_VIEW_NAME", "users/user");
        assetsMap.put("USER_LIST_VIEW_NAME", "users/userList");
        assetsMap.put("CREATE_USER_VIEW_NAME", "users/createUser");
        assetsMap.put("CURRENT_USER_TASKS_LIST_VIEW_NAME", "tasks/taskList");
        assetsMap.put("CREATE_TASK_VIEW_NAME", "tasks/createTask");
        assetsMap.put("CREATE_REPORT_VIEW_NAME", "tasks/createReport");
        assetsMap.put("DISPLAY_TASK_VIEW_NAME", "tasks/task");
        assetsMap.put("AREA_LIST_VIEW_NAME", "areas/areaList");

        assetsMap.put("LOGIN_VIEW_NAME", "login");
        assetsMap.put("LOGIN_PAGE", "/login.jsp");
        assetsMap.put("GENERAL_ERROR_PAGE", "/errors/error-page.jsp");

        //************************* MESSAGES **********************************
        assetsMap.put("MSG_CREDENTIALS_ARE_NOT_CORRECT", "validation.credentialsAreNotCorrect");
        assetsMap.put("MSG_USERNAME_IS_NOT_UNIQUE", "validation.usernameIsNotUnique");
        assetsMap.put("MSG_SUPERIOR_LOGIN_IS_INVALID", "validation.superiorLoginIsInvalid");
        assetsMap.put("MSG_SUPERIOR_FOR_FORESTER_IS_INVALID", "validation.superiorForForesterIsInvalid");
        assetsMap.put("MSG_SUPERIOR_FOR_TASKMASTER_IS_INVALID", "validation.superiorForTaskmasterIsInvalid");

        assetsMap.put("MSG_RECIEVER_LOGIN_IS_INVALID", "validation.recieverLoginIsInvalid");
        assetsMap.put("MSG_USER_IS_NOT_SUBORDINATE", "validation.userIsNotSubordinate");
        assetsMap.put("MSG_CREATE_TASK_SUCCESS", "task.createSuccess");
        assetsMap.put("MSG_CREATE_USER_SUCCESS", "user.createSuccess");
        assetsMap.put("MSG_CREATE_REPORT_SUCCESS", "report.createSuccess");
    }

    private List<String> publicViewNames = Arrays.asList(
            assetsMap.get("LOGIN_VIEW_NAME")
    );

    public boolean isViewPublic(String viewName) {
        return publicViewNames.contains(viewName);
    }

    @Override
    public String get(Object key) {
        return assetsMap.get(key);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        throw  new UnsupportedOperationException();
    }
}