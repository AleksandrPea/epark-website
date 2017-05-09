<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.task" var="langTask"/>

<div class="row">
    <div class="col-md-12">
    <h2 class="text-center"><fmt:message key="task.taskPage.title" bundle="${langTask}"/></h2>
        <h3>
            ${requestScope[assets.TASK_ATTR_NAME].title}
            <a href="<c:url value="${assets.DELETE_TASK_URI}/${requestScope[assets.TASK_ATTR_NAME].id}"/>"
                    class="confirmDelete" role="button">
                <small><span class="glyphicon glyphicon-remove"></span></small>
            </a>
        </h3>
        <h5>
            <fmt:message key="task.createdLabel" bundle="${langTask}"/>:
                <fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value = "${requestScope[assets.TASK_ATTR_NAME].creationDate}"/>
        </h5>
        <p><em>
            <fmt:message key="task.from.label" bundle="${langTask}"/>
            <a href="<c:url value="${assets.DISPLAY_USER_URI}/${requestScope[assets.TASK_RECEIVER_ATTR_NAME].userId}"/>">
                ${requestScope[assets.TASK_RECEIVER_ATTR_NAME].login}
            </a>

            <fmt:message key="task.for.label" bundle="${langTask}"/>
            <a href="<c:url value="${assets.DISPLAY_USER_URI}/${requestScope[assets.TASK_SENDER_ATTR_NAME].userId}"/>">
                ${requestScope[assets.TASK_SENDER_ATTR_NAME].login}
            </a>
        </em></p>
        <p>${requestScope[assets.TASK_ATTR_NAME].comment}</p>
        <p>
            <strong><fmt:message key="task.taskPage.plants" bundle="${langTask}"/>: </strong>
            <c:forEach var="plant" items="${requestScope[assets.TASK_PLANTS_ATTR_NAME]}">
                <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}/${plant.areaId}/1"/>">
                    ${plant.name}</a>,
            </c:forEach>
        <p>
        <ul class="reports list-unstyled">
            <c:forEach var="report" items="${requestScope[assets.TASK_REPORTS_ATTR_NAME]}">
                <li>
                    <h3>
                        <fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value = "${report.creationDate}"/>
                        <a href="<c:url value="${assets.DELETE_REPORT_URI}/${report.id}"/>" class="confirmDelete" role="button">
                            <small><span class="glyphicon glyphicon-remove"></span></small>
                        </a>
                    </h3>
                    <img class="img-responsive center-block" src="${report.imgPath}" alt=""/>
                    <p class="report-text">${report.comment}</p>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == requestScope[assets.TASK_ATTR_NAME].receiverId}">
            <a href="#report-form" data-toggle="collapse" class="btn btn-primary" role="button">
                <fmt:message key="task.taskPage.addReportButton" bundle="${langTask}"/>
            </a>
            <div id="report-form" class="col-md-12 collapse">
                <%@include file="/WEB-INF/backend/report/createReportForm.jsp" %>
            </div>
        </c:if>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>