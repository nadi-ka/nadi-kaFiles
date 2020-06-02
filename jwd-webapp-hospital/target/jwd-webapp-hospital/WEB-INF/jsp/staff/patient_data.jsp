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
	<fmt:message bundle="${loc}" key="local.staff.patient_data.btn_add" var="anyway_add"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.patients_found" var="patients_found"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.choose" var="choose"/>
	<fmt:message bundle="${loc}" key="local.email" var="e_mail"/>
	
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
</head>

<body>

	<!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<c:out value="${requestScope['javax.servlet.forward.query_string']}"/>
	
	<!-- Alert -->
	
	<c:if test="${requestScope.message == 'patients_found'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${patients_found}"/>
		</div>
	</c:if>
				
	<!-- Displaying of the new patient's personal data, if after 'add_patient' patients with the same surnames were found -->
	
	<c:if test="${requestScope.message == 'patient_exists'}">
	
	<ul class="list-group">
		<li class="list-group-item list-group-item-primary">
			<p>${current_patient}</p>
			<ul class="list-group">
				<li class="list-group-item">
					<p>${param.surname} ${param.name}</p>
				</li>
				<li class="list-group-item">
					<p>${date_of_birth} ${param.date_of_birth}</p>
				</li>
				<li class="list-group-item">
					<p>${e_mail} ${param.email}</p>
				</li>
				<li class="list-group-item">
		
            <!-- Button, which obviously add current patient without checking, if such surname already exists -->
            
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
		</li>
	</ul>
	
	</c:if>
	
	<c:if test="${requestScope.message == 'patient_exists'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${patient_exists}"/>
		</div>
	</c:if>
	
	<!-- Displaying of the List of patients with the same surnames -->
		
	<ul class="list-group">
		<c:if test="${!empty patients}">
    		<c:out value="${choose}"/>
		</c:if>
	</ul>
	
	<ul class="list-group">
		<c:forEach var="patient" items="${patients}">
		
			<li class="list-group-item">
		
				<form action="font" method="GET">
					<input type="hidden" name="command" value="get_current_patient_page"/>
					<input type="hidden" name="patient_id" value="${patient.id}" />
					<button type="submit" class="btn btn-link">${patient.surname} ${patient.name} 
					${date_of_birth} ${patient.dateOfBirth}</button>
				</form>
			</li>
		</c:forEach>
	</ul>
		
</body>
</html>	