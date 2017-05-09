<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.general" var="general"/>
<fmt:setBundle basename="webProject.i18n.backend.user" var="langUser"/>

<div class="col-md-12">
    <h2><fmt:message key="user.page.title" bundle="${langUser}"/></h2>
    <p><fmt:message key="user.page.description" bundle="${langUser}"/></p>
    <div class="col-xs-12 col-md-10 col-md-offset-1">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.login.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.CREDENTIAL_ATTR_NAME].login}</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.firstName.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.USER_ATTR_NAME].firstName}</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.lastName.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.USER_ATTR_NAME].lastName}</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.email.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.USER_ATTR_NAME].email}</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.role.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.USER_ATTR_NAME].role}</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">
                    <fmt:message key="user.info.label" bundle="${langUser}"/></label>
                <div class="col-xs-8">
                    <p class="form-control-static">${requestScope[assets.USER_ATTR_NAME].info}</p>
                </div>
            </div>
            <c:if test="${requestScope[assets.SUPERIOR_LOGIN_ATTR_NAME] != null}">
                <div class="form-group">
                    <label class="col-xs-4 control-label">
                        <fmt:message key="user.superior.label" bundle="${langUser}"/></label>
                    <div class="col-xs-8">
                        <p class="form-control-static">
                             <a href="<c:url value="${assets.DISPLAY_USER_URI}/${user.superiorId}"/>">
                                ${requestScope[assets.SUPERIOR_LOGIN_ATTR_NAME]}
                            </a>
                        </p>
                    </div>
                </div>
            </c:if>
        </form>
        <div class="col-md-offset-4">
            <c:if test="${requestScope[assets.CURRENT_USER_ATTR_NAME].role == 'OWNER'
                    || sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == requestScope[assets.USER_ATTR_NAME].id}">

                <a href="<c:url value="${assets.EDIT_USER_URI}/${requestScope[assets.USER_ATTR_NAME].id}"/>"
                        class="btn btn-default" role="button">
                    <fmt:message key="user.page.editButton" bundle="${langUser}"/>
                </a>
            </c:if>
            <c:if test="${requestScope[assets.CURRENT_USER_ATTR_NAME].role == 'OWNER'
                    && sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] != requestScope[assets.USER_ATTR_NAME].id}">
                <a href="<c:url value="${assets.DELETE_USER_URI}/${requestScope[assets.USER_ATTR_NAME].id}"/>"
                        class="btn btn-danger confirmDelete" role="button">
                    <fmt:message key="user.page.deleteButton" bundle="${langUser}"/>
                </a>
            </c:if>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>