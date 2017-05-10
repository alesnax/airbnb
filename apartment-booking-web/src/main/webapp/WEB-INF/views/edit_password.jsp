<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
    <meta charset="utf-8">
    <title>
        <spring:message code="edit_profile.page_title"/>
    </title>
    <link rel="shortcut icon" href="<c:url value="${pageContext.request.contextPath}/resources/img/q_logo.png" />" type="image/png">
	<link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/edit_profile_style.css" />">
</head>
<body>

<c:import url="template/header_common.jsp"/>
<div class="page_layout">
    <div class="content">
        <section>
            <div class="wall_content wide_block">
                <div class="page_block wide_block post_content">
                    <div class="page_main_header_block">
                        <h1>
                            <spring:message code="edit_profile.txt.main_header"/>
                        </h1>
                    </div>
                </div>
                <%--                           photo block--%>
                <div class="top_block">
                    <div class="page_block photo_block">
                        <div class="page_avatar">
                            <div class="photo-wrap">
                                <img class="avatar" src='${pageContext.request.contextPath}/resources/img/no_avatar.jpg' alt="no_photo" 
                                onerror="src='${pageContext.request.contextPath}/resources/img/no_avatar.jpg'">
                            </div>
                        </div>
                    </div>
                    <div class="page_block upload_avatar_block">
                     
                    </div>
                </div>
                <%--        edit main user info block--%>
                
                <div class="create_account_form_block page_block">
                    <form:form  class="create_account_form"
                          id="change_password" method="POST" modelAttribute="changePassword">
                        <form:input type="hidden" path="id" value="${sessionScope.user.id}"/>
                        
                        <div class="form_element password_form_element">
                            <label>
                                <div class="left_form_text">
                                    <spring:message code="edit_profile.change_password_form.pass1.placeholder" var="p1_ph"/>
                                    <strong>
                                        <spring:message code="edit_profile.change_password_form.pass1.lab"/>
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                    <form:input type="password" path="oldPassword" id="OldPasswd" class="" placeholder="${p1_ph}" />
                                    <span class="errormsg" id="error_0_OldPasswd"><form:errors path="oldPassword" class="errormsg" /></span>
                                </div>
                            </label>
                        </div>
                        <div class="form_element password_form_element">
                            <label>
                                <div class="left_form_text">
                                   <spring:message code="edit_profile.change_password_form.pass2.placeholder" var="p2_ph"/>
                                    <strong>
                                        <spring:message code="edit_profile.change_password_form.pass2.lab"/>
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                    <form:input type="password" path="newPassword" id="Passwd" class="" placeholder="${p2_ph}"/>
                                    <span class="errormsg" id="error_0_Passwd"><form:errors path="newPassword" class="errormsg" /></span>
                                </div>
                            </label>
                        </div>
                        <div class="form_element password_form_element">
                            <label>
                                <spring:message code="edit_profile.change_password_form.pass3.placeholder" var="p3_ph"/>
                                <div class="left_form_text">
                                    <strong>
                                        <spring:message code="edit_profile.change_password_form.pass3.lab"/>
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                    <form:input type="password" path="matchingPassword" id="PasswdAgain" class="" placeholder="${p3_ph}"/>
                                    <span class="errormsg" id="error_0_PasswdAgain"><form:errors path="matchingPassword" class="errormsg" />
                                 <form:errors class="errormsg" />
                                    </span>
                                </div>
                            </label>
                        </div>
                        <div class="form_element submit_button">
                            <spring:message code="edit_profile.change_password_form.submit_button" var="submit_val"/>
                            <input type="submit" value="${submit_val}" class="reg_button">
                            <div class="cancel_submit_block" >
                                
                                <spring:message code="common.post.cancel_text" var="cancel_text"/>
                                <a class="cancel_link" href="/user/edit/pass">${cancel_text}</a>
                            </div>
                        </div>
                    </form:form>
                </div>
                
                <div class="create_account_form_block page_block">
                	<div class="answers_header">
                            <a class="answers_title" href="${pageContext.request.contextPath}/user/edit">
                                <span><spring:message code="edit_profile.edit_main_info_text"/></span>
                            </a>
                        </div>
                </div>
                
            </div>
        </section>
    </div>
</div>

</body>
</html>