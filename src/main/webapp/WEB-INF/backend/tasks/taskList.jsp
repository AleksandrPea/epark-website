<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.task" var="langTask"/>

<div class="row">
    <div class="col-md-12">
    <h2><fmt:message key="task.taskListPage.title" bundle="${langTask}"/></h2>
        <div class="panel-group">
            <div class="panel panel-default">
                <a href="#received" data-toggle="collapse" class="list-group-item">
                    <h3><fmt:message key="task.taskListPage.received.label" bundle="${langTask}"/></h3></a>
                <div id="received" class="panel-collapse collapse">
                    <div class="panel-body">
                        <c:forEach var="task" items="${requestScope[assets.CURRENT_USER_RECEIVED_TASKS_ATTR_NAME]}">
                            <a href="<c:url value="${assets.DISPLAY_TASK_URI}/${task.id}"/>" class="list-group-item">
                                <h5 class="list-group-item-heading">
                                    ${task.title}
                                    <small><fmt:message key="task.createdLabel" bundle="${langTask}"/>:
                                            <fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value = "${task.creationDate}"/>
                                    </small>
                                    <span class="label label-info pull-right">
                                        <fmt:message key="task.state.${task.state}" bundle="${langTask}"/>
                                    </span>
                                </h5>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <a href="#sended" data-toggle="collapse" class="list-group-item">
                     <h3><fmt:message key="task.taskListPage.sended.label" bundle="${langTask}"/></h3></a>
                <div id="sended" class="panel-collapse collapse">
                    <div class="panel-body">
                        <c:forEach var="taskEntry" items="${requestScope[assets.CURRENT_USER_SENDED_TASKS_ATTR_NAME]}">
                            <a href="<c:url value="${assets.DISPLAY_TASK_URI}/${taskEntry.key.id}"/>" class="list-group-item">
                                <h5 class="list-group-item-heading">
                                    ${taskEntry.key.title}
                                    <small><fmt:message key="task.createdLabel" bundle="${langTask}"/>:
                                            <fmt:formatDate pattern = "yyyy-MM-dd HH:mm" value = "${taskEntry.key.creationDate}"/>
                                    </small>
                                    <span class="label label-info pull-right">
                                        <fmt:message key="task.state.${taskEntry.key.state}" bundle="${langTask}"/>
                                    </span>
                                </h5>
                                <fmt:message key="task.for.label" bundle="${langTask}"/> ${taskEntry.value}
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>