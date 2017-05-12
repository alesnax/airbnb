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
	href="<c:url value="${pageContext.request.contextPath}/resources/img/q_logo.png" />"
	type="image/png">
<link rel="stylesheet"
	href="<c:url value="${pageContext.request.contextPath}/resources/css/user_registration_style.css" />">
</head>
<body>

	<header>
		<div class="back"></div>
		<div class="topbar_wrapper">
			<div class="fl_l ">

				<a href="/"> <img class="header_logo"
					src="<c:url value="${pageContext.request.contextPath}/resources/img/logo.png" />"
					alt="Q&A logo" />
				</a>
			</div>
			<%--     <c:import url="template/header_search_block.jsp"/> --%>
			<c:import url="template/switch_language.jsp" />
			<div class="fl_r h_links">
				<a class="h_link" href="${pageContext.request.contextPath}/login"> <spring:message
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
								<form:label path="name">
									<strong> <spring:message code="registration.user.name" />
										<span class="notice_star">*</span></strong>
								</form:label>
								<form:input path="name" />

								<form:errors path="name" class="errormsg" />
							</div>

							<div class="form_element login">
								<form:label path="surname">
									<strong> <spring:message
											code="registration.user.surname" /> <span
										class="notice_star">*</span>
									</strong>
								</form:label>
								<form:input path="surname" />

								<form:errors path="surname" class="errormsg" />
							</div>


							<div class="form_element login">
								<form:label path="email">
									<strong> <spring:message
											code="registration.user.email" /> <span class="notice_star">*</span>
									</strong>
								</form:label>
								<form:input path="email" type="email" />

								<form:errors path="email" class="errormsg" />
							</div>


							<div class="form_element login">
								<form:label path="password">
									<strong> <spring:message
											code="registration.user.password" /> <span
										class="notice_star">*</span>
									</strong>
								</form:label>
								<form:password path="password" />

								<form:errors path="password" class="errormsg" />
							</div>

							<div class="form_element login">
								<form:label path="matchingPassword">
									<strong> <spring:message
											code="registration.user.matching_password" /> <span
										class="notice_star">*</span>
									</strong>
								</form:label>
								<form:password path="matchingPassword" />

								<form:errors class="errormsg" />
								<form:errors path="matchingPassword" class="errormsg" />

							</div>

							<div class="form_element submit_button">
								<input type="submit"
									value="<spring:message code="registration.user.register" />"
									class="reg_button" />
							</div>

						</form:form>

					</div>
				</div>
			</section>
		</div>
	</div>





</body>
<html>