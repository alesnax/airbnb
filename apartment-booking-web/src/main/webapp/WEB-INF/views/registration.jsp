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


	<div class="page_layout">
		<div class="content">
			<section>
				<div class="validation_block ">
					<div class="create_account_form_block page_block">
					
					
						<form:form method="POST" modelAttribute="account"
							class="create_account_form">
					
							<div class="form_element login">
								<label> 
								<strong>  <spring:message code="registration.user.name" /> <span class="notice_star">*</span> </strong> 
								<form:input path="name" />
								</label> 
								<span class="errormsg"> <form:errors path="name" class="errormsg" /></span>
							</div>
					
							<div class="form_element login">
								<label> 
								<strong> <spring:message code="registration.user.surname" /> <span class="notice_star">*</span> </strong> 
								<form:input path="surname" />
								</label> 
								<span class="errormsg"> <form:errors path="surname" class="errormsg" /></span>
							</div>
					
							
							<div class="form_element login">
								<label> 
								<strong>  <spring:message code="registration.user.email" /> <span class="notice_star">*</span> </strong> 
								<form:input path="email" type="email" />
								</label> 
								<span class="errormsg"> <form:errors path="email" class="errormsg" /></span>
							</div>
					
						
							<div class="form_element login">
								<label> 
								<strong>  <spring:message code="registration.user.password" /> <span class="notice_star">*</span> </strong> 
								<form:input path="password" type="password" />
								</label> 
								<span class="errormsg"> <form:errors path="password" class="errormsg" /></span>
							</div>
							
							<div class="form_element login">
								<label> 
								<strong>  <spring:message code="registration.user.matching_password" /> <span class="notice_star">*</span> </strong> 
								<form:input path="matchingPassword" type="password" />
								</label> 
								<span class="errormsg"> <form:errors path="matchingPassword" class="errormsg" /></span>
							</div>
						
							<div class="form_element submit_button">				
								<input type="submit" value="<spring:message code="registration.user.register" />" class="reg_button" />
							</div>
							
						</form:form>
					
					
					
					</div>
				</div>
			</section>
		</div>
	</div>


	


</body>
<html>
