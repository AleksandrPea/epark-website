<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/"><fmt:message key="header.siteName" bundle="${langGeneral}"/></a>
        </div>
        <c:choose>
            <c:when test="${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] != null}">
                <ul class="nav navbar-nav">
                    <li><a href="<c:url value="${assets.AREA_LIST_URI}"/>">
                            <span class="glyphicon glyphicon-tree-deciduous"></span>
                            <fmt:message key="menu.allAreas.label" bundle="${langGeneral}"/></a></li>
                    <li><a href="<c:url value="${assets.USER_LIST_URI}"/>">
                            <fmt:message key="menu.allUsers.label" bundle="${langGeneral}"/></a></li>
                    <li><a href="<c:url value="${assets.DISPLAY_USER_TASKS_URI}"/>">
                            <span class="glyphicon glyphicon-tasks"></span>
                            <fmt:message key="menu.userTasks.label" bundle="${langGeneral}"/></a></li>
                    <c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] != 'FORESTER'}">
                        <li><a href="<c:url value="${assets.CREATE_TASK_URI}"/>">
                                <fmt:message key="menu.createTask.label" bundle="${langGeneral}"/></a></li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span class="glyphicon glyphicon-flag"></span>
                            <fmt:message key="language.label" bundle="${langGeneral}"/>
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a class="language-picker" href="en_EN"><fmt:message key="language.english" bundle="${langGeneral}"/></a></li>
                            <li><a class="language-picker" href="ru_RU"><fmt:message key="language.russian" bundle="${langGeneral}"/></a></li>
                        </ul>
                    </li>
                    <li><a href="<c:url value="${assets.DISPLAY_USER_URI}/${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME]}"/>">
                        <span class="glyphicon glyphicon-user">
                        </span> <fmt:message key="myAccount.label" bundle="${langGeneral}"/>
                    </a></li>
                    <li><a href="<c:url value="${assets.SIGN_OUT_URI}"/>">
                        <span class="glyphicon glyphicon-log-out">
                        </span> <fmt:message key="signOut.label" bundle="${langGeneral}"/>
                    </a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <c:if test="${pageContext.request.requestURI != assets.LOGIN_PAGE}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="<c:url value="${assets.LOGIN_PAGE}"/>">
                            <span class="glyphicon glyphicon-log-in">
                            </span> <fmt:message key="signIn.label" bundle="${langGeneral}"/>
                        </a></li>
                    </ul>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
</nav>