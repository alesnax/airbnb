<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User profile</title>

<link rel="shortcut icon"
	href="<c:url value="${pageContext.request.contextPath}/resources/img/q_logo.png" />"
	type="image/png">
<link rel="stylesheet"
	href="<c:url value="${pageContext.request.contextPath}/resources/css/edit_profile_style.css" />">
</head>
<style type="text/css">
TABLE {
	width: 300px; /* Ширина таблицы */
	border-collapse: collapse; /* Убираем двойные линии между ячейками */
}

TD, TH {
	padding: 3px; /* Поля вокруг содержимого таблицы */
	border: 1px solid black; /* Параметры рамки */
}

TH {
	background: #b0e0e6; /* Цвет фона */
}
</style>
<body>

	<c:import url="template/header_common.jsp" />
	<div class="page_layout">
		<div class="content">
			<section>

				<h1><span>${page_name}</span></h1>
				<table>
					<thead>
						<tr>
							<th></th>
							<th>name</th>
							<th>max guests</th>
							<th>type</th>
							<th>country</th>
							<th>city</th>
							<th></th>
						</tr>
					</thead>
					<c:forEach var="apartment" items="${apartments}" varStatus="status">
						<tr style="border: 1px;">
							<td>${status.count}</td>
							<td>${apartment.name}</td>
							<td>${apartment.maxGuestNumber}</td>
							<td>${apartment.type.type}</td>
							<td>${apartment.location.country}</td>
							<td>${apartment.location.city}</td>
							<td>
								<a href="${pageContext.request.contextPath}/apartment/info/${apartment.id}">see info</a>
							</td>

						</tr>
					</c:forEach>
				</table>

			</section>
		</div>
	</div>
</body>
</html>