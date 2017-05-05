<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<meta charset="utf-8">
<title><spring:message code="common.page_title" /></title>
<link rel="shortcut icon"
	href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
<link rel="stylesheet"
	href="<c:url value="/resources/css/user_registration_style.css" />">
</head>
<body>

	<header>
		<div class="back"></div>
		<div class="topbar_wrapper">
			<div class="fl_l ">

				<a href="/"> <img class="header_logo"
					src="<c:url value="/resources/img/logo.png" />" alt="Q&A logo" />
				</a>
			</div>
			<%--     <c:import url="template/header_search_block.jsp"/> --%>
			<c:import url="template/switch_language.jsp" />
			<div class="fl_r h_links">
				<a class="h_link" href="/user/login"> <spring:message
						code="common.sign_in_text" />
				</a>
			</div>
		</div>
	</header>


	registration
	<hr />
	<!-- <a href="/user/registration?lang=en">English </a> |
	<a href="/user/registration?lang=ru">Russian</a> -->
	<hr />
	<form:form method="POST" modelAttribute="account">
		<spring:message code="registration.user.name" />
		<form:input path="name" />
		<br />
		<form:errors path="name" class="errormsg" />
		<br />

		<spring:message code="registration.user.surname" />
		<form:input path="surname" />
		<br />
		<form:errors path="surname" class="errormsg" />
		<br />

		<spring:message code="registration.user.email" />
		<form:input path="email" type="email" />
		<br />
		<form:errors path="email" class="errormsg" />
		<br />

		<spring:message code="registration.user.password" />
		<form:input path="password" type="password" />
		<br />
		<form:errors path="password" class="errormsg" />
		<br />

		<spring:message code="registration.user.matching_password" />
		<form:input path="matchingPassword" type="password" />
		<br />
		<form:errors path="matchingPassword" class="errormsg" />
		<br/>
		<form:errors class="errormsg" />
		<br />
		
		<input type="submit"
			value="<spring:message code="registration.user.register"/>" />
	</form:form>


</body>
<html>