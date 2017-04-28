<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.task" var="langTask"/>

<div class="row">
    <div class="col-md-12">
    <h2><fmt:message key="task.taskListPage.title" bundle="${langTask}"/></h2>
        <div class="panel panel-default">
            <ul class="list-group">
                <c:forEach var="task" items="${requestScope[assets.CURRENT_USER_TASKS_ATTR_NAME]}">
                    <a href="#task${task.id}" data-toggle="collapse" class="list-group-item">
                        ${task.title}<span class="badge">${task.state}</span>
                    </a>
                    <div id="task${task.id}" class="collapse">
                        <p>${task.comment}</p>
                    </div>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>