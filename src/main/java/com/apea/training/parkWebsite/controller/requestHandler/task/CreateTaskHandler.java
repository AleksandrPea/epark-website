package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Plant;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

public class CreateTaskHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        Map<String, FrontendMessage> formMessages = new HashMap<>();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        String abstractViewName;
        boolean isNewTaskCreated = tryToCreateTask(request, formMessages);
        if (!isNewTaskCreated) {
            setRequestAttributes(request, formMessages);
            abstractViewName = FORWARD + assets.get("CREATE_TASK_VIEW_NAME");
        } else {
            generalMessages.add(FrontMessageFactory.getInstance()
                    .getSuccess(assets.get("MSG_CREATE_TASK_SUCCESS")));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
            abstractViewName = REDIRECT + assets.get("CREATE_TASK_URI");
        }
        return abstractViewName;
    }

    private boolean tryToCreateTask(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String recieverLogin = request.getParameter(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"));
        User reciever = ServiceFactoryImpl.getInstance().getUserService().getByLogin(recieverLogin);
        if (checkRecieverIsNotExists(reciever,formMessages)) { return false; }

        User currentUser = ControllerUtils.getCurrentUser(request);
        if (isSubordinationInvalid(reciever, currentUser, formMessages)) { return false; }
        String title = request.getParameter(assets.get("TASK_TITLE_PARAM_NAME"));
        String comment = request.getParameter(assets.get("TASK_COMMENT_PARAM_NAME"));
        Integer senderId = currentUser.getId();
        Integer recieverId = reciever.getId();
        Task task = new Task.Builder().setTitle(title).setComment(comment)
                .setSenderId(senderId).setReceiverId(recieverId).build();
        List<Plant> plants = fetchTaskPlants(request);
        ServiceFactoryImpl.getInstance().getTaskService().createNewAndAssociate(task, plants);

        return true;
    }

    private boolean checkRecieverIsNotExists(User reciever, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (reciever == null) {
            formMessages.put(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_RECIEVER_LOGIN_IS_INVALID")));
            return true;
        }
        return false;
    }

    private boolean isSubordinationInvalid(User reciever, User currentUser, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        if (!currentUser.getId().equals(reciever.getSuperiorId())) {
            formMessages.put(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"),
                    FrontMessageFactory.getInstance().getError(assets.get("MSG_USER_IS_NOT_SUBORDINATE")));
            return true;
        }
        return false;
    }

    private List<Plant> fetchTaskPlants(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        String[] plantNames = request.getParameterValues(assets.get("TASK_PLANTS_PARAM_NAME"));
        if (plantNames == null) {
            return Collections.emptyList();
        }
        List<String> plantNamesList = Arrays.asList(plantNames);
        return ((List<Plant>) request.getAttribute(assets.get("ALL_TASK_PLANTS_ATTR_NAME")))
                .stream()
                .filter(plant -> plantNamesList.contains(plant.getName()))
                .collect(Collectors.toList());
    }

    private void setRequestAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        AppAssets assets = AppAssets.getInstance();
        String recieverLogin = request.getParameter(assets.get("TASK_RECIEVER_LOGIN_PARAM_NAME"));
        String[] plantNames = request.getParameterValues(assets.get("TASK_PLANTS_PARAM_NAME"));
        String title = request.getParameter(assets.get("TASK_TITLE_PARAM_NAME"));
        String comment = request.getParameter(assets.get("TASK_COMMENT_PARAM_NAME"));
        request.setAttribute(assets.get("TASK_RECIEVER_LOGIN_ATTR_NAME"), recieverLogin);
        request.setAttribute(assets.get("TASK_TITLE_ATTR_NAME"), title);
        request.setAttribute(assets.get("TASK_COMMENT_PARAM_NAME"), comment);
        if (plantNames != null) {
            request.setAttribute(assets.get("TASK_PLANTS_ATTR_NAME"), Arrays.asList(plantNames));
        }

        request.setAttribute(assets.get("MESSAGES_ATTR_NAME"), formMessages);
    }
}