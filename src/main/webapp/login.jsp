<%@include file="/WEB-INF/includes/header.jsp" %>
<fmt:setBundle basename="webProject.i18n.credential" var="credential"/>
<fmt:setBundle basename="webProject.i18n.validation" var="validation"/>

<div id="login-form" class="row">
    <div class="col-xs-8 col-xs-offset-2 col-md-4 col-md-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="credential.signIn.title" bundle="${credential}"/></h3>
                <form method="POST" name="loginform"
                      action="<c:url value="${assets.SIGN_IN_URI}"/>"
                      accept-charset="UTF-8" role="form">
                    <div class="form-group validated required">
                        <label class="control-label" for="login">
                            <fmt:message key="credential.login.label" bundle="${credential}"/></label>
                        <input type="text" class="form-control" id="login"
                               placeholder="<fmt:message key="credential.login.label" bundle="${credential}"/>"
                               name="${assets.LOGIN_PARAM_NAME}"
                               value="${requestScope[assets.LOGIN_ATTR_NAME]}"/>

                        <mytags:formMessages formInputName="${assets.LOGIN_PARAM_NAME}"/>


                    </div>
                    <div class="form-group validated required">
                        <label class="control-label" for="userPassword">
                            <fmt:message key="credential.password.label" bundle="${credential}"/></label>
                        <input type="password" class="form-control" id="userPassword"
                               placeholder="<fmt:message key="credential.password.label" bundle="${credential}"/>"
                               name="${assets.PASSWORD_PARAM_NAME}"/>

                        <mytags:formMessages formInputName="${assets.PASSWORD_PARAM_NAME}"/>
                    </div>

                    <button type="submit"
                            class="btn btn-lg btn-primary btn-block">
                        <fmt:message key="credential.signIn.label" bundle="${credential}"/></button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/includes/footer.jsp" %>