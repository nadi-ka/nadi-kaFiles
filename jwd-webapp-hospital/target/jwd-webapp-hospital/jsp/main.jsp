<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>

<head>
<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.main.showtreat" var="showtreat" />
<fmt:message bundle="${loc}" key="local.main.welcome" var="welcome" />
<fmt:message bundle="${loc}" key="local.main.navigate_login" var="navigate_login" />
<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
	
<fmt:setLocale value="${requestScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.main.denied" var="accessdenied" />	
	
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
	
	<hr />
		<h3>${user},<c:out value="${welcome}" /></h3>
	<hr />
		
	<form name="showTreatment" method="GET" action="register">
		<input type="hidden" name="command" value="showTreatment" /> 
		<input type="submit" name="btn_showTreatment" value="${showtreat}" /><br />
	</form>	


	<form name="LogoutForm" method="POST" action="register">

		<input type="hidden" name="command" value="logout" /> 
		<input type="submit" name="btn_logout" value="${logout_button}">

	</form>

	<a href="/index.jsp"><c:out value="${navigate_login}" /></a>

	<br /> 
	<c:out value="${accessdenied}" />
	<br />

</body>
</html>