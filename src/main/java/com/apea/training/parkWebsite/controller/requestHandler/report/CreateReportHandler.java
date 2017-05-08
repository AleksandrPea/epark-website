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
import java.util.ArrayList;
import java.util.List;

public class CreateReportHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        createReport(request);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_CREATE_REPORT_SUCCESS")));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        return REDIRECT + assets.get("DISPLAY_TASK_URI") + "/"+taskId;
    }

    private void createReport(HttpServletRequest request) {
        AppAssets assets = AppAssets.getInstance();
        Integer taskId = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        String comment = request.getParameter(assets.get("REPORT_COMMENT_PARAM_NAME"));
        String imgPath = request.getParameter(assets.get("REPORT_IMG_PATH_PARAM_NAME"));
        Report report = new Report.Builder().setComment(comment).setImgPath(imgPath)
                .setTaskId(taskId).build();
        ServiceFactoryImpl.getInstance().getReportService().create(report);
    }
}