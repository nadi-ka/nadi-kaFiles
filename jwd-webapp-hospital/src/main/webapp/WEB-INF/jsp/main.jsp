<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

<title>Main-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />

<fmt:message bundle="${loc}" key="local.main.get.consent" var="get_consent" />
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
		
	<form name="Logout_form" method="POST" action="font" class="float-right">
		<input type="hidden" name="command" value="logout" /> 
		<button type="submit" class="btn btn-link">${logout_button}</button>
	</form>

	 <!-- Change language buttons -->
	
	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<div id="patient_nav">
    	<jsp:include page="/WEB-INF/jsp/part/patient_nav.jsp"/>
	</div>
	
	<!-- Navigation menu -->

	<div class="ml-4">
  		<h1>${welcome}</h1>
  	</div>
  		
  	<img src="img/background_theme.png" alt="stethoscope_image" class="ml-4">
  		
  	<!-- Alerts -->		

	<c:if test="${not empty requestScope.access_denied}">
		<c:out value="${access_denied}"/>
	</c:if>
	
	<c:if test="${not empty requestScope.data_unavailable}">
		<c:out value="${data_unavailable}"/>
	</c:if>
		
	<hr>
		
	<!-- Footer -->
		
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>

</body>
</html>