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
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.staff.patient_data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.diagnosis_successfully" var="diagnosis_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.hospit_successfully" var="hospit_successfully"/>
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.data" var="current_patient"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.make_diagnosis" var="make_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.prescribe_treatment" var="prescribe_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.treatment" var="treatment" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.date_begin" var="date_begin" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.date_end" var="date_end" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.consent" var="consent" />
	<fmt:message bundle="${loc}" key="local.email" var="e_mail" />
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.diagnosis" var="diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.treatment" var="treatment_heading" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.setting_date" var="setting_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.is_primary" var="is_primary" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.hospitalization" var="hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.med_history" var="medical_history_num" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.entry_date" var="entry_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.discharge_date" var="discharge_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.hospitalization" var="set_hospitalization" />
	
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
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  		
  		<form class="form-inline" action="font" method="post">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${add_patient}</button>
        </form>
        
        <form class="form-inline" action="font" method="post">
  			<input type="hidden" name="command" value="get_diagnosis_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${make_diagnosis}</button>
        </form>

        <form class="form-inline" action="font" method="post">
  			<input type="hidden" name="command" value="get_prescriptions_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${prescribe_treatment}</button>
        </form>
        
        <form class="form-inline" action="font" method="post">
  			<input type="hidden" name="command" value="get_hospitalization_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${set_hospitalization}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Alerts -->
	
  	<c:if test="${param.message == 'added_successfully'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${added_successfully}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'diagnosis_added_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${diagnosis_successfully}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'hospit_added_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${hospit_successfully}"/>
		</div>
	</c:if>	
	
	<!-- Displaying of the current patient's data -->
	
	<h2>${current_patient}</h2>
	
		<!-- personal data -->
		
		<div class="p-3 mb-2 bg-success text-white">${patient.surname} ${patient.name}</div>
			<ul class="list-group">
  				<li class="list-group-item list-group-item-light">${date_of_birth} ${patient.dateOfBirth}</li>
  				<li class="list-group-item list-group-item-light">${e_mail} ${patient.email}</li>  
			</ul>         	
		
		<!-- hospitalizations -->
		
		<c:if test="${!empty patient.hospitalizations}">
		<h3>${hospitalization}</h3>
		
		<table class="table table-bordered">
			<thead>
				<tr>
					<th scope="col">${medical_history_num}</th>
					<th scope="col">${entry_date}</th>
					<th scope="col">${discharge_date}</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${patient.hospitalizations}" var="hospital">
					<tr>
						<th scope="row">${hospital.idMedicalHistory}</th>
						<td>${hospital.entryDate}</td>	
						<td>${hospital.dischargeDate}</td>
					</tr>	
				</c:forEach>
			</tbody>
		</table>
		
		</c:if>
		
		<!-- diagnosis -->
		
		<ul class="list-group">
		
		<c:if test="${!empty patient.diagnosisList}">
		
		<h3>${diagnosis}</h3>
		
		<c:forEach items="${patient.diagnosisList}" var="patientDiagnosis">
			<li class="list-group-item list-group-item-secondary">
				${diagnosis} ${patientDiagnosis.diagnosisName}
				<ul class="list-group">
  					<li class="list-group-item">${setting_date} ${patientDiagnosis.settingDate}</li>
  					<li class="list-group-item">${is_primary} ${patientDiagnosis.primary}</li>  
				</ul> 
			</li>
		</c:forEach>
		
		</c:if>
		
		<!-- treatment -->
		
		<c:if test="${!empty patient.prescriptions}">
		
		<h3>${treatment_heading}</h3>
		
		<c:forEach items="${patient.prescriptions}" var="treatment">
			<li class="list-group-item list-group-item-secondary">
				${treatment_heading} ${treatment.treatmentType} ${treatment.treatmentName}
				<ul class="list-group">
  					<li class="list-group-item">${date_begin} ${treatment.dateBeginning}</li>
  					<li class="list-group-item">${date_end} ${treatment.dateFinishing}</li>
  					<li class="list-group-item">${doctor} ${treatment.doctorSurname} ${treatment.doctorName}</li> 
  					<li class="list-group-item">${consent} ${treatment.consent}</li>   
				</ul> 
			</li>
		</c:forEach>
		
		</c:if>		
		 
	</ul>	
	
</body>
</html>