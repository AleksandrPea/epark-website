<%@include file="/WEB-INF/includes/header.jsp" %>

<div class="row text-center">
    <div class="col-md-12">
        <h1><fmt:message key="generalErrorPage.topTitle" bundle="${langGeneral}"/></h1>
    </div>
    <div class="col-md-8 col-md-offset-2">
        <h2><fmt:message key="generalErrorPage.subTitle" bundle="${langGeneral}"/></h2>
        <p><fmt:message key="generalErrorPage.description" bundle="${langGeneral}"/>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>