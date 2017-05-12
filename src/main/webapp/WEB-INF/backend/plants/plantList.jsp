<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.plant" var="langPlant"/>

<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">
            <fmt:message key="plant.plantListPage.title" bundle="${langPlant}"/>
            <small>${requestScope[assets.AREA_ATTR_NAME].name}</small>
        </h1>
    </div>
</div>

<c:forEach var="plantRow" items="${requestScope[assets.PLANTS_ATTR_NAME]}">
    <div class="row">
        <c:forEach var="plant" items="${plantRow}">
        <div class="col-md-4 plant-item">
            <img class="img-responsive plant-img" src="${fn:escapeXml(plant.imgPath)}" alt=""/>
            <c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'
                    || sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == requestScope[assets.AREA_ATTR_NAME].taskmasterId}">
                <a href="<c:url value="${assets.EDIT_PLANT_URI}?${assets.ID_PARAM_NAME}=${plant.id}"/>">
                    <span class="glyphicon glyphicon-pencil"></span>
                </a>
                <a href="<c:url value="${assets.DELETE_PLANT_URI}?${assets.ID_PARAM_NAME}=${plant.id}"/>" class="confirm" role="button">
                    <span class="glyphicon glyphicon-remove-circle"></span>
                </a>
            </c:if>
            <h3>
                <c:out value="${plant.name}"/>
                <small><em>
                    <fmt:message key="plant.state.${plant.state}" bundle="${langPlant}"/>
                </em></small>
            </h3>
            <p><c:out value="${plant.description}"/></p>
        </div>
        </c:forEach>
    </div>
</c:forEach>

<c:if test="${sessionScope[assets.CURRENT_USER_ROLE_ATTR_NAME] == 'OWNER'
        || sessionScope[assets.CURRENT_USER_ID_ATTR_NAME] == requestScope[assets.AREA_ATTR_NAME].taskmasterId}">
    <a href="<c:url value="${assets.CREATE_PLANT_URI}?${assets.AREA_ID_PARAM_NAME}=${requestScope[assets.AREA_ATTR_NAME].id}"/>"
            class="btn btn-primary" role="button">
        <fmt:message key="plant.plantList.createButton" bundle="${langPlant}"/>
    </a>
</c:if>

<hr>

<div class="row text-center">
    <div class="col-lg-12">
        <ul class="pagination">
            <li>
                <c:choose>
                    <c:when test="${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME] == 1}">
                        <a href="#">&laquo;</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}?${assets.AREA_ID_PARAM_NAME}=${requestScope[assets.AREA_ATTR_NAME].id}&${
                                assets.PAGE_PARAM_NAME}=${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME]-1}"/>">&laquo;</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <c:forEach var = "page" begin = "1" end = "${requestScope[assets.MAX_PLANT_PAGES_ATTR_NAME]}">
                <li class="${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME] == page ? 'active' :''}">
                    <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}?${assets.AREA_ID_PARAM_NAME}=${requestScope[assets.AREA_ATTR_NAME].id}&${
                            assets.PAGE_PARAM_NAME}=${page}"/>">${page}</a>
                </li>
            </c:forEach>
            <li>
                <c:choose>
                    <c:when test="${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME] >= requestScope[assets.MAX_PLANT_PAGES_ATTR_NAME]}">
                        <a href="#">&raquo;</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}?${assets.AREA_ID_PARAM_NAME}=${requestScope[assets.AREA_ATTR_NAME].id}&${
                                assets.PAGE_PARAM_NAME}=${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME]+1}"/>">&raquo;</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
</div>

<hr>
<%@include file="/WEB-INF/includes/footer.jsp" %>