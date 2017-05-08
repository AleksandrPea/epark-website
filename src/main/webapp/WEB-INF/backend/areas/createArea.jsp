<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>
<fmt:setBundle basename="webProject.i18n.backend.area" var="langArea"/>

<div class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <h3 class="text-center"><fmt:message key="area.createPage.title" bundle="${langArea}"/></h3>
        <form method="POST" name="createAreaForm"
              action="<c:url value="${assets.CREATE_AREA_URI}"/>"
              accept-charset="UTF-8" role="form">

            <div class="form-group required">
                <label class="control-label" for="name">
                    <fmt:message key="area.name.label" bundle="${langArea}"/></label>
                <input type="text" class="form-control" id="name"
                       placeholder="<fmt:message key="area.name.label" bundle="${langArea}"/>"
                       name="${assets.AREA_NAME_PARAM_NAME}"/>
            </div>

            <div class="form-group">
                <label class="control-label" for="description">
                    <fmt:message key="area.description.label" bundle="${langArea}"/></label>
                <textarea class="form-control" id="description" rows="8"
                       placeholder="<fmt:message key="area.description.label" bundle="${langArea}"/>"
                       name="${assets.AREA_DESCRIPTION_PARAM_NAME}"></textarea>
            </div>
            <button type="submit"
                    class="btn btn-lg btn-success btn-block">
                <fmt:message key="area.createPage.submit" bundle="${langArea}"/></button>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>