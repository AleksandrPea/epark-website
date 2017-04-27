<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.user" var="langUser"/>

<div class="row">
    <div class="col-md-12 table-responsive">
        <h1><fmt:message key="user.usersList.title" bundle="${langUser}"/></h1>
        <table class="table table-hover table-bordered table-striped">
            <thead>
            <tr>
                <th><fmt:message key="user.login.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.firstName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.lastName.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.role.label" bundle="${langUser}"/></th>
                <th><fmt:message key="user.email.label" bundle="${langUser}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${requestScope[assets.ALL_USERS_ATTR_NAME]}">
                <tr>
                    <td><a href="<c:url value="${assets.DISPLAY_USER_URI}/${user.id}"/>">
                        ${credential.login}</a></td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.role}</td>
                    <td>${user.email}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a href="<c:url value="${assets.CREATE_USER_URI}"/>" class="btn btn-primary" role="button">
            <fmt:message key="user.userList.createButton" bundle="${langUser}"/>
        </a>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>