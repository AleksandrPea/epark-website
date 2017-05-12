<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.task" var="langTask"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center">
            <fmt:message key="task.createPage.title" bundle="${langTask}"/>
        </h3>
        <form method="POST" name="createTaskForm" id="createTaskForm"
              action="<c:url value="${assets.CREATE_TASK_URI}"/>"
              accept-charset="UTF-8" role="form">

            <div class="form-group validated required">
                <label class="control-label" for="receiverLogin">
                    <fmt:message key="task.receiverLogin.label" bundle="${langTask}"/></label>
                <input type="text" class="form-control" id="receiverLogin" required
                       placeholder="<fmt:message key="task.receiverLogin.label" bundle="${langTask}"/>"
                       name="${assets.TASK_RECEIVER_LOGIN_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.TASK_RECEIVER_LOGIN_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.TASK_RECEIVER_LOGIN_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="title">
                    <fmt:message key="task.title.label" bundle="${langTask}"/></label>
                <input type="text" class="form-control" id="title" required
                       placeholder="<fmt:message key="task.title.label" bundle="${langTask}"/>"
                       name="${assets.TASK_TITLE_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.TASK_TITLE_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.TASK_TITLE_PARAM_NAME}"/>
            </div>

            <div class="form-group validated">
                <label class="control-label" for="comment">
                    <fmt:message key="task.comment.label" bundle="${langTask}"/></label>
                <textarea class="form-control" id="comment" rows="12"
                       placeholder="<fmt:message key="task.comment.label" bundle="${langTask}"/>"
                       name="${assets.TASK_COMMENT_PARAM_NAME}"><c:out value="${requestScope[assets.TASK_COMMENT_ATTR_NAME]}"/></textarea>
                <mytags:formMessages formInputName="${assets.TASK_COMMENT_PARAM_NAME}"/>
            </div>
            <div class="form-group">
                <label class="control-label" for="plants">
                    <fmt:message key="task.plants.label" bundle="${langTask}"/></label>
                <select id="plants" class="selectpicker form-control" multiple data-selected-text-format="count > 3"
                        name="${assets.TASK_PLANTS_PARAM_NAME}">
                    <c:forEach var="plant" items="${requestScope[assets.ALL_TASK_PLANTS_ATTR_NAME]}">
                        <option value="${plant.name}" ${(requestScope[assets.TASK_PLANTS_ATTR_NAME].contains(plant.name)) ? 'selected' : ''}>
                            <c:out value="${plant.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit"
                    class="btn btn-lg btn-success btn-block">
                <fmt:message key="task.createPage.submit" bundle="${langTask}"/></button>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>