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
    <link rel="shortcut icon" href="<c:url value="/resources/img/q_logo.png" />" type="image/png">
	<link rel="stylesheet" href="<c:url value="/resources/css/edit_profile_style.css" />">
</head>
<body>

<c:import url="template/header_common.jsp"/>
<div class="page_layout">
    <div class="content">
        <section>
            <div class="wall_content wide_block">
                
                <div class="create_account_form_block page_block">
                  <form:form method="GET" modelAttribute="apartment_criteria" class="create_account_form" id="create_account" action="/apartment/show_result"> 
                        <div class="form_element name">
                            <label>
                                <div class="left_form_text">
                                    <%-- <spring:message code="user_registration.form.first_name.placeholder" var="fname_ph"/> --%>
                                    <strong>
                                        <%-- <spring:message code="edit_profile.user_data_form.name"/> --%>
                                        Country:
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                  <form:input type="text" path="country" value="" class="" placeholder="choose country" />  
                                 <%--   <span class="errormsg" id="error_0_FirstName"><form:errors path="name" class="errormsg" /></span> --%>
                                </div>
                            </label>
                        </div>
                        
                        <div class="form_element name">
                            <label>
                                <div class="left_form_text">
                                    <%-- <spring:message code="user_registration.form.first_name.placeholder" var="fname_ph"/> --%>
                                    <strong>
                                        <%-- <spring:message code="edit_profile.user_data_form.name"/> --%>
                                        City:
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                  <form:input type="text" path="city" value=""  class="" placeholder="choose city" />  
                                 <%--   <span class="errormsg" id="error_0_FirstName"><form:errors path="name" class="errormsg" /></span> --%>
                                </div>
                            </label>
                        </div>
                       
                        <div class="form_element name">
                            <label>
                                <div class="left_form_text">
                                    <%-- <spring:message code="user_registration.form.first_name.placeholder" var="fname_ph"/> --%>
                                    <strong>
                                        <%-- <spring:message code="edit_profile.user_data_form.name"/> --%>
                                        Max price:
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                  <form:input type="text" path="maxPrice" value=""  class=""  />  
                                 <%--   <span class="errormsg" id="error_0_FirstName"><form:errors path="name" class="errormsg" /></span> --%>
                                </div>
                            </label>
                        </div>
                        
                       
                        <div class="form_element name">
                            <label>
                                <div class="left_form_text">
                                    <%-- <spring:message code="user_registration.form.first_name.placeholder" var="fname_ph"/> --%>
                                    <strong>
                                        <%-- <spring:message code="edit_profile.user_data_form.name"/> --%>
                                        Arrival date:
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                <jsp:useBean id="now" class="java.util.Date" scope="page"/>
						<%-- 		 <fmt:formatDate id={now} value="${now}"/> --%>
                                
                                  <form:input type="date" path="arrivalDate"  class="" />  
                                 <%--   <span class="errormsg" id="error_0_FirstName"><form:errors path="name" class="errormsg" /></span> --%>
                                </div>
                            </label>
                        </div>
                        
                         <div class="form_element name">
                            <label>
                                <div class="left_form_text">
                                 
                                    <strong>
                                       <%--  <spring:message code="edit_profile.user_data_form.name"/> --%>
                                        Leaving date:
                                        <span class="notice_star">*</span>
                                    </strong>
                                </div>
                                <div class="right_form_field">
                                  <form:input type="date" path="leavingDate" value=""  class=""  />  
<%--                                    <span class="errormsg" id="error_0_FirstName"><form:errors path="name" class="errormsg" /></span> --%>
                                </div>
                            </label>
                        </div> 
                        
                        <div class="form_element submit_button">
                           <%--  <spring:message code="edit_profile.user_data_form.submit_button" var="submit_v"/> --%>
                            <input type="submit" value="Find" class="reg_button">
                            <div class="cancel_submit_block" >
                                <spring:message code="common.post.cancel_text" var="cancel_text"/>
                                <a class="cancel_link" href="/apartment/main">${cancel_text}</a>
                            </div>
                        </div> 

				</form:form>
              </div>   
         </div>
                

          
        </section>
    </div>
</div>

</body>
</html>