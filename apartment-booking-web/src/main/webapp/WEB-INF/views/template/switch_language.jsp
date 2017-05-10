<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header_common_style.css">
</head>
<body>
	<div class="fl_r lang_links">
		<a class="lang_link" href="?lang=en"> <span class="lang_label">
				<spring:message code="common.change_language.en" />
		</span> <span class="en_icon"></span>
		</a>
	</div>
	<div class="fl_r lang_links">
		<a class="lang_link" href="?lang=ru"> <span class="lang_label">
				<spring:message code="common.change_language.ru" />
		</span> <span class="ru_icon"></span>
		</a>
	</div>
</body>
</html>