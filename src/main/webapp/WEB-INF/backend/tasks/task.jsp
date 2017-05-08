<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.task" var="langTask"/>

<div class="row">
    <div class="col-md-12">
    <h2><fmt:message key="task.taskPage.title" bundle="${langTask}"/></h2>
        ${requestScope[assets.TASK_ATTR_NAME].title}<br/>
        ${requestScope[assets.TASK_ATTR_NAME].comment}
        <ul>
            <c:forEach var="report" items="${requestScope[assets.TASK_REPORTS_ATTR_NAME]}">
                <li>
                    <fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value = "${report.creationDate}"/>
                    ${report.comment}

                </li>
            </c:forEach>
        </ul>
        <c:if test="${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == requestScope[assets.TASK_ATTR_NAME].receiverId}">
            <a href="#report-form" data-toggle="collapse" class="btn btn-primary" role="button">
                <fmt:message key="task.taskPage.addReportButton" bundle="${langTask}"/>
            </a>
            <div id="report-form" class="col-md-12 collapse">
                <form method="POST" name="createReportForm" id="createReportForm"
                      action="<c:url value="${assets.CREATE_REPORT_URI}/${requestScope[assets.TASK_ATTR_NAME].id}"/>"
                      accept-charset="UTF-8" role="form">

                    <div class="form-group">
                        <label class="control-label" for="comment">
                            <fmt:message key="task.report.comment.label" bundle="${langTask}"/></label>
                        <textarea class="form-control" id="comment" rows="6"
                               placeholder="<fmt:message key="task.report.comment.label" bundle="${langTask}"/>"
                               name="${assets.REPORT_COMMENT_PARAM_NAME}"></textarea>
                    </div>

                    <div class="form-group">
                        <label class="control-label" for="imgPath">
                            <fmt:message key="task.report.imgPath.label" bundle="${langTask}"/></label>
                        <input type="text" class="form-control" id="imgPath"
                               placeholder="<fmt:message key="task.report.imgPath.label" bundle="${langTask}"/>"
                               name="${assets.REPORT_IMG_PATH_PARAM_NAME}"/>
                    </div>


                    <button type="submit"
                            class="btn btn-success btn-block">
                        <fmt:message key="task.taskPage.submitAddReportButton" bundle="${langTask}"/></button>
                </form>
            </div>
        </c:if>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>