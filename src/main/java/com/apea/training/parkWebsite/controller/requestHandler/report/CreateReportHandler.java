package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.apea.training.parkWebsite.controller.AppAssets.*;

public class CreateReportHandler implements RequestHandler {

    private ReportService reportService = ServiceFactory.getInstance().getReportService();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        createReport(request);
        generalMessages.add(messageFactory.getSuccess(MSG_CREATE_REPORT_SUCCESS));
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        Integer taskId = (Integer) request.getAttribute(TASK_ID_ATTR_NAME);
        return REDIRECT + DISPLAY_TASK_URI + "/"+taskId;
    }

    private void createReport(HttpServletRequest request) {
        Integer taskId = (Integer) request.getAttribute(TASK_ID_ATTR_NAME);
        String comment = request.getParameter(REPORT_COMMENT_PARAM_NAME);
        String imgPath = request.getParameter(REPORT_IMG_PATH_PARAM_NAME);
        Report report = new Report.Builder().setComment(comment).setImgPath(imgPath)
                .setTaskId(taskId).build();
        reportService.create(task);
    }
}