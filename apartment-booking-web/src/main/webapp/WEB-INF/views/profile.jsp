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
	href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
<link rel="stylesheet"
	href="<c:url value="/resources/css/user_profile_style.css" />">
</head>
<body>

 <c:import url="template/header_common.jsp"/> 
<div class="page_layout">
    <div class="content">
        <section>
            <div class="top_block">
                <div class="page_block photo_block">
                    <div class="page_avatar">
                        <div class="photo-wrap">

                            <img class="avatar" src="<c:url value="/resources/img/no_avatar.jpg" />" alt="no_photo" onerror="src='/resources/img/no_avatar.jpg'">
   
 						<!-- 	<img class="avatar" src="/resources/img/no_avatar.jpg" alt="no_photo" > -->
                   </div>
                             <div class="profile_edit">
                                    <a class="profile_edit_act" href="/user/edit">
                                        <spring:message code="profile.edit_profile_text"/>
                                    </a>
                             </div>
                    </div>
                </div>
                <div class="page_block short_info_block">
                    <div class="profile_name">
                        <h1>${user.name} ${user.surname}</h1>
                    </div>
                    <div class="short_info">
                        <table>
                            <tbody>
                            <tr>
                                <td class="info_label"><spring:message code="profile.birthday_text"/></td>
                                <td class="info_labeled">${user.birthday} </td>
                            </tr>
                            <c:if test="${not empty user.email}">
                                <tr>
                                    <td class="info_label"><spring:message code="profile.email_text"/></td>
                                    <td class="info_labeled">${user.email}</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>