package com.apea.training.parkWebsite.controller.requestHandler.task;

import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apea.training.parkWebsite.controller.AppAssets.*;

public class CreateTaskHandler implements RequestHandler {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
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
            generalMessages.add(messageFactory.getSuccess(MSG_CREATE_TASK_SUCCESS));
            ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        }
        return REDIRECT + CREATE_TASK_URI;
    }

    // PLANTS!
    private boolean tryToCreateTask(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        String recieverLogin = request.getParameter(TASK_RECIEVER_LOGIN_PARAM_NAME);
        User reciever = serviceFactory.getUserService().getByLogin(recieverLogin);
        if (checkRecieverIsNotExists(reciever,formMessages)) { return false; }

        User currentUser = ControllerUtils.getCurrentUser(request);
        if (isSubordinationInvalid(reciever, currentUser, formMessages)) { return false; }

        String comment = request.getParameter(TASK_COMMENT_PARAM_NAME);
        Task.State state = Task.State.NEW;
        Integer senderId = currentUser.getId();
        Integer recieverId = reciever.getId();
        Task task = new Task.Builder().setState(state).setComment(comment).setSenderId(senderId)
                .setRecieverId(recieverId).build();
        serviceFactory.getTaskService().create(task);
        return true;
    }

    private boolean checkRecieverIsNotExists(User reciever, Map<String, FrontendMessage> formMessages) {
        if (reciever == null) {
            formMessages.put(TASK_RECIEVER_LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_RECIEVER_LOGIN_IS_INVALID));
            return true;
        }
        return false;
    }

    private boolean isSubordinationInvalid(User reciever, User currentUser, Map<String, FrontendMessage> formMessages) {
        if (!currentUser.getId().equals(reciever.getSuperiorId())) {
            formMessages.put(TASK_RECIEVER_LOGIN_PARAM_NAME,
                    messageFactory.getError(MSG_USER_IS_NOT_SUBORDINATE));
            return true;
        }
        return false;
    }

    private void setSessionAttributes(HttpServletRequest request, Map<String, FrontendMessage> formMessages) {
        HttpSession session = request.getSession();
        String recieverLogin = request.getParameter(TASK_RECIEVER_LOGIN_PARAM_NAME);
        String comment = request.getParameter(TASK_COMMENT_PARAM_NAME);
        session.setAttribute(TASK_RECIEVER_LOGIN_ATTR_NAME, recieverLogin);
        session.setAttribute(TASK_COMMENT_PARAM_NAME, comment);

        session.setAttribute(MESSAGES_ATTR_NAME, formMessages);
    }

    private void removeSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(TASK_RECIEVER_LOGIN_ATTR_NAME);
        session.removeAttribute(TASK_COMMENT_ATTR_NAME);
    }
}