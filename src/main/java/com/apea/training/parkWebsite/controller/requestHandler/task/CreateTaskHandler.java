package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.TaskService;
import com.apea.training.parkWebsite.service.UserService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

public class CreateTaskHandler implements RequestHandler {

    private AppAssets assets = AppAssets.getInstance();
    private UserService userService = ServiceFactoryImpl.getInstance().getUserService();
    private TaskService taskService = ServiceFactoryImpl.getInstance().getTaskService();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isNewTaskCreated = tryToCreateTask(request, formMessages);
        if (!isNewTaskCreated) {
            setSessionAttributes(request, formMessages);
        } else {
            removeSessionAttributes(request);
            generalMessages.add(messageFactory.getSuccess(assets.get("MSG_CREATE_TASK_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        }
        return REDIRECT + assets.get("CREATE_TASK_URI");
    }

    private boolean tryToCreateTask(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        String recieverLogin = request.getParameter(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"));
        User reciever = userService.getByLogin(recieverLogin);
        if (checkRecieverIsNotExists(reciever,formMessages)) { return false; }

        User currentUser = ControllerUtils.getCurrentUser(request);
        if (isSubordinationInvalid(reciever, currentUser, formMessages)) { return false; }
        String title = request.getParameter(assets.get("TASK_TITLE_PARAM_NAME"));
        String comment = request.getParameter(assets.get("TASK_COMMENT_PARAM_NAME"));
        Task.State state = Task.State.NEW;
        Integer senderId = currentUser.getId();
        Integer recieverId = reciever.getId();
        Task task = new Task.Builder().setState(state).setTitle(title).setComment(comment)
                .setSenderId(senderId).setRecieverId(recieverId).build();
        List<Plant> plants = fetchTaskPlants(request);
        taskService.createNewAndAssociate(task, plants);

        return true;
    }

    private boolean checkRecieverIsNotExists(User reciever, Map<String, FrontendMessage> formMessages) {
        if (reciever == null) {
            formMessages.put(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_RECIEVER_LOGIN_IS_INVALID")));
            return true;
        }
        return false;
    }

    private boolean isSubordinationInvalid(User reciever, User currentUser, Map<String, FrontendMessage> formMessages) {
        if (!currentUser.getId().equals(reciever.getSuperiorId())) {
            formMessages.put(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"),
                    messageFactory.getError(assets.get("MSG_USER_IS_NOT_SUBORDINATE")));
            return true;
        }
        return false;
    }

    private List<Plant> fetchTaskPlants(HttpServletRequest request) {
        String[] plantNames = request.getParameterValues(assets.get("TASK_PLANTS_PARAM_NAME"));
        List<String> plantNamesList = Arrays.asList(plantNames);
        return ((List<Plant>) request.getSession().getAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME")))
                .stream()
                .filter(plant -> plantNamesList.contains(plant.getName()))
                .collect(Collectors.toList());
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        HttpSession session = request.getSession();
        String recieverLogin = request.getParameter(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"));
        String[] plantNames = request.getParameterValues(assets.get("TASK_PLANTS_PARAM_NAME"));
        String title = request.getParameter(assets.get("TASK_TITLE_PARAM_NAME"));
        String comment = request.getParameter(assets.get("TASK_COMMENT_PARAM_NAME"));
        session.setAttribute(assets.get("TASK_RECIEVER_LOGIN_ATTR_NAME"), recieverLogin);
        session.setAttribute(assets.get("TASK_PLANTS_ATTR_NAME"), Arrays.asList(plantNames));
        session.setAttribute(assets.get("TASK_TITLE_ATTR_NAME"), title);
        session.setAttribute(assets.get("TASK_COMMENT_PARAM_NAME"), comment);

        session.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }

    private void removeSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(assets.get("TASK_RECIEVER_LOGIN_ATTR_NAME"));
        session.removeAttribute(assets.get("TASK_PLANTS_ATTR_NAME"));
        session.removeAttribute(assets.get("TASK_TITLE_ATTR_NAME"));
        session.removeAttribute(assets.get("TASK_COMMENT_ATTR_NAME"));
    }
}