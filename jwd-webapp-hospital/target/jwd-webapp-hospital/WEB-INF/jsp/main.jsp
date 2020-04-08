<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
<fmt:message bundle="${loc}" key="local.main.get.consent" var="get_consent" />
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
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" /> 
		<button type="button" class="btn btn-dark">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" /> 
		<button type="button" class="btn btn-dark">${en_button}</button>
	</form>
	
  		<h1>${login}, ${welcome}</h1>
	
	
		<form name="showTreatment" method="GET" action="register">
			<input type="hidden" name="command" value="show_treatment" /> 
			<button type="button" class="btn btn-primary">${show_treat}</button>
		</form>
		
		<form name="giveConsent" method="POST" action="register">
			<input type="hidden" name="command" value="get_consent" /> 
			<button type="button" class="btn btn-primary">${get_consent}</button>
		</form>
			

		<c:if test="${not empty requestScope.access_denied}">
			<c:out value="${access_denied}"/>
		</c:if>
	
		<c:if test="${not empty requestScope.data_unavailable}">
			<c:out value="${data_unavailable}"/>
		</c:if>
		
	<div class="fixed-bottom">	

	<form name="LogoutForm" method="POST" action="register">

		<input type="hidden" name="command" value="logout" /> 
		<button type="button" class="btn btn-link">${logout_button}</button>

	</form>
	
	</div>

</body>
</html>