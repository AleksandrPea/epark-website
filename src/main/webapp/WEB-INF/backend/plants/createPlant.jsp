<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.backend.plant" var="langPlant"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center"><fmt:message key="plant.createPlantPage.title" bundle="${langPlant}"/></h3>
        <form method="POST" name="createPlantForm" id="createPlantForm"
              action="<c:url value="${assets.CREATE_PLANT_URI}/${requestScope[assets.AREA_ID_ATTR_NAME]}"/>"
              accept-charset="UTF-8" role="form">

            <div class="form-group required">
                <label class="control-label" for="name">
                    <fmt:message key="plant.name.label" bundle="${langPlant}"/></label>
                <input type="text" class="form-control" id="name"
                       placeholder="<fmt:message key="plant.name.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_NAME_PARAM_NAME}"/>
            </div>

            <div class="form-group">
                <label class="control-label" for="description">
                    <fmt:message key="plant.description.label" bundle="${langPlant}"/></label>
                <textarea class="form-control" id="description" rows="8"
                       placeholder="<fmt:message key="plant.description.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_DESCRIPTION_PARAM_NAME}"></textarea>
            </div>

            <div class="form-group required">
                <label class="control-label" for="plantState">
                    <fmt:message key="plant.state.label" bundle="${langPlant}"/></label>
                <select id="plantState" class="selectpicker form-control" name="${assets.PLANT_STATE_PARAM_NAME}">
                    <option value="SEEDLING">
                        <fmt:message key="plant.state.seedling" bundle="${langPlant}"/>
                    </option>
                    <option value="NORMAL">
                        <fmt:message key="plant.state.normal" bundle="${langPlant}"/>
                    </option>
                    <option value="SICK">
                        <fmt:message key="plant.state.sick" bundle="${langPlant}"/>
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label class="control-label" for="imgPath">
                    <fmt:message key="plant.imgPath.label" bundle="${langPlant}"/></label>
                <input type="text" class="form-control" id="imgPath"
                       placeholder="<fmt:message key="plant.imgPath.label" bundle="${langPlant}"/>"
                       name="${assets.PLANT_IMG_PATH_PARAM_NAME}"/>
            </div>

            <button type="submit"
                    class="btn btn-lg btn-success btn-block">
                <fmt:message key="plant.createPage.submit" bundle="${langPlant}"/></button>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>