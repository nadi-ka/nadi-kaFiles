<%@ page language="java" contentType="text/html; charset=UTF-8;"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>
	
	<title>SignUp-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.register" var="register" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.signup.button" var="signup_btn" />
<fmt:message bundle="${loc}" key="local.signup.account" var="account" />
<fmt:message bundle="${loc}" key="local.signup.reflogin" var="reflogin" />
<fmt:message bundle="${loc}" key="local.signup.errordata" var="errordata" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" /> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" /> 
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>

	<h1>${register}</h1>
	
	<img src="img/registration.png" alt="pen and paper image" style="width:3em; height:4em;" />
	
	<div class="form">

	<form name="registerForm" method="POST" action="register">
		<input type="hidden" name="command" value="sign_up" />

		<fieldset>
			
			<div class="form-field">
				
				<label class="field-label">${login}</label>
				<input type="text" name="login" value="" /> 
					
			</div>
			
			<div class="form-field">
				
				<label class="field-label">${password}</label>
				<input type="password" name="password" value="" /> 
					
			</div>
			
			<div class="form-field">
				
				<label class="field-label">${email}</label>
				<input type="email" name="email" value="" /> 
					
			</div>
		
			<div class="form-message">	
				<c:if test="${not empty requestScope.errordata}">
					<c:out value="${error_data}"/>
				</c:if>
			</div>
			
			<div class="form-button">

				<input type="submit"  name="btn_signup" value="${signup_btn}" /><br/>
				
			</div>
			
		</fieldset>

	</form>
	
	</div>

	<h3>
		<c:out value="${account}" /> <a href="index.jsp">${reflogin}</a>
	</h3>

</body>
</html>