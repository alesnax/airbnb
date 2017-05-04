<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<title>
	<spring:message code="authorization.page_title" />
</title>
<link rel="shortcut icon"
	href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
<link rel="stylesheet"
	href="<c:url value="/resources/css/user_authorization_style.css" />">
</head>
<body>
	<c:import url="template/header_common.jsp" />

	<div class="page_layout">
		<div class="content">
			<section>
				<div class="wall_content wide_block">
					<div class="validation_block ">
						<div class="fl_l left_block">
							<div class="create_account_form_block page_block">
								<form:form method="POST" modelAttribute="loginDto"
									class="create_account_form" id="create_account">
									<div class="enter_form_element login">
										</strong> <form:input path="email" type="email" />
										</label> <span class="errormsg"><form:errors path="email" class="errormsg" /></span>
									</div>
									<div class="enter_form_element password_form_element">
									<spring:message code="registration.user.password" var="password_text"/>
										<label> <form:input
												path="password" type="password" placeholder="${password_text}"  />
										</label> <span class="errormsg"><form:errors path="password"
												class="errormsg" /></span>
									</div>

									<%--    
                                <div class="enter_form_element password_forgot_element">
                                    <fmt:message bundle="${config}" key="command.go_to_password_recovery" var="forgot_pass_command"/>
                                    <a class="forgot_pass" href="${forgot_pass_command}">
                                        <fmt:message bundle="${loc}" key="guest.user_authorization_page.forgot_password"/>
                                    </a>
                                </div>
                                 --%>

									<div class="enter_form_element submit_button">
										<input type="submit"
											value="<spring:message code="authorisation.form.submit"/>"
											class="login_button">
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>

</body>
</html>
