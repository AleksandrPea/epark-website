<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.area" var="langArea"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center">
            <fmt:message key="${requestScope[assets.IS_CREATING_AREA_ATTR_NAME] ?
                    'area.createPage.title' : 'area.editPage.title'}" bundle="${langArea}"/>
        </h3>
        <form method="POST" name="createAreaForm"
              action="<c:url value="${requestScope[assets.IS_CREATING_AREA_ATTR_NAME] ?
                    assets.CREATE_AREA_URI : assets.EDIT_AREA_URI}"/>"
              accept-charset="UTF-8" role="form">
            <c:if test="${!requestScope[assets.IS_CREATING_AREA_ATTR_NAME]}">
                <input type="hidden"
                    name="${assets.AREA_ID_PARAM_NAME}"
                    value="${requestScope[assets.AREA_ID_ATTR_NAME]}"/>
            </c:if>
            <div class="form-group required">
                <label class="control-label" for="name">
                    <fmt:message key="area.name.label" bundle="${langArea}"/></label>
                <input type="text" class="form-control" id="name"
                       placeholder="<fmt:message key="area.name.label" bundle="${langArea}"/>"
                       name="${assets.AREA_NAME_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.AREA_NAME_ATTR_NAME])}"/>
            </div>

            <div class="form-group">
                <label class="control-label" for="description">
                    <fmt:message key="area.description.label" bundle="${langArea}"/></label>
                <textarea class="form-control" id="description" rows="8"
                       placeholder="<fmt:message key="area.description.label" bundle="${langArea}"/>"
                       name="${assets.AREA_DESCRIPTION_PARAM_NAME}"><c:out value="${requestScope[assets.AREA_DESCRIPTION_ATTR_NAME]}"/></textarea>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="taskmasterLogin">
                    <fmt:message key="area.taskmasterLogin.label" bundle="${langArea}"/></label>
                <input type="text" class="form-control" id="taskmasterLogin"
                       placeholder="<fmt:message key="area.taskmasterLogin.label" bundle="${langArea}"/>"
                       name="${assets.TASKMASTER_LOGIN_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.TASKMASTER_LOGIN_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.TASKMASTER_LOGIN_PARAM_NAME}"/>
            </div>

            <c:choose>
                <c:when test="${requestScope[assets.IS_CREATING_AREA_ATTR_NAME]}">
                    <button type="submit"
                            class="btn btn-lg btn-success btn-block">
                        <fmt:message key="area.createPage.submitCreate" bundle="${langArea}"/></button>
                </c:when>
                <c:otherwise>
                    <button type="submit"
                        class="btn btn-lg btn-default btn-block">
                    <fmt:message key="area.createPage.submitEdit" bundle="${langArea}"/></button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>