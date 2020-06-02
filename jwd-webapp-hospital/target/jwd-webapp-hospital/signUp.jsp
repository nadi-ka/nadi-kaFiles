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
<fmt:message bundle="${loc}" key="local.signup.errordata" var="error_data" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="show_signup_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="show_signup_page"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>

	<h1>${register}</h1>
	
	<img class="rounded float-right" src="img/registration.png" 
		 alt="pen and paper image" style="width:4em; height:5em;" />

	<form name="registerForm" method="POST" action="register">
		<input type="hidden" name="command" value="sign_up" />
			
			<div class="form-group">
				
				<label class="field-label">${login}</label>
				<input type="text" name="login" value="" /> 
					
			</div>
			
			<div class="form-group">
				
				<label class="field-label">${password}</label>
				<input type="password" name="password" value="" /> 
					
			</div>
			
			<div class="form-group">
				
				<label class="field-label">${email}</label>
				<input type="email" name="email" value="" /> 
					
			</div>
			
			<div class="alert alert-danger" role="alert">
	
				<h5><c:if test="${param.message == 'error_data'}">
						<c:out value="${error_data}"/>
					</c:if>
				</h5>
		
			</div>

			<button type="submit" class="btn btn-primary" name="btn_signup">${signup_btn}</button>

	</form>

	<h3>
		<c:out value="${account}" /> <a href="index.jsp">${reflogin}</a>
	</h3>

</body>
</html>