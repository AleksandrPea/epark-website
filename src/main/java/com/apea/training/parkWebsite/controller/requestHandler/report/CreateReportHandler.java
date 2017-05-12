package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CreateReportHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        boolean isReportCreated = tryToCreateReport(request);
        if (isReportCreated) {
            generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_CREATE_REPORT_SUCCESS")));
        } else {
            generalMessages.add(FrontMessageFactory.getInstance().getError(assets.get("MSG_CREATE_REPORT_ERROR")));
        }
        String taskId = request.getParameter(assets.get("TASK_ID_PARAM_NAME"));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_TASK_URI") + "?"+assets.get("ID_PARAM_NAME")+"="+taskId;
    }

    private boolean tryToCreateReport(HttpServletRequest request) {
        if (areParametersInvalid(request)) {return false;}
        AppAssets assets = AppAssets.getInstance();
        String taskId = request.getParameter(assets.get("TASK_ID_PARAM_NAME"));
        String comment = request.getParameter(assets.get("REPORT_COMMENT_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("REPORT_IMG_PATH_PARAM_NAME"));
        Report report = new Report.Builder().setComment(comment).setImgPath(imgPath)
                .setTaskId(Integer.valueOf(taskId)).build();
        ServiceFactoryImpl.getInstance().getReportService().create(report);
        return true;
    }

    private boolean areParametersInvalid(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Set<FrontendMessage> validationMessages = new HashSet<>();

        ControllerUtils.validateText(request.getParameter(assets.get("REPORT_COMMENT_PARAM_NAME")))
                .ifPresent(validationMessages::add);

        ControllerUtils.validateText(request.getParameter(assets.get("REPORT_IMG_PATH_PARAM_NAME")))
                .ifPresent(validationMessages::add);

        return !validationMessages.isEmpty();
    }
}