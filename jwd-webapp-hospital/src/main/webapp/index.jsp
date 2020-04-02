<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>Index-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.login.haveaccount" var="account" />
<fmt:message bundle="${loc}" key="local.register" var="regist" />
<fmt:message bundle="${loc}" key="local.login.button"
	var="login_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />

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

	<h1><c:out value="${logoutsuccess}" /></h1>
	<h1><c:out value="${registrsuccess}" />, ${requestScope.user}</h1>


	<form name="loginForm" method="POST" action="register">
	
		<input type="hidden" name="command" value="login" />
		<c:out value="${login}"/>
		<br /> 
		<input type="text" name="login" value="" /> 
		<br />
		<c:out value="${password}"/>
		<br /> 
		<input type="password" name="password" value="" /> 
		<br />
		
		<c:out value="${errordata}"/> 
		<br /> 
		<c:out value="${wrongaction}"/> 
		<br /> 
		<c:out value="${nullpage}"/>  
		<br />
		
		<input type="submit" value="${login_button}" /><br />

		<h3>
			<c:out value="${account}"/>
			<a href="jsp/signUp.jsp"><c:out value="${regist}"/></a>
		</h3>
	</form>
	<hr />

</body>
</html>