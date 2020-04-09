<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>
	
	<title>Login-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.login.welcome" var="welcome_message"/>
<fmt:message bundle="${loc}" key="local.login.ready.help" var="ready_message"/>
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.login.haveaccount" var="account" />
<fmt:message bundle="${loc}" key="local.register" var="regist" />
<fmt:message bundle="${loc}" key="local.login.successfully.logout" var="logout_success"/>
<fmt:message bundle="${loc}" key="local.login.successfully.registr" var="success_sign_up"/>
<fmt:message bundle="${loc}" key="local.login.errordata" var="error_data"/>

<fmt:message bundle="${loc}" key="local.login.button" var="login_button" />
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
	
	<h1>${welcome_message}</h1>
	<p>${ready_message}</p>
	
	<img src="img/hospital_facade.png" class="img-thumbnail" alt="hospital facade image">
	
	<h3><c:if test="${not empty requestScope.logout_success}">
			<c:out value="${logout_success}"/>
		</c:if>
	</h3>
	
	<h3><c:if test="${not empty requestScope.regisrt_success}">
			<c:out value="${success_sign_up}"/>
		</c:if>
	</h3>
			
	
	<div class="form">	
	
		<form name="loginForm" method="POST" action="register">
			<input type="hidden" name="command" value="login" />
				
				<div class="form-field">
				
					<label class="field-label">${login}</label>
					<input type="text" name="login" value="" /> 
					
				</div>
				
				<div class="form-field">
				
					<label class="field-label">${password}</label>
					<input type="password" name="password" value="" />  
					
				</div>
				
				<div class="form-message">	
					<c:if test="${not empty requestScope.errordata}">
						<c:out value="${error_data}"/>
					</c:if>
				</div>
				
				<div class="form-button">
				
					<button type="submit" class="btn btn-secondary">${login_button}</button>
					
				</div>

	    </form>
	
	</div>
	
	<h3>${account}<a href="signUp.jsp">${regist}</a></h3>

</body>
</html>