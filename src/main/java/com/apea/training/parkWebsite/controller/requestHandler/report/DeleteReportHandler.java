package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.exception.AccessDeniedException;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.domain.Task;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.ReportService;
import com.apea.training.parkWebsite.service.ServiceFactory;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteReportHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();

        if (ControllerUtils.getCurrentUserId(request) == null) {return REDIRECT + assets.get("LOGIN_PAGE");}
        Report report = getReport(request);
        if (!checkIfReportBelongsToCurrentUser(request, report)) {throw new AccessDeniedException("Report doesn't belong to current user.");}
        if (request.getParameter(assets.get("ID_PARAM_NAME")) == null) {return REDIRECT + assets.get("HOME_PAGE");}

        List<FrontendMessage> generalMessages = new ArrayList<>();
        Integer taskId = deleteReport(report, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_TASK_URI") + "?"+assets.get("ID_PARAM_NAME")+"="+taskId;
    }

    private Report getReport(HttpServletRequest request) {
        String reportId = request.getParameter(AppAssets.getInstance().get("ID_PARAM_NAME"));
        return ServiceFactoryImpl.getInstance().getReportService().getById(Integer.valueOf(reportId));
    }

    private boolean checkIfReportBelongsToCurrentUser(HttpServletRequest request, Report report) {
        User currentUser = ControllerUtils.getCurrentUser(request);
        Task task = ServiceFactoryImpl.getInstance().getTaskService().getById(report.getTaskId());
        if (task.getReceiverId().equals(currentUser.getId())) {
            return true;
        } else {
            return false;
        }
    }

    /** @return task id */
    private Integer deleteReport(Report report, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        ServiceFactoryImpl.getInstance().getReportService().delete(report);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_REPORT_SUCCESS")));
        return report.getTaskId();
    }
}