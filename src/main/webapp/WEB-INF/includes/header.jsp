<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="webProject.i18n.general" var="langGeneral"/>

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
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/custom.css">
    <script src="/js/jquery-3.1.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/"><fmt:message key="header.siteName" bundle="${langGeneral}"/></a>
        </div>
        <div class="container">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#"><fmt:message key="header.about" bundle="${langGeneral}"/></a>
                </li>
                <li>
                    <a href="#"><fmt:message key="header.contact" bundle="${langGeneral}"/></a>
                </li>
                <li>
                    <a href="/plants/add">Add plant</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">

