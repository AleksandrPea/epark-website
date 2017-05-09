<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="webProject.i18n.backend.general" var="langGeneral"/>

<%-- <c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/> --%>

<!DOCTYPE html>
<%-- <html lang="${language}"> --%>
<html lang="en">
<head>
    <title><fmt:message key="htmlHead.title" bundle="${langGeneral}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/confirm-delete.js"></script>
</head>
<body>

<%@include file="/WEB-INF/includes/topMenu.jsp" %>

<div id="main-container" class="container">
    <c:if test="${(not empty messages) && (not empty messages[assets.GENERAL_MESSAGES_BLOCK_NAME])}">
        <%@include file="/WEB-INF/includes/topMessages.jsp" %>
    </c:if>