<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client Regisration</title>
</head>
<body>
<h1>Register Client application </h1>
			<form:form method="POST" id="registerApp" commandName="clientApp"
				action="register">


				<div id="register-form">

					<fieldset>
						<div class="fieldgroup">
							<font class="reqd">* Required Information</font>
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="name">User ID : <font class="star">*</font></label>
							<form:input type="text" path="name" name="appName"
								value="${clientApp.name}" />
						</div>
					
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="redirectUrl">Redirect url : <font class="star">*</font></label>
							<form:input type="text" path="redirectUrl" name="redirectUrl" />
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="appUrl">Application url : <font class="star">*</font></label>
							<form:input type="text" path="appUrl" name="appUrl" />
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="description">Application discription : <font class="star">*</font></label>
							<form:input type="text" path="description" name="discription" />
						</div>
						<div class="fieldgroup">
							<hr />
							<label for="icon">Application Icon : <font class="star">*</font></label>
							<form:input type="text" path="icon" name="icon" />
						</div>
						
					<div class="fieldgroup">
							<input type="submit" value="register" class="submit" id="submit" />
							<!--  disabled="disabled" -->

						</div>
			</fieldset></div>
			</form:form>


</body>
</html>