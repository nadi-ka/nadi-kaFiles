<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Patients-data-page</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.staff.patient_data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.patient_exists" var="patient_exists"/>
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.current_patient" var="current_patient"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.same_surnames" var="same_surnames_list"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.btn_add" var="anyway_add"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.patients_found" var="patients_found"/>
	
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
</head>

<body>

	<!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="redirect_command" value="get_patient_data_page"/>
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="redirect_command" value="get_patient_data_page"/>
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<div class="alert alert-primary" role="alert">
  		<h5><c:if test="${param.message == 'added_successfully'}">
				<c:out value="${added_successfully}"/>
			</c:if>
			<c:if test="${requestScope.message == 'patient_exists'}">
				<c:out value="${patient_exists}"/>
			</c:if>
			<c:if test="${requestScope.message == 'patients_found'}">
				<c:out value="${patients_found}"/>
			</c:if>
		</h5>		
	</div>
	
	<!-- Displaying of the current entered patient's personal data -->
	
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
            
            <!-- Button, which obviously add current patient without checking, if he already exists -->
            
            <form name="add_patient" action="font" method="POST">
				<input type="hidden" name="command" value="add_new_patient" />
				<input type="hidden" name="surname" value="${param.surname}" />
				<input type="hidden" name="name" value="${param.name}" />
				<input type="hidden" name="date_of_birth" value="${param.date_of_birth}" />
				<input type="hidden" name="email" value="${param.email}" />
				<input type="hidden" name="add_obviously" value="true" />
				<button type="submit"  class="btn btn-info">
					${anyway_add}
				</button>
			</form>
			
		</li>
	</ul>
	
	<!-- Displaying of the List of patients with the same surnames, if they exist -->
		
	<ul class="list-group">
		<c:if test="${!empty patients}">
    		<c:out value="${same_surnames_list}"/>
		</c:if>
	</ul>
	
	<ul class="list-group">
		<c:forEach var="patient" items="${patients}">
			<li class="list-group-item">${patient.surname} ${patient.name} ${date_of_birth} ${patient.dateOfBirth}</li>
		</c:forEach>
	</ul>
		
</body>
</html>	