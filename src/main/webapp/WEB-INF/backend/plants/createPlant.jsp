<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.plant" var="langPlant"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center">
            <fmt:message key="${requestScope[assets.IS_CREATING_PLANT_ATTR_NAME] ?
                    'plant.createPage.title' : 'plant.editPage.title'}" bundle="${langPlant}"/>
        </h3>
        <form method="POST" name="createPlantForm" id="createPlantForm"
              action="<c:url value="${requestScope[assets.IS_CREATING_PLANT_ATTR_NAME] ?
                    assets.CREATE_PLANT_URI : assets.EDIT_PLANT_URI}"/>"
              accept-charset="UTF-8" role="form">

            <c:if test="${!requestScope[assets.IS_CREATING_AREA_ATTR_NAME]}">
                <input type="hidden"
                    name="${assets.PLANT_ID_PARAM_NAME}"
                    value="${requestScope[assets.PLANT_ID_ATTR_NAME]}"/>
            </c:if>

            <div class="form-group required">
                <label class="control-label" for="name">
                    <fmt:message key="plant.name.label" bundle="${langPlant}"/></label>
                <input type="text" class="form-control" id="name"
                       placeholder="<fmt:message key="plant.name.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_NAME_PARAM_NAME}"
                       value="${requestScope[assets.PLANT_NAME_ATTR_NAME]}"/>
            </div>

            <div class="form-group">
                <label class="control-label" for="description">
                    <fmt:message key="plant.description.label" bundle="${langPlant}"/></label>
                <textarea class="form-control" id="description" rows="8"
                       placeholder="<fmt:message key="plant.description.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_DESCRIPTION_PARAM_NAME}">${requestScope[assets.PLANT_DESCRIPTION_ATTR_NAME]}</textarea>
            </div>

            <div class="form-group required">
                <label class="control-label" for="plantState">
                    <fmt:message key="plant.state.label" bundle="${langPlant}"/></label>
                <select id="plantState" class="selectpicker form-control" name="${assets.PLANT_STATE_PARAM_NAME}">
                    <option value="SEEDLING" ${(requestScope[assets.PLANT_STATE_ATTR_NAME] == 'SEEDLING') ? 'selected' : ''}>
                        <fmt:message key="plant.state.SEEDLING" bundle="${langPlant}"/>
                    </option>
                    <option value="NORMAL" ${(requestScope[assets.PLANT_STATE_ATTR_NAME] == 'NORMAL') ? 'selected' : ''}>
                        <fmt:message key="plant.state.NORMAL" bundle="${langPlant}"/>
                    </option>
                    <option value="SICK" ${(requestScope[assets.PLANT_STATE_ATTR_NAME] == 'SICK') ? 'selected' : ''}>
                        <fmt:message key="plant.state.SICK" bundle="${langPlant}"/>
                    </option>
                    <c:if test="${!requestScope[assets.IS_CREATING_PLANT_ATTR_NAME]}">
                        <option value="EXTRACTED" ${(requestScope[assets.PLANT_STATE_ATTR_NAME] == 'EXTRACTED') ? 'selected' : ''}>
                            <fmt:message key="plant.state.EXTRACTED" bundle="${langPlant}"/>
                        </option>
                    </c:if>
                </select>
            </div>

            <div class="form-group">
                <label class="control-label" for="imgPath">
                    <fmt:message key="plant.imgPath.label" bundle="${langPlant}"/></label>
                <input type="text" class="form-control" id="imgPath"
                       placeholder="<fmt:message key="plant.imgPath.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_IMG_PATH_PARAM_NAME}"
                       value="${requestScope[assets.PLANT_IMG_PATH_ATTR_NAME]}"/>
            </div>

            <c:choose>
                <c:when test="${requestScope[assets.IS_CREATING_PLANT_ATTR_NAME]}">
                     <input type="hidden"
                            name="${assets.AREA_ID_PARAM_NAME}"
                            value="${requestScope[assets.AREA_ID_ATTR_NAME]}"/>
                </c:when>
                <c:otherwise>
                    <div class="form-group required">
                        <label class="control-label" for="areas">
                            <fmt:message key="plant.area.label" bundle="${langPlant}"/></label>
                        <select id="areas" class="selectpicker form-control"
                                name="${assets.AREA_ID_PARAM_NAME}">
                            <c:forEach var="area" items="${requestScope[assets.ALL_AREAS_ATTR_NAME]}">
                                <option value="${area.id}" ${(requestScope[assets.AREA_ATTR_NAME].id == area.id) ? 'selected' : ''}>
                                    ${area.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </c:otherwise>
            </c:choose>

             <c:choose>
                <c:when test="${requestScope[assets.IS_CREATING_PLANT_ATTR_NAME]}">
                    <button type="submit"
                            class="btn btn-lg btn-success btn-block">
                        <fmt:message key="plant.createPage.submitCreate" bundle="${langPlant}"/></button>
                </c:when>
                <c:otherwise>
                    <button type="submit"
                            class="btn btn-lg btn-default btn-block">
                        <fmt:message key="plant.createPage.submitEdit" bundle="${langPlant}"/></button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>