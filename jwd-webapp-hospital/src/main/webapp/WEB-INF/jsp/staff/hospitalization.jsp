<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Hospitalization-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.make_diagnosis" var="make_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.prescribe_treatment" var="prescribe_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.get_current" var="get_current_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.error_discharge" var="error_data_discharge" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.entry_date" var="entry_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.discharge_date" var="discharge_date" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.start_hospitalization" var="start_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.end_hospitalization" var="end_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.med_history" var="medical_history_num" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.last_hospitalization" var="last_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.discharged_success" var="discharged_success" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.diagnosis_absent" var="diagnosis_absent" />
	<fmt:message bundle="${loc}" key="local.staff.hospital.final_diagnosis" var="final_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.name" var="diagnosis_name" />
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	
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

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>  
		<input type="hidden" name="redirect_command" value="get_hospitalization_page"/>     
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>  
		<input type="hidden" name="redirect_command" value="get_hospitalization_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
  		<form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${add_patient}</button>
        </form>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_diagnosis_page" />
  			<input type="hidden" name="patient_id" value="${requestScope.patient.id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${make_diagnosis}</button>
        </form>

        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_prescriptions_page" />
  			<input type="hidden" name="patient_id" value="${requestScope.patient.id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${prescribe_treatment}</button>
        </form>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_current_patient_page" />
  			<input type="hidden" name="patient_id" value="${requestScope.patient.id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${get_current_patient}</button>
        </form>       
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 float-right">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${surname}" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Alerts -->
	
	<c:if test="${param.message == 'diagnosis_absent'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${diagnosis_absent}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'discharged_successfully'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${discharged_success}"/>
		</div>
	</c:if>
	
	<h2>${patient.surname} ${patient.name}</h2> 	
	<h5><i>${patient.dateOfBirth}</i></h5>
	
	<!-- If the patient was successfully discharged, display the final diagnosis -->
	
	<c:if test="${param.message == 'discharged_successfully'}">
		<h5>${final_diagnosis}</h5>
		
		<table class="table table-bordered">
			<tr class="table-secondary">
				<th scope="col">${diagnosis_name}</th>
				<th scope="row">${param.diagnosis_name}</th>
			</tr>
		</table>
	</c:if>
	
	<!-- Display the dates of the last hospitalization -->
	
	<c:if test="${!empty hospitalization}">
		<h5>${last_hospitalization}</h5>
		
		<table class="table table-bordered">
			<thead>
				<tr class="table-secondary">
					<th scope="col">${medical_history_num}</th>
					<th scope="col">${entry_date}</th>
					<th scope="col">${discharge_date}</th>
				</tr>
			</thead>
			
			<tbody>
				<tr>
					<th scope="row">${hospitalization.idMedicalHistory}</th>
					<td>${hospitalization.entryDate}</td>	
					<td>${hospitalization.dischargeDate}</td>
				</tr>	
			</tbody>
		</table>
		
	</c:if>
	
	<!-- Forms for start and finish hospitalization -->
	
	<div class="d-inline-block form-bcground">
		<form name="start_hospitalization" action="font" method="POST">
			<input type="hidden" name="command" value="add_hospitalization" />
			<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>
			
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
	
	<div class="d-inline-block form-bcground">
		<form name="end_hospitalization" action="font" method="POST">
			<p><b>${end_hospitalization}</b></p>
				<input type="hidden" name="command" value="discharge_patient"/>
				<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>
				<input type="hidden" name="id_history" value="${requestScope.hospitalization.idMedicalHistory}"/>
				<input type="hidden" name="date_beginning" value="${requestScope.hospitalization.entryDate}"/>
				
				<div class="form-group">
  					<label for="date_finishing">${discharge_date}</label>
  					<input type="date" name="date_finishing" value="" >
				</div>
				
				<c:if test="${param.message == 'error_data_discharge'}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error_data_discharge}"/>
					</div>
				</c:if>
			
			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>

</body>
</html>