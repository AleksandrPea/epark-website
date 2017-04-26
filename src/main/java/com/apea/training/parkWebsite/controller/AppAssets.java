package com.apea.training.parkWebsite.controller;

public class AppAssets {
    //************************* ATTRIBUTES **********************************
    public static final String MESSAGES_ATTR_NAME = "messages";
    public static final String GENERAL_MESSAGES_BLOCK_NAME = "generalMessages";
    public static final String CURRENT_USER_ATTR_NAME = "currentUser";
    public static final String USER_ATTR_NAME = "user";
    public static final String ALL_USERS_ATTR_NAME = "allUsers";
    public static final String ALL_AREAS_ATTR_NAME = "allAreas";
    public static final String CURRENT_USER_TASKS_ATTR_NAME = "currentUserTasks";

    public static final String SIGN_IN_LOGIN_ATTR_NAME = "signInLogin";
    public static final String LOGIN_ATTR_NAME = "login";
    public static final String PASSWORD_ATTR_NAME = "password";
    public static final String FIRSTNAME_ATTR_NAME = "firstName";
    public static final String LASTNAME_ATTR_NAME = "lastName";
    public static final String EMAIL_ATTR_NAME = "email";
    public static final String ROLE_ATTR_NAME = "role";
    public static final String USER_INFO_ATTR_NAME = "info";
    public static final String SUPERIOR_LOGIN_ATTR_NAME = "superiorLogin";

    public static final String TASK_RECIEVER_LOGIN_ATTR_NAME = "recieverLogin";
    public static final String TASK_COMMENT_ATTR_NAME = "taskComment";
    public static final String TASK_ID_ATTR_NAME = "taskId";
    public static final String TASK_ATTR_NAME = "task";
    public static final String TASK_REPORTS_ATTR_NAME = "taskReports";

    //************************* PARAMS **********************************
    public static final String SIGN_IN_LOGIN_PARAM_NAME = "signInLogin";
    public static final String LOGIN_PARAM_NAME = "login";
    public static final String PASSWORD_PARAM_NAME = "password";
    public static final String FIRSTNAME_PARAM_NAME = "firstName";
    public static final String LASTNAME_PARAM_NAME = "lastName";
    public static final String EMAIL_PARAM_NAME = "email";
    public static final String ROLE_PARAM_NAME = "role";
    public static final String USER_INFO_PARAM_NAME = "info";
    public static final String SUPERIOR_LOGIN_PARAM_NAME = "superiorLogin";

    public static final String TASK_RECIEVER_LOGIN_PARAM_NAME = "recieverLogin";
    public static final String TASK_COMMENT_PARAM_NAME = "taskComment";
    public static final String REPORT_COMMENT_PARAM_NAME = "reportComment";
    public static final String REPORT_IMG_PATH_PARAM_NAME = "reportImg";

    //************************* VIEWS and URIS **********************************
    public static final String SIGN_IN_URI = "/backend/signIn";
    public static final String DISPLAY_CURRENT_USER_URI = "/backend/users/currentUser";
    public static final String DISPLAY_USER_URI = "/backend/users/user";
    public static final String CREATE_USER_URI = "/backend/users/newUser";
    public static final String USER_LIST_URI = "/backend/users";
    public static final String CREATE_TASK_URI = "/backend/tasks/newTask";
    public static final String DISPLAY_USER_TASKS_URI = "/backend/tasks";
    public static final String DISPLAY_TASK_URI = "/backend/tasks/task";
    public static final String CREATE_REPORT_URI = "/backend/reports/newReport";
    public static final String AREA_LIST_URI = "/backend/areas";
    public static final String SIGN_OUT_URI = "/backend/signOut";

    public static final String DISPLAY_USER_VIEW_NAME = "users/userAccount";
    public static final String USER_LIST_VIEW_NAME = "users/userList";
    public static final String CREATE_USER_VIEW_NAME = "users/createUser";
    public static final String CURRENT_USER_TASKS_LIST_VIEW_NAME = "tasks/taskList";
    public static final String CREATE_TASK_VIEW_NAME = "tasks/createTask";
    public static final String CREATE_REPORT_VIEW_NAME = "tasks/createReport";
    public static final String DISPLAY_TASK_VIEW_NAME = "tasks/task";
    public static final String AREA_LIST_VIEW_NAME = "areas/areaList";

    public static final String GENERAL_ERROR_PAGE_VIEW_NAME = "/error-page";
    public static final String LOGIN_PAGE = "/login.jsp";

    //************************* MESSAGES **********************************
    public static final String MSG_CREDENTIALS_ARE_NOT_CORRECT = "validation.credentialsAreNotCorrect";
    public static final String MSG_USERNAME_IS_NOT_UNIQUE = "validation.usernameIsNotUnique";
    public static final String MSG_SUPERIOR_LOGIN_IS_INVALID = "validation.superiorLoginIsInvalid";
    public static final String MSG_SUPERIOR_FOR_FORESTER_IS_INVALID = "validation.superiorForForesterIsInvalid";
    public static final String MSG_SUPERIOR_FOR_TASKMASTER_IS_INVALID = "validation.superiorForTaskmasterIsInvalid";

    public static final String MSG_RECIEVER_LOGIN_IS_INVALID = "validation.recieverLoginIsInvalid";
    public static final String MSG_USER_IS_NOT_SUBORDINATE = "validation.userIsNotSubordinate";
    public static final String MSG_CREATE_TASK_SUCCESS = "task.createSuccess";
    public static final String MSG_CREATE_USER_SUCCESS = "user.createSuccess";
    public static final String MSG_CREATE_REPORT_SUCCESS = "report.createSuccess";
}