<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.frontend.general" var="frontGeneral"/>

<div class="row">
    <h1><fmt:message key="home.topMessage" bundle="${frontGeneral}"/></h1>
    <c:choose>
        <c:when test="${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] != null}">
            <p><fmt:message key="auth.text" bundle="${frontGeneral}"/>
                <a href="<c:url value="${assets.DISPLAY_USER_URI}?${assets.ID_PARAM_NAME}=${sessionScope[assets.CURRENT_USER_ID_ATTR_NAME]}"/>">
                    <fmt:message key="linkAccount.text" bundle="${frontGeneral}"/>
                </a>
            </p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="unauth.text" bundle="${frontGeneral}"/>.</p>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>