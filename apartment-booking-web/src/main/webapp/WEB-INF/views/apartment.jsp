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
	href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
<link rel="stylesheet"
	href="<c:url value="/resources/css/edit_profile_style.css" />">

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

</head>
<body>

	<c:import url="template/header_common.jsp" />
	<div class="page_layout">
		<div class="content">
			<section>
				<table>
					<thead>
						<tr>
						
							<th>name</th>
							<th>max guests</th>
							<th>type</th>
						</tr>
					</thead>
					
					<tr style="border: 1px;">
						<td>${apartment.name}</td>
						<td>${apartment.maxGuestNumber}</td>
						<td>${apartment.type.type}</td>
					</tr>
				</table>

				<a href="/apartment/find/;locationId=${apartment.location.city}">show other apartments in ${apartment.location.city}</a>
				<a href="/apartment/main">go to main</a>

			</section>
		</div>
	</div>

</body>
</html>