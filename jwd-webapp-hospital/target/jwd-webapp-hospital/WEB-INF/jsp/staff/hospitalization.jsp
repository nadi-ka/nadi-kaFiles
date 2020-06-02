<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Hospitalization-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.entry_date" var="entry_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.discharge_date" var="discharge_date" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.start_hospitalization" var="start_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.end_hospitalization" var="end_hospitalization" />
	
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

</head>
<body>

	<!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>  
		<input type="hidden" name="redirect_command" value="get_hospitalization_page"/>     
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>  
		<input type="hidden" name="redirect_command" value="get_hospitalization_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  		
  		<form class="form-inline" action="font" method="post">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${add_patient}</button>
        </form>      
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${surname}" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Forms for start and finish hospitalization -->
	
	<div class="d-inline-block" style="background-color: #eee;">
		<form name="start_hospitalization" action="font" method="POST">
			<input type="hidden" name="command" value="add_hospitalization" />
			<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>
			
			<p><b>${start_hospitalization}</b></p>
				
				<div class="form-group">
  					<label for="date_beginning">${entry_date}</label>
  					<input type="date" name="date_beginning" value="" >
				</div>
				
				<c:if test="${param.message == 'error_data'}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error_data}"/>
					</div>
				</c:if>
				
			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<div class="d-inline-block" style="background-color: #eee;">
		<form name="end_hospitalization" action="font" method="POST">
			<p><b>${end_hospitalization}</b></p>
				<input type="hidden" name="command" value="discarge_patient"/>
				<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>
				
				<div class="form-group">
  					<label for="date_finishing">${discharge_date}</label>
  					<input type="date" name="date_finishing" value="" >
				</div>
			
			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>

</body>
</html>