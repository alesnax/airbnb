<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <meta charset="utf-8">
    <title>
	    <spring:message code="welcome.page_title"/>
    </title>
   
    <link rel="shortcut icon" href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome_style.css" />">
</head>
<body>
<c:import url="template/header_common.jsp"/> 
<div class = "center-div">
    <p class = "first">Alesnax-bnb</p>
    <p class="second"><spring:message code="welcome.page.first_welcome"/></p>
    <p class="second"><spring:message code="welcome.page.second_welcome"/></p>
    <div class="buttonD">
    
        <%-- <fmt:message bundle="${config}" key="command.go_to_main_page" var="go_to_main_page"/> --%>
        <a href="/">
            <p><spring:message code="welcome.page.link_text"/></p>
        </a>
    </div>
</div>
</body>
</html>