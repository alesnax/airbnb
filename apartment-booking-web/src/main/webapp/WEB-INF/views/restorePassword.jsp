<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<title><spring:message code="authorization.page_title" /></title>
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/resources/img/q_logo.png" />"
	type="image/png">
<link rel="stylesheet"
	href="<c:url value="${pageContext.request.contextPath}/resources/css/user_authorization_style.css" />">
</head>
<body>
	<c:import url="template/header_common.jsp" />

	<div class="page_layout">
		<div class="content">
			<section>
				<div class="wall_content wide_block">

					<div class="page_block wide_block post_content">
						<div class="page_main_header_block">
							<h1>
								<spring:message code="pass_recov.txt.main_header" />
							</h1>
						</div>
					</div>

					<div class="pass_recov_form_block page_block">
				 	<form class="pass_recov_form" name="pass_recov_form"
							action="/user/forgot_pass" method="post">

							<div class="form_element email">
								<label>
									<div class="left_form_text">
										<spring:message
											code="user_registration.form.email.placeholder"
											var="email_ph" />
										<strong> <spring:message
												code="edit_profile.user_data_form.email" /> <span
											class="notice_star">*</span>
										</strong>
									</div>
									<div class="right_form_field">
										<input type="email" value="" name="email" 
											class="" placeholder="${email_ph}" required> 
											<c:if test="${not empty email_error}">
												<span class="errormsg" ><spring:message
												code="${email_error}" /></span>
											</c:if>
											<span class="errormsg" id="error_0_email"></span>
									</div>
								</label>
							</div>



							<div class="form_element submit_button">
							
								<spring:message code="pass_recov.form.submit_button"
									var="submit_v" />

								<input type="submit" value="${submit_v}" class="recover_button">
								<div class="cancel_submit_block">

									<spring:message code="common.post.cancel_text"
										var="cancel_text" />
									<a class="cancel_link" href="/user/forgot_pass">${cancel_text}</a>
								</div>
							</div>
						</form> 
					</div>
				</div>
			</section>
		</div>
	</div>

</body>
</html>


