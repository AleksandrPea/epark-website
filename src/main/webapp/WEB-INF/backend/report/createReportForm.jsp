<fmt:setBundle basename="webProject.i18n.backend.report" var="langReport"/>

<form method="POST" name="createReportForm" id="createReportForm"
      action="<c:url value="${assets.CREATE_REPORT_URI}"/>"
      accept-charset="UTF-8" role="form">

    <div class="form-group">
        <label class="control-label" for="comment">
            <fmt:message key="report.comment.label" bundle="${langReport}"/></label>
        <textarea class="form-control" id="comment" rows="6"
               placeholder="<fmt:message key="report.comment.label" bundle="${langReport}"/>"
               name="${assets.REPORT_COMMENT_PARAM_NAME}"></textarea>
    </div>

    <div class="form-group">
        <label class="control-label" for="imgPath">
            <fmt:message key="report.imgPath.label" bundle="${langReport}"/></label>
        <input type="text" class="form-control" id="imgPath"
               placeholder="<fmt:message key="report.imgPath.label" bundle="${langReport}"/>"
               name="${assets.REPORT_IMG_PATH_PARAM_NAME}"/>
    </div>

        <input type="hidden"
                name="${assets.TASK_ID_PARAM_NAME}"
                value="${requestScope[assets.TASK_ATTR_NAME].id}"/>

    <button type="submit"
            class="btn btn-success btn-block">
        <fmt:message key="report.createPage.submit" bundle="${langReport}"/></button>
</form>