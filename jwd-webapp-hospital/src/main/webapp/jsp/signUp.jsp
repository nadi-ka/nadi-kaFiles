<%@ page language="java" contentType="text/html; charset=UTF-8;"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SignUp-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.register" var="register" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.email" var="e-mail" />
<fmt:message bundle="${loc}" key="local.signup.button" var="signup_btn" />
<fmt:message bundle="${loc}" key="local.signup.account" var="account" />
<fmt:message bundle="${loc}" key="local.signup.reflogin" var="reflogin" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
	
<fmt:setLocale value="${requestScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.signup.errordata" var="errordata" />
<fmt:message bundle="${loc}" key="local.nullpage" var="nullpage" />
<fmt:message bundle="${loc}" key="local.wrongaction" var="wrongaction" />
	
</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="local" value="ru" /> 
		<input type="submit" value="${ru_button}" /><br />
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="local" value="en" /> 
		<input type="submit" value="${en_button}" /><br />
	</form>

	<h1><c:out value="${register}" /></h1>

	<form name="registerForm" method="POST" action="register">
		<input type="hidden" name="command" value="sign_up" />

		<fieldset>
			<label><c:out value="${login}" /> <input type="text" name="login"><br />
			</label> 
			<label><c:out value="${password}" /><input type="password" name="password"><br />
			</label> 
			<label><c:out value="${e-mail}" /><input type="email" name="email"><br />
			</label> 
			
			<c:out value="${errordata}"/> 
			<br /> 
			<c:out value="${wrongaction}"/> 
			<br /> 
			<c:out value="${nullpage}"/>  
			<br />

			<input type="submit"  name="btn_signup" value="${signup_btn}" /><br/>
		</fieldset>

	</form>

	<h3>
		<c:out value="${account}" /> <a href="login.jsp"><c:out value="${reflogin}"/></a>
	</h3>

</body>
</html>