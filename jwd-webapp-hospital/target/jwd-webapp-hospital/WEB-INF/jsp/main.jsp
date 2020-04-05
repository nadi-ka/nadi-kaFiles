<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/style.css"/>

<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.main.showtreat" var="showtreat" />
<fmt:message bundle="${loc}" key="local.main.welcome" var="welcome" />
<fmt:message bundle="${loc}" key="local.main.navigate_login" var="navigate_login" />
<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
<fmt:message bundle="${loc}" key="local.main.denied" var="access_denied" />
<fmt:message bundle="${loc}" key="local.main.data.unavailable" var="data_unavailable" />

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
	
	<h3>${login},<c:out value="${welcome}" /></h3>
	
		
	<form name="showTreatment" method="GET" action="register">
		<input type="hidden" name="command" value="showTreatment" /> 
		<input type="submit" name="btn_showTreatment" value="${showtreat}" />
		
		<c:if test="${not empty requestScope.access_denied}">
			<c:out value="${access_denied}"/>
		</c:if>
	
		<c:if test="${not empty requestScope.data_unavailable}">
			<c:out value="${data_unavailable}"/>
		</c:if>
		
	</form>	


	<form name="LogoutForm" method="POST" action="register">

		<input type="hidden" name="command" value="logout" /> 
		<input type="submit" name="btn_logout" value="${logout_button}">

	</form>

	<a href="${pageContext.request.contextPath}/index.jsp"><c:out value="${navigate_login}" /></a>

</body>
</html>