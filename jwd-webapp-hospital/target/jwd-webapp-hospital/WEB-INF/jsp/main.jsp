<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
<fmt:message bundle="${loc}" key="local.main.get.consent" var="get_consent" />
<fmt:message bundle="${loc}" key="local.main.calc_hospitalization" var="calc_hospitalization" />
<fmt:message bundle="${loc}" key="local.main.welcome" var="welcome" />
<fmt:message bundle="${loc}" key="local.main.navigate_login" var="navigate_login" />
<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
<fmt:message bundle="${loc}" key="local.main.denied" var="access_denied" />
<fmt:message bundle="${loc}" key="local.main.data.unavailable" var="data_unavailable" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

	<!-- Logout button -->
		
	<form name="Logout_form" method="POST" action="register" class="float-right">
		<input type="hidden" name="command" value="logout" /> 
		<button type="submit" class="btn btn-link">${logout_button}</button>
	</form>

	 <!-- Change language buttons -->
	
	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	
		<form class="form-inline" name="get_treatment" method="GET" action="font">
			<input type="hidden" name="command" value="get_treatment_page" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${show_treat}</button>
		</form>
		
		<form class="form-inline" name="calc_hospitalization" method="GET" action="font">
			<input type="hidden" name="command" value="get_hospitalization_plan" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${calc_hospitalization}</button>
		</form>
		
	</nav>
	
  		<h1>${login}, ${welcome}!</h1>		

		<c:if test="${not empty requestScope.access_denied}">
			<c:out value="${access_denied}"/>
		</c:if>
	
		<c:if test="${not empty requestScope.data_unavailable}">
			<c:out value="${data_unavailable}"/>
		</c:if>

</body>
</html>