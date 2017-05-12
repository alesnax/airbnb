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
								<%-- <spring:message code="pass_recov.txt.main_header" /> --%>
								Please, check your email, to restore password!
							</h1>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</body>