<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.user" var="langUser"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center"><fmt:message key="user.createPage.title" bundle="${langUser}"/></h3>
        <form method="POST" name="createUserForm" id="createUserForm"
              action="<c:url value="${assets.CREATE_USER_URI}"/>"
              accept-charset="UTF-8" role="form">

            <div class="form-group validated required">
                <label class="control-label" for="login">
                    <fmt:message key="user.login.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="login"
                       placeholder="<fmt:message key="user.login.label" bundle="${langUser}"/>"
                       name="${assets.LOGIN_PARAM_NAME}"
                       value="${requestScope[assets.LOGIN_ATTR_NAME]}"/>
                <c:if test="${(not empty messages) && (not empty messages[assets.LOGIN_PARAM_NAME])}">
                    <label class="messages ${messages[assets.LOGIN_PARAM_NAME].type == 'ERROR' ? 'error' : ''}">
                        <fmt:message key="${messages[assets.LOGIN_PARAM_NAME].messageKey}" bundle="${validation}"/>
                    </label>
                </c:if>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="userPassword">
                    <fmt:message key="user.password.label" bundle="${langUser}"/></label>
                <input type="password" class="form-control" id="userPassword"
                       placeholder="<fmt:message key="user.password.label" bundle="${langUser}"/>"
                       name="${assets.PASSWORD_PARAM_NAME}"/>

            </div>

            <div class="form-group required">
                <label class="control-label" for="firstName">
                    <fmt:message key="user.firstName.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="firstName"
                       placeholder="<fmt:message key="user.firstName.label" bundle="${langUser}"/>"
                       name="${assets.FIRSTNAME_PARAM_NAME}"
                       value="${requestScope[assets.FIRSTNAME_ATTR_NAME]}"/>
            </div>

            <div class="form-group required">
                <label class="control-label" for="lastName">
                    <fmt:message key="user.lastName.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="lastName"
                       placeholder="<fmt:message key="user.lastName.label" bundle="${langUser}"/>"
                       name="${assets.LASTNAME_PARAM_NAME}"
                       value="${requestScope[assets.LASTNAME_ATTR_NAME]}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="userEmail">
                    <fmt:message key="user.email.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="userEmail"
                       placeholder="<fmt:message key="user.email.label" bundle="${langUser}"/>"
                       name="${assets.EMAIL_PARAM_NAME}"
                       value="${requestScope[assets.EMAIL_ATTR_NAME]}"/>
                <c:if test="${(not empty messages) && (not empty messages['userEmail'])}">
                    <label class="messages ${messages['userEmail'].type == 'ERROR' ? 'error' : ''}">
                        <fmt:message key="${messages['userEmail'].messageKey}" bundle="${validation}"/>
                    </label>
                </c:if>
            </div>

            <div class="form-group required">
                <label class="control-label" for="userRole">
                    <fmt:message key="user.role.label" bundle="${langUser}"/></label>
                <select id="userRole" class="selectpicker form-control" name="${assets.ROLE_PARAM_NAME}">
                    <option value="FORESTER" ${(requestScope[assets.ROLE_ATTR_NAME] == 'FORESTER') ? 'selected' : ''}>
                        <fmt:message key="user.role.forester" bundle="${langUser}"/>
                    </option>
                    <option value="TASKMASTER" ${(requestScope[assets.ROLE_ATTR_NAME] == 'TASKMASTER') ? 'selected' : ''}>
                        <fmt:message key="user.role.taskmaster" bundle="${langUser}"/>
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label class="control-label" for="info">
                    <fmt:message key="user.info.label" bundle="${langUser}"/></label>
                <textarea class="form-control" id="info"
                       placeholder="<fmt:message key="user.info.label" bundle="${langUser}"/>"
                       name="${assets.USER_INFO_PARAM_NAME}">${requestScope[assets.USER_INFO_ATTR_NAME]}</textarea>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="superiorLogin">
                    <fmt:message key="user.superiorLogin.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="superiorLogin"
                       placeholder="<fmt:message key="user.superiorLogin.label" bundle="${langUser}"/>"
                       name="${assets.SUPERIOR_LOGIN_PARAM_NAME}"
                       value="${requestScope[assets.SUPERIOR_LOGIN_ATTR_NAME]}"/>
                <c:if test="${(not empty messages) && (not empty messages[assets.SUPERIOR_LOGIN_PARAM_NAME])}">
                    <label class="messages ${messages[assets.SUPERIOR_LOGIN_PARAM_NAME].type == 'ERROR' ? 'error' : ''}">
                        <fmt:message key="${messages[assets.SUPERIOR_LOGIN_PARAM_NAME].messageKey}" bundle="${validation}"/>
                    </label>
                </c:if>
            </div>

            <button type="submit"
                    class="btn btn-lg btn-primary btn-block">
                <fmt:message key="user.createPage.submit" bundle="${langUser}"/></button>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>