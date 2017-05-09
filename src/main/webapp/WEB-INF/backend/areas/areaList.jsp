<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.area" var="langArea"/>

<div class="row">
    <div class="col-md-12">
    <h2><fmt:message key="area.areaListPage.title" bundle="${langArea}"/></h2>
        <div class="panel panel-default">
            <ul class="list-group">
                <c:forEach var="area" items="${requestScope[assets.ALL_AREAS_ATTR_NAME]}">
                    <a href="#area${area.id}" data-toggle="collapse" class="list-group-item">
                        ${area.name}
                    </a>
                    <div id="area${area.id}" class="collapse">
                        <div class="list-group-item clearfix">
                            <p>${area.description}</p>
                            <c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'
                                    || sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == area.taskmasterId}">

                                <a href="<c:url value="${assets.EDIT_AREA_URI}/${area.id}"/>"
                                        class="btn btn-default" role="button">
                                    <fmt:message key="area.areaListPage.editButton" bundle="${langArea}"/>
                                </a>
                            </c:if>
                            <c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'}">
                                <a href="<c:url value="${assets.DELETE_AREA_URI}/${area.id}"/>"
                                        class="btn btn-danger confirm" role="button">
                                    <fmt:message key="area.areaListPage.deleteButton" bundle="${langArea}"/>
                                </a>
                            </c:if>
                            <span class="pull-right">
                                <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}/${area.id}/1"/>" class="btn btn-success" role="button">
                                    <fmt:message key="area.areaListPage.diplayPlantsButton" bundle="${langArea}"/>
                                </a>
                            </span>
                        </div>
                    </div>
                </c:forEach>
            </ul>
        </div>
        <c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'}">
            <a href="<c:url value="${assets.CREATE_AREA_URI}"/>" class="btn btn-primary" role="button">
                <fmt:message key="area.areaList.createButton" bundle="${langArea}"/>
            </a>
        </c:if>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>