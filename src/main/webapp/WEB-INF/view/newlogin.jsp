<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login form</title>
</head>
<body>
<div>
<h1>Login form </h1>
			<form:form method="POST" id="userForm" commandName="user"
				action="userlogin">


				<div id="register-form">

					<fieldset>
						<div class="fieldgroup">
							<font class="reqd">* Required Information</font>
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="firstName">User ID : <font class="star">*</font></label>
							<form:input type="text" path="userId" name="userId"
								value="${user.userId }" />
						</div>
					
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="password">Password : <font class="star">*</font></label>
							<form:input type="password" path="password" name="password" />
						</div>
					<div class="fieldgroup">
							<input type="submit" value="signin" class="submit" id="submit" />
							<!--  disabled="disabled" -->

						</div>
			</fieldset></div></form:form>

<%-- <form:form id="myform" action="userlogin" method="POST" commandName="user">
   <form:label path="userId">UserId : </form:label>
   <form:input type="text" name="userId" path="userId"/></br>
   <form:input type="password" name="password" path="password"/>
   <form:input type="text" name="email" path="email"/>
   <input type="submit" value="submit">
</form:form> --%>
</div>
</body>
</html>