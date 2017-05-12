<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header_common_style.css">
</head>
<body>
<%-- <fmt:message bundle="${config}" key="img.common.logo" var="main_logo"/> --%>

<header>
    <div class="back"></div>
    <div class="topbar_wrapper">
        <div class="fl_l ">
            
            <a href="${pageContext.request.contextPath}/">
               <img class="header_logo" src="<c:url value="${pageContext.request.contextPath}/resources/img/logo.png" />" alt="Q&A logo"/>
            </a>
        </div>
       <spring:message code="common.header.search_form.placeholder" var="s_form_ph"/>
        <spring:message code="common.header.search_form.submit_value" var="s_submit_v"/>
      <div class="fl_l search_block">
            <form class="search_form" action="/apartment/search_by_name" method="get">
                <input class="s_back_img search_input" name="apartment_name" value="" type="search" placeholder="${s_form_ph}" required pattern=".{3,}"/>
                <input class="search_submit" type="submit" value="${s_submit_v}"/>
            </form>
        </div>
      <c:if test="${not empty sessionScope.user}">
            <div class="fl_r h_links">
                <a class="h_link" href="${pageContext.request.contextPath}/logout">
                    <spring:message code="common.log_out_text"/>
                </a>
            </div>
            <div class="fl_r h_links">
                <a class="h_link" href="${pageContext.request.contextPath}/user/profile">
                   profile
                </a>
            </div>
        </c:if> 
        <c:if test="${empty sessionScope.user}"> 
            <div class="fl_r lang_links">
               
                <a class="lang_link" href="?lang=en">
                    <span class="lang_label">
                    	<spring:message code="common.change_language.en"/>
                    </span>
                    <span class="en_icon"></span>
                </a>
            </div>
            <div class="fl_r lang_links">
               
                <a class="lang_link" href="?lang=ru">
                    <span class="lang_label">
                        <spring:message code="common.change_language.ru"/>
                    </span>
                    <span class="ru_icon"></span>
                </a>
            </div>
            <div class="fl_r h_links">
                <a class="h_link" href="${pageContext.request.contextPath}/registration">
                    <spring:message code="common.sign_up_text"/>
                </a>
            </div>
            <div class="fl_r h_links">
                <a class="h_link" href="${pageContext.request.contextPath}/login">
                    <spring:message code="common.sign_in_text"/>
                </a>
            </div>
      </c:if> 
        
        </div>
    </div>
</header>