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
<fmt:message bundle="${loc}" key="local.login.successfully.logout" var="successful_logout"/>
<fmt:message bundle="${loc}" key="local.login.successfully.registr" var="successful_sign_up"/>
<fmt:message bundle="${loc}" key="local.login.errordata" var="error_data"/>

<fmt:message bundle="${loc}" key="local.login.button" var="login_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="show_index_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="show_index_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<h1>${welcome_message}</h1>
	<p>${ready_message}</p>
	
	<img src="img/hospital_facade.png" class="img-thumbnail" alt="hospital facade image">
	
	<div class="alert alert-primary" role="alert">
	
		<h5><c:if test="${param.message == 'successful_logout'}">
				<c:out value="${successful_logout}"/>
			</c:if>
		</h5>
	
		<h5><c:if test="${param.message == 'successful_registration'}">
				<c:out value="${successful_sign_up}"/>
			</c:if>
		</h5>
		
	</div>
	
	<!-- Displaying of the login-form -->
	
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
	
	<div>
		<h3>${account}<a href="signUp.jsp">${regist}</a></h3>
	</div>

</body>
</html>