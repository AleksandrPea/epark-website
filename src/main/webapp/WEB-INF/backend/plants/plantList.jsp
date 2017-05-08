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
            <a href="#">
                <img class="img-responsive" src="${plant.imgPath}" alt="">
            </a>
            <h3>${plant.name}</h3>
            <p>${plant.description}</p>
        </div>
        </c:forEach>
    </div>
</c:forEach>

<c:if test="${requestScope[assets.CURRENT_USER_ATTR_NAME].role == 'OWNER'}">
    <a href="<c:url value="${assets.CREATE_PLANT_URI}/${requestScope[assets.AREA_ATTR_NAME].id}"/>"
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
                    <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}/
                            ${requestScope[assets.AREA_ATTR_NAME].id}/
                            ${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME]-1}"/>">&laquo;</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <c:forEach var = "page" begin = "1" end = "${requestScope[assets.MAX_PLANT_PAGES_ATTR_NAME]}">
                <li class="${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME] == page ? 'active' :''}">
                    <a href="<c:url value="${plant.imgPath}"/>">${page}</a>
                </li>
            </c:forEach>
            <li>
                <c:choose>
                    <c:when test="${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME] >= requestScope[assets.MAX_PLANT_PAGES_ATTR_NAME]}">
                        <a href="#">&raquo;</a>
                    </c:when>
                    <c:otherwise>
                    <a href="<c:url value="${assets.DISPLAY_PLANTS_URI}/
                            ${requestScope[assets.AREA_ATTR_NAME].id}/
                            ${requestScope[assets.CURRENT_PLANT_PAGE_ATTR_NAME]+1}"/>">&raquo;</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
</div>

<hr>
<%@include file="/WEB-INF/includes/footer.jsp" %>