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
        assetsMap.put("CHARACTER_ENCODING", "UTF-8");

        //************************* ATTRIBUTES *********************************
        assetsMap.put("LANGUAGE_ATTR_NAME", "language");

        assetsMap.put("MESSAGES_ATTR_NAME", "messages");
        assetsMap.put("GENERAL_MESSAGES_BLOCK_NAME", "generalMessages");
        assetsMap.put("CURRENT_USER_ID_ATTR_NAME", "currentUserId");
        assetsMap.put("CURRENT_USER_ATTR_NAME", "currentUser");
        assetsMap.put("CURRENT_USER_ROLE_ATTR_NAME", "currentUserRole");
        assetsMap.put("CURRENT_USER_LOGIN_ATTR_NAME", "currentUserLogin");
        assetsMap.put("USER_ATTR_NAME", "user");
        assetsMap.put("CREDENTIAL_ATTR_NAME", "credentials");
        assetsMap.put("ALL_USERS_ATTR_NAME", "allUsers");
        assetsMap.put("ALL_CREDENTIALS_ATTR_NAME", "allCredentials");
        assetsMap.put("ALL_AREAS_ATTR_NAME", "allAreas");

        assetsMap.put("IS_CREATING_USER_ATTR_NAME", "isCreatingUser");
        assetsMap.put("IS_CREATING_AREA_ATTR_NAME", "isCreatingArea");
        assetsMap.put("IS_CREATING_PLANT_ATTR_NAME", "isCreatingPlant");

        assetsMap.put("LOGIN_ATTR_NAME", "login");
        assetsMap.put("PASSWORD_ATTR_NAME", "password");
        assetsMap.put("FIRSTNAME_ATTR_NAME", "firstName");
        assetsMap.put("LASTNAME_ATTR_NAME", "lastName");
        assetsMap.put("EMAIL_ATTR_NAME", "email");
        assetsMap.put("ROLE_ATTR_NAME", "role");
        assetsMap.put("USER_INFO_ATTR_NAME", "info");
        assetsMap.put("SUPERIOR_LOGIN_ATTR_NAME", "superiorLogin");
        assetsMap.put("SUBORDINATES_ATTR_NAME", "subordinates");

        assetsMap.put("AREA_ID_ATTR_NAME", "areaId");
        assetsMap.put("TASKMASTER_LOGIN_ATTR_NAME", "taskmasterLogin");
        assetsMap.put("AREA_NAME_ATTR_NAME", "areaName");
        assetsMap.put("AREA_DESCRIPTION_ATTR_NAME", "areaDescription");

        assetsMap.put("TASK_RECEIVER_LOGIN_ATTR_NAME", "receiverLogin");
        assetsMap.put("TASK_TITLE_ATTR_NAME", "taskTitle");
        assetsMap.put("TASK_COMMENT_ATTR_NAME", "taskComment");
        assetsMap.put("TASK_PLANTS_ATTR_NAME", "taskPlants");
        assetsMap.put("ALL_TASK_PLANTS_ATTR_NAME", "allTaskPlants");
        assetsMap.put("TASK_ATTR_NAME", "task");
        assetsMap.put("TASK_REPORTS_ATTR_NAME", "taskReports");
        assetsMap.put("TASK_RECEIVER_ATTR_NAME", "taskReceiver");
        assetsMap.put("TASK_SENDER_ATTR_NAME", "taskSender");

        assetsMap.put("CURRENT_USER_SENDED_TASKS_ATTR_NAME", "currentUserSendedTasks");
        assetsMap.put("CURRENT_USER_RECEIVED_TASKS_ATTR_NAME", "currentUserReceivedTasks");
        assetsMap.put("NEW_RECEIVED_COUNT_ATTR_NAME", "newReceivedCount");

        assetsMap.put("REPORT_COMMENT_ATTR_NAME", "reportComment");
        assetsMap.put("REPORT_IMG_PATH_ATTR_NAME", "reportImg");
        assetsMap.put("TASK_ID_ATTR_NAME", "taskId");

        assetsMap.put("AREA_ATTR_NAME", "area");
        assetsMap.put("PLANTS_ATTR_NAME", "plants");
        assetsMap.put("CURRENT_PLANT_PAGE_ATTR_NAME", "currentPlantPage");
        assetsMap.put("MAX_PLANT_PAGES_ATTR_NAME", "maxPlantPages");
        assetsMap.put("AREA_TASKMASTERS_ATTR_NAME", "areaTaskmasters");

        assetsMap.put("PLANT_ID_ATTR_NAME", "plantId");
        assetsMap.put("PLANT_NAME_ATTR_NAME", "plantName");
        assetsMap.put("PLANT_DESCRIPTION_ATTR_NAME", "plantDescription");
        assetsMap.put("PLANT_IMG_PATH_ATTR_NAME", "plantImgPath");
        assetsMap.put("PLANT_STATE_ATTR_NAME", "plantState");

        //************************* PARAMS **********************************
        assetsMap.put("ID_PARAM_NAME", "id");
        assetsMap.put("PAGE_PARAM_NAME", "page");

        assetsMap.put("LOGIN_PARAM_NAME", "login");
        assetsMap.put("PASSWORD_PARAM_NAME", "password");
        assetsMap.put("FIRSTNAME_PARAM_NAME", "firstName");
        assetsMap.put("LASTNAME_PARAM_NAME", "lastName");
        assetsMap.put("EMAIL_PARAM_NAME", "email");
        assetsMap.put("ROLE_PARAM_NAME", "role");
        assetsMap.put("USER_INFO_PARAM_NAME", "info");
        assetsMap.put("SUPERIOR_LOGIN_PARAM_NAME", "superiorLogin");

        assetsMap.put("TASK_RECEIVER_LOGIN_PARAM_NAME", "receiverLogin");
        assetsMap.put("TASK_TITLE_PARAM_NAME", "taskTitle");
        assetsMap.put("TASK_COMMENT_PARAM_NAME", "taskComment");
        assetsMap.put("TASK_PLANTS_PARAM_NAME", "taskPlants");

        assetsMap.put("REPORT_COMMENT_PARAM_NAME", "reportComment");
        assetsMap.put("REPORT_IMG_PATH_PARAM_NAME", "reportImg");
        assetsMap.put("TASK_ID_PARAM_NAME", "taskId");

        assetsMap.put("AREA_ID_PARAM_NAME", "areaId");
        assetsMap.put("AREA_NAME_PARAM_NAME", "areaName");
        assetsMap.put("AREA_DESCRIPTION_PARAM_NAME", "areaDescription");
        assetsMap.put("TASKMASTER_LOGIN_PARAM_NAME", "taskmasterLogin");

        assetsMap.put("PLANT_ID_PARAM_NAME", "plantId");
        assetsMap.put("PLANT_NAME_PARAM_NAME", "plantName");
        assetsMap.put("PLANT_DESCRIPTION_PARAM_NAME", "plantDescription");
        assetsMap.put("PLANT_IMG_PATH_PARAM_NAME", "plantImgPath");
        assetsMap.put("PLANT_STATE_PARAM_NAME", "plantState");

        //************************* REGEX for validation ***************************
        assetsMap.put("NAME_REGEX", "[а-яА-ЯіІїЇєЄёЁ\\w\\s!&?$#@'\"-]{1,30}");
        assetsMap.put("TEXT_REGEX", "[^<>]*");
        assetsMap.put("PASSWORD_REGEX", "[\\w]{4,12}");
        assetsMap.put("EMAIL_REGEX", "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");

        //************************* VIEWS and URIS **********************************
        assetsMap.put("SIGN_IN_URI", "/backend/signIn");
        assetsMap.put("DISPLAY_USER_URI", "/backend/users/user");
        assetsMap.put("CREATE_USER_URI", "/backend/users/newUser");
        assetsMap.put("EDIT_USER_URI", "/backend/users/editUser");
        assetsMap.put("DELETE_USER_URI", "/backend/users/deleteUser");
        assetsMap.put("USER_LIST_URI", "/backend/users");

        assetsMap.put("CREATE_TASK_URI", "/backend/tasks/newTask");
        assetsMap.put("RECEIVE_TASK_URI", "/backend/tasks/receive");
        assetsMap.put("FINISH_TASK_URI", "/backend/tasks/finish");
        assetsMap.put("ABORT_TASK_URI", "/backend/tasks/abort");
        assetsMap.put("DELETE_TASK_URI", "/backend/tasks/deleteTask");
        assetsMap.put("DISPLAY_USER_TASKS_URI", "/backend/tasks");
        assetsMap.put("DISPLAY_TASK_URI", "/backend/tasks/task");
        assetsMap.put("CREATE_REPORT_URI", "/backend/reports/newReport");
        assetsMap.put("DELETE_REPORT_URI", "/backend/reports/deleteReport");

        assetsMap.put("AREA_LIST_URI", "/backend/areas");
        assetsMap.put("CREATE_AREA_URI", "/backend/areas/newArea");
        assetsMap.put("EDIT_AREA_URI", "/backend/areas/editArea");
        assetsMap.put("DELETE_AREA_URI", "/backend/areas/deleteArea");

        assetsMap.put("DISPLAY_PLANTS_URI", "/backend/plants");
        assetsMap.put("CREATE_PLANT_URI", "/backend/plants/newPlant");
        assetsMap.put("EDIT_PLANT_URI", "/backend/plants/editPlant");
        assetsMap.put("DELETE_PLANT_URI", "/backend/plants/deletePlant");
        assetsMap.put("SIGN_OUT_URI", "/backend/signOut");

        assetsMap.put("DISPLAY_USER_VIEW_NAME", "users/user");
        assetsMap.put("USER_LIST_VIEW_NAME", "users/userList");
        assetsMap.put("CREATE_USER_VIEW_NAME", "users/createUser");
        assetsMap.put("CURRENT_USER_TASKS_LIST_VIEW_NAME", "tasks/taskList");
        assetsMap.put("CREATE_TASK_VIEW_NAME", "tasks/createTask");
        assetsMap.put("DISPLAY_TASK_VIEW_NAME", "tasks/task");
        assetsMap.put("AREA_LIST_VIEW_NAME", "areas/areaList");
        assetsMap.put("CREATE_AREA_VIEW_NAME", "areas/createArea");
        assetsMap.put("PLANT_LIST_VIEW_NAME", "plants/plantList");
        assetsMap.put("CREATE_PLANT_VIEW_NAME", "plants/createPlant");

        assetsMap.put("LOGIN_VIEW_NAME", "login");
        assetsMap.put("HOME_VIEW_NAME", "home");
        assetsMap.put("GENERAL_ERROR_VIEW_NAME", "errors/error-page");
        assetsMap.put("404_ERROR_VIEW_NAME", "errors/page-404");
        assetsMap.put("STORAGE_ERROR_VIEW_NAME", "errors/storage-error-page");
        assetsMap.put("ACCESS_DENIED_ERROR_VIEW_NAME", "errors/accessDenied");

        assetsMap.put("LOGIN_PAGE", "/login.jsp");
        assetsMap.put("HOME_PAGE", "/home.jsp");

        //************************* MESSAGES **********************************
        assetsMap.put("MSG_CREDENTIALS_ARE_NOT_CORRECT", "validation.credentialsAreNotCorrect");
        assetsMap.put("MSG_USERNAME_IS_NOT_UNIQUE", "validation.usernameIsNotUnique");
        assetsMap.put("MSG_SUPERIOR_LOGIN_IS_INVALID", "validation.superiorLoginIsInvalid");
        assetsMap.put("MSG_SUPERIOR_FOR_FORESTER_IS_INVALID", "validation.superiorForForesterIsInvalid");
        assetsMap.put("MSG_SUPERIOR_FOR_TASKMASTER_IS_INVALID", "validation.superiorForTaskmasterIsInvalid");
        assetsMap.put("MSG_TASKMASTER_LOGIN_IS_INVALID", "validation.taskmasterLoginIsInvalid");
        assetsMap.put("MSG_IS_NOT_TASKMASTER", "validation.isNotTaskmaster");
        assetsMap.put("MSG_OWNER_SUPERIOR_IS_NOT_EMPTY", "validation.ownerSuperiorIsNotEmpty");

        assetsMap.put("NAME_VALIDATION_FAILED", "validation.regex.name");
        assetsMap.put("TEXT_VALIDATION_FAILED", "validation.regex.text");
        assetsMap.put("PASSWORD_VALIDATION_FAILED", "validation.regex.password");
        assetsMap.put("EMAIL_VALIDATION_FAILED", "validation.regex.email");

        assetsMap.put("MSG_RECEIVER_LOGIN_IS_INVALID", "validation.receiverLoginIsInvalid");
        assetsMap.put("MSG_USER_IS_NOT_SUBORDINATE", "validation.userIsNotSubordinate");
        assetsMap.put("MSG_CREATE_TASK_SUCCESS", "task.createSuccess");
        assetsMap.put("MSG_CREATE_USER_SUCCESS", "user.createSuccess");
        assetsMap.put("MSG_CREATE_REPORT_SUCCESS", "report.createSuccess");
        assetsMap.put("MSG_CREATE_REPORT_ERROR", "report.createError");
        assetsMap.put("MSG_CREATE_AREA_SUCCESS", "area.createSuccess");
        assetsMap.put("MSG_CREATE_PLANT_SUCCESS", "plant.createSuccess");

        assetsMap.put("MSG_EDIT_USER_SUCCESS", "user.editSuccess");
        assetsMap.put("MSG_DELETE_USER_SUCCESS", "user.deleteSuccess");
        assetsMap.put("MSG_DELETE_TASKMASTER_SUBS_ERROR", "user.deleteError.taskmaster.subs");
        assetsMap.put("MSG_DELETE_TASKMASTER_AREAS_ERROR", "user.deleteError.taskmaster.areas");
        assetsMap.put("MSG_EDIT_AREA_SUCCESS", "area.editSuccess");
        assetsMap.put("MSG_DELETE_AREA_SUCCESS", "area.deleteSuccess");
        assetsMap.put("MSG_DELETE_AREA_ERROR", "area.deleteError");
        assetsMap.put("MSG_EDIT_PLANT_SUCCESS", "plant.editSuccess");
        assetsMap.put("MSG_DELETE_PLANT_SUCCESS", "plant.deleteSuccess");
        assetsMap.put("MSG_DELETE_REPORT_SUCCESS", "report.deleteSuccess");
        assetsMap.put("MSG_DELETE_TASK_SUCCESS", "task.deleteSuccess");
    }

    private List<String> publicViewNames = Arrays.asList(
            assetsMap.get("LOGIN_VIEW_NAME"),
            assetsMap.get("HOME_VIEW_NAME"),
            assetsMap.get("GENERAL_ERROR_VIEW_NAME"),
            assetsMap.get("404_ERROR_VIEW_NAME"),
            assetsMap.get("STORAGE_ERROR_VIEW_NAME"),
            assetsMap.get("ACCESS_DENIED_ERROR_VIEW_NAME")
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