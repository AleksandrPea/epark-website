<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.user" var="langUser"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center">
            <fmt:message key="${requestScope[assets.IS_CREATING_USER_ATTR_NAME] ?
                'user.createPage.title' : 'user.editPage.title'}" bundle="${langUser}"/>
        </h3>
        <form method="POST" name="createUserForm" id="createUserForm"
              action="<c:url value="${requestScope[assets.IS_CREATING_USER_ATTR_NAME] ?
                    assets.CREATE_USER_URI : assets.EDIT_USER_URI}"/>"
              accept-charset="UTF-8" role="form">

            <div class="form-group validated required">
                <label class="control-label" for="login">
                    <fmt:message key="user.login.label" bundle="${langUser}"/></label>
                <c:choose>
                    <c:when test="${requestScope[assets.IS_CREATING_USER_ATTR_NAME]}">
                        <input type="text" class="form-control" id="login" required
                               placeholder="<fmt:message key="user.login.label" bundle="${langUser}"/>"
                               name="${assets.LOGIN_PARAM_NAME}"
                               value="${fn:escapeXml(requestScope[assets.LOGIN_ATTR_NAME])}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="login"
                               readonly
                               name="${assets.LOGIN_PARAM_NAME}"
                               value="${fn:escapeXml(requestScope[assets.LOGIN_ATTR_NAME])}"/>
                    </c:otherwise>
                </c:choose>
                <mytags:formMessages formInputName="${assets.LOGIN_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="userPassword">
                    <fmt:message key="user.password.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="userPassword" required
                       placeholder="<fmt:message key="user.password.label" bundle="${langUser}"/>"
                       name="${assets.PASSWORD_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.PASSWORD_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.PASSWORD_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="firstName">
                    <fmt:message key="user.firstName.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="firstName" required
                       placeholder="<fmt:message key="user.firstName.label" bundle="${langUser}"/>"
                       name="${assets.FIRSTNAME_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.FIRSTNAME_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.FIRSTNAME_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="lastName">
                    <fmt:message key="user.lastName.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="lastName" required
                       placeholder="<fmt:message key="user.lastName.label" bundle="${langUser}"/>"
                       name="${assets.LASTNAME_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.LASTNAME_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.LASTNAME_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="userEmail">
                    <fmt:message key="user.email.label" bundle="${langUser}"/></label>
                <input type="text" class="form-control" id="userEmail" required
                       placeholder="<fmt:message key="user.email.label" bundle="${langUser}"/>"
                       name="${assets.EMAIL_PARAM_NAME}"
                       value="${fn:escapeXml(requestScope[assets.EMAIL_ATTR_NAME])}"/>
                <mytags:formMessages formInputName="${assets.EMAIL_PARAM_NAME}"/>
            </div>

            <div class="form-group required">
                <label class="control-label" for="userRole">
                    <fmt:message key="user.role.label" bundle="${langUser}"/></label>
                <c:choose>
                    <c:when test="${requestScope[assets.IS_CREATING_USER_ATTR_NAME]
                            && sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'}">
                        <select id="userRole" class="selectpicker form-control" name="${assets.ROLE_PARAM_NAME}">
                            <option value="FORESTER" ${(requestScope[assets.ROLE_ATTR_NAME] == 'FORESTER') ? 'selected' : ''}>
                                <fmt:message key="user.role.FORESTER" bundle="${langUser}"/>
                            </option>
                            <option value="TASKMASTER" ${(requestScope[assets.ROLE_ATTR_NAME] == 'TASKMASTER') ? 'selected' : ''}>
                                <fmt:message key="user.role.TASKMASTER" bundle="${langUser}"/>
                            </option>
                        </select>
                    </c:when>
                    <c:when test="${requestScope[assets.IS_CREATING_USER_ATTR_NAME]
                             && sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'TASKMASTER'}">
                        <input type="text" class="form-control" id="userRole"
                               readonly
                               name="${assets.ROLE_PARAM_NAME}"
                               value="FORESTER"/>
                    </c:when>
                    <c:otherwise>
                         <input type="text" class="form-control" id="userRole"
                               readonly
                               name="${assets.ROLE_PARAM_NAME}"
                               value="${requestScope[assets.ROLE_ATTR_NAME]}"/>
                    </c:otherwise>
                </c:choose>

            </div>

            <div class="form-group validated">
                <label class="control-label" for="info">
                    <fmt:message key="user.info.label" bundle="${langUser}"/></label>
                <textarea class="form-control" id="info"
                       placeholder="<fmt:message key="user.info.label" bundle="${langUser}"/>"
                       name="${assets.USER_INFO_PARAM_NAME}"><c:out value="${requestScope[assets.USER_INFO_ATTR_NAME]}"/></textarea>
                <mytags:formMessages formInputName="${assets.USER_INFO_PARAM_NAME}"/>
            </div>

            <div class="form-group validated required">
                <label class="control-label" for="superiorLogin">
                    <fmt:message key="user.superiorLogin.label" bundle="${langUser}"/></label>
                <c:choose>
                    <c:when test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'}">
                        <input type="text" class="form-control" id="superiorLogin" required
                               placeholder="<fmt:message key="user.superiorLogin.label" bundle="${langUser}"/>"
                               name="${assets.SUPERIOR_LOGIN_PARAM_NAME}"
                               value="${fn:escapeXml(requestScope[assets.SUPERIOR_LOGIN_ATTR_NAME])}"/>
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="superiorLogin"
                                readonly
                                name="${assets.SUPERIOR_LOGIN_PARAM_NAME}"
                                value="${fn:escapeXml(requestScope[assets.SUPERIOR_LOGIN_ATTR_NAME])}"/>
                    </c:otherwise>
                </c:choose>
                <mytags:formMessages formInputName="${assets.SUPERIOR_LOGIN_PARAM_NAME}"/>
             </div>

            <c:choose>
                <c:when test="${requestScope[assets.IS_CREATING_USER_ATTR_NAME]}">
                    <button type="submit"
                            class="btn btn-lg btn-primary btn-block">
                        <fmt:message key="user.createPage.submitCreate" bundle="${langUser}"/></button>
                </c:when>
                <c:otherwise>
                    <button type="submit"
                        class="btn btn-lg btn-default btn-block">
                    <fmt:message key="user.createPage.submitEdit" bundle="${langUser}"/></button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>