<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	
	<title>Login-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.login.welcome" var="welcome_message"/>
<fmt:message bundle="${loc}" key="local.login.ready.help" var="ready_message"/>
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.login.have_account" var="account" />
<fmt:message bundle="${loc}" key="local.register" var="regist" />
<fmt:message bundle="${loc}" key="local.login.successfully.logout" var="successful_logout"/>
<fmt:message bundle="${loc}" key="local.login.successfully.registr" var="successful_sign_up"/>
<fmt:message bundle="${loc}" key="local.login.errordata" var="error_data"/>
<fmt:message bundle="${loc}" key="local.login.access_dinied" var="access_denied"/>

<fmt:message bundle="${loc}" key="local.login.button" var="login_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_index_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_index_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<h1>${welcome_message}</h1>
	<p>${ready_message}</p>
	
	<img src="img/hospital_facade.png" class="img-thumbnail" alt="hospital facade image">
	
	<!-- Alerts -->
	
		<c:if test="${param.message == 'successful_logout'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${successful_logout}"/>
			</div>
		</c:if>
		
		<c:if test="${param.message == 'successful_registration'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${successful_sign_up}"/>
			</div>
		</c:if>
		
		<c:if test="${param.message == 'access_denied'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${access_denied}"/>
			</div>
		</c:if>
	
	<!-- Login-form -->
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;">	
	
		<form name="loginForm" method="POST" action="register">
			<input type="hidden" name="command" value="login" />
				
				<div class="form-group">
				
					<label for="login">${login}</label>
					<input type="text" name="login" value="" /> 
					
				</div>
				
				<div class="form-group">
				
					<label for="password">${password}</label>
					<input type="password" name="password" value="" />  
					
				</div>
				
				<c:if test="${param.message == 'error_data'}">
					<div class="alert alert-danger" role="alert">	
						<c:out value="${error_data}"/>
					</div>
				</c:if>
				
				<button type="submit" class="btn btn-secondary">${login_button}</button>
	    </form>
	</div>
	
	<!-- Go to signUp-page -->
	
	<div>
		<h5>
			<c:out value="${account}" /> 
		</h5>
		<form name="To_signup_page" method="GET" action="font" >
			<input type="hidden" name="command" value="get_signup_page" /> 
			<button type="submit" class="btn btn-link">${regist}</button>
		</form>
	</div>

</body>
</html>