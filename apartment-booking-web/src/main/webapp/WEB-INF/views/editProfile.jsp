<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<meta charset="utf-8">
<title><spring:message code="edit_profile.page_title" /></title>
<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/resources/img/q_logo.png" />"
	type="image/png">
<link rel="stylesheet"
	href="<c:url value="${pageContext.request.contextPath}/resources/css/edit_profile_style.css" />">
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
								<spring:message code="edit_profile.txt.main_header" />
							</h1>
						</div>
					</div>
					<%--                           photo block--%>
					<div class="top_block">
						<div class="page_block photo_block">
							<div class="page_avatar">
								<div class="photo-wrap">
									<img class="avatar"
										src='${pageContext.request.contextPath}/resources/img/${user.avatar}'
										alt="no_photo"
										onerror="src='${pageContext.request.contextPath}/resources/img/no_avatar.jpg'">
								</div>
							</div>
						</div>
						<div class="page_block upload_avatar_block">
							<div class="upload_avatar_header">
								<h2>
									<spring:message
										code="edit_profile.load_avatar_form.avatar_submit" />
								</h2>
							</div>
							<form class="load_avatar_form" name="load_avatar"
								action="/uploadAvatar" method="post"
								enctype="multipart/form-data">
								<input type="hidden" name="id" value="${user.id}" />
								<div class="form_element name">
									<div class="right_form_field">
										<input type="file" name="avatar">
									</div>
								</div>
								<div class="form_element submit_button">
									<spring:message
										code="edit_profile.user_data_form.submit_button"
										var="submit_v" />
									<input type="submit" value="${submit_v}"
										class="reg_button_load">
								</div>
							</form>
						</div>
					</div>
					<%--        edit main user info block--%>

					<div class="create_account_form_block page_block">
						<form:form method="POST" modelAttribute="editedUser"
							class="create_account_form" id="create_account">
							<form:input type="hidden" path="id"
								value="${sessionScope.user.id}" />
							<div class="form_element name">
								<label>
									<div class="left_form_text">
										<spring:message
											code="user_registration.form.first_name.placeholder"
											var="fname_ph" />
										<strong> <spring:message
												code="edit_profile.user_data_form.name" /> <span
											class="notice_star">*</span>
										</strong>
									</div>
									<div class="right_form_field">
										<form:input type="text" path="name"
											value="${sessionScope.user.name}" id="FirstName" class=""
											placeholder="${fname_ph}" />
										<span class="errormsg" id="error_0_FirstName"><form:errors
												path="name" class="errormsg" /></span>
									</div>
								</label>
							</div>

							<div class="form_element name">
								<label>
									<div class="left_form_text">
										<spring:message
											code="user_registration.form.last_name.placeholder"
											var="lname_ph" />
										<strong> <spring:message
												code="edit_profile.user_data_form.surname" /> <span
											class="notice_star">*</span>
										</strong>
									</div>
									<div class="right_form_field">
										<form:input type="text" path="surname"
											value="${sessionScope.user.surname}" id="LastName" class=""
											placeholder="${lname_ph}" />
										<span class="errormsg" id="error_0_LastName"><form:errors
												path="surname" class="errormsg" /></span>
									</div>
								</label>
							</div>
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
										<form:input type="text" value="${sessionScope.user.email}"
											path="email" id="email" class="" placeholder="${email_ph}" />
										<span class="errormsg" id="error_0_email"><form:errors
												path="email" class="errormsg" /></span>
									</div>
								</label>
							</div>
							<div class="form_element submit_button">
								<spring:message code="edit_profile.user_data_form.submit_button"
									var="submit_v" />
								<input type="submit" value="${submit_v}" class="reg_button">
								<div class="cancel_submit_block">
									<spring:message code="common.post.cancel_text"
										var="cancel_text" />
									<a class="cancel_link" href="/user/edit">${cancel_text}</a>
								</div>
							</div>
						</form:form>
					</div>
					<div class="create_account_form_block page_block">
						<div class="answers_header">
							<a class="answers_title"
								href="${pageContext.request.contextPath}/user/edit/pass"> <span><spring:message
										code="edit_profile.edit_pass_text" /></span>
							</a>
						</div>
					</div>

				</div>
			</section>
		</div>
	</div>

</body>
</html>