<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Current-patient-page</title>
	
	<fmt:message bundle="${loc}" key="local.staff.patient_data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.current_patient" var="current_patient"/>
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth"/>
	
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>
<body>

	<!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="redirect_command" value="get_current_patient_page"/>
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="redirect_command" value="get_current_patient_page"/>
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<div class="alert alert-primary" role="alert">
  		<h5><c:if test="${param.message == 'added_successfully'}">
				<c:out value="${added_successfully}"/>
			</c:if>
		</h5>
	</div>
	
	<!-- Displaying of the current entered/chosen patient's personal data -->
	
	<ul class="list-group">
		<li class="list-group-item list-group-item-info">
			<p>${current_patient}</p>
			
			<c:if test="${param.surname != null}">
  				<c:out value="${param.surname}"/>
            </c:if>
            <c:if test="${param.name != null}">
  				<c:out value="${param.name}"/>
            </c:if>
            <c:if test="${param.date_of_birth != null}">
  				<c:out value="${date_of_birth} ${param.date_of_birth}"/>
            </c:if>	
		</li>
	</ul>
</body>
</html>