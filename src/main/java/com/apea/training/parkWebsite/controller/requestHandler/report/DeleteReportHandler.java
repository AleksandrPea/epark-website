package com.apea.training.parkWebsite.controller.requestHandler.report;

import com.apea.training.parkWebsite.controller.AppAssets;
import com.apea.training.parkWebsite.controller.message.FrontMessageFactory;
import com.apea.training.parkWebsite.controller.message.FrontendMessage;
import com.apea.training.parkWebsite.controller.requestHandler.RequestHandler;
import com.apea.training.parkWebsite.controller.utils.ControllerUtils;
import com.apea.training.parkWebsite.domain.Report;
import com.apea.training.parkWebsite.service.ReportService;
import com.apea.training.parkWebsite.service.impl.ServiceFactoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteReportHandler implements RequestHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        AppAssets assets = AppAssets.getInstance();
        List<FrontendMessage> generalMessages = new ArrayList<>();
        Integer taskId = deleteReport(request, generalMessages);
        ControllerUtils.saveGeneralMsgsInSession(request, generalMessages);
        return REDIRECT + assets.get("DISPLAY_TASK_URI")+"/"+taskId;
    }

    /** @return task id */
    private Integer deleteReport(HttpServletRequest request, List<FrontendMessage> generalMessages) {
        AppAssets assets = AppAssets.getInstance();
        Integer id = ControllerUtils.getFirstIdFromUri(request.getRequestURI());
        ReportService reportService = ServiceFactoryImpl.getInstance().getReportService();
        Report report = reportService.getById(id);
        reportService.delete(report);
        generalMessages.add(FrontMessageFactory.getInstance().getSuccess(assets.get("MSG_DELETE_REPORT_SUCCESS")));
        return report.getTaskId();
    }
}
