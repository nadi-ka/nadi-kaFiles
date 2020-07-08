<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/jquery/jquery-ui.min.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="style/style.css"/>

	<title>Current-patient-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.staff.patient_data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.diagnosis_successfully" var="diagnosis_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.hospit_successfully" var="hospit_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.canceled_successfully" var="canceled_successfully"/>
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.make_diagnosis" var="make_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.prescribe_treatment" var="prescribe_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.perform_treatment" var="perform_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.nav_main" var="nav_main"/>
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
	<fmt:message bundle="${loc}" key="local.staff.current_patient.nav_hospitalization" var="set_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.no_current_treat" var="no_current_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.yes" var="yes" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.no" var="no" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.surgical" var="surgical" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.conservative" var="conservative" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.procedures" var="procedures" />
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.completed" var="completed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.canceled" var="canceled"/>
	<fmt:message bundle="${loc}" key="local.staff.current_patient.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.delete_question" var="delete_question" />
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.delete_btn" var="delete_btn" />
	
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
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
	
		<c:if test="${sessionScope.role == 'ADMINISTRATOR' or sessionScope.role == 'DOCTOR'}">
  		
  			<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_staff_main_page" />
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${add_patient}</button>
        	</form>
        
        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_hospitalization_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${set_hospitalization}</button>
        	</form>
        
        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_diagnosis_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${make_diagnosis}</button>
        	</form>

        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_prescriptions_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${prescribe_treatment}</button>
        	</form>
        
        </c:if>
        
        <c:if test="${sessionScope.role == 'NURSE'}">
        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_staff_main_page" />
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${nav_main}</button>
        	</form>
        </c:if>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_treat_performance_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${perform_treatment}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
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
	
	<c:if test="${param.message == 'canceled_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${canceled_successfully}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'no_current_treatment'}">
		<div class="alert alert-warning" role="alert">
			<c:out value="${no_current_treatment}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'error_data'}">
		<div class="alert alert-warning" role="alert">
			<c:out value="${error_data}"/>
		</div>
	</c:if>		
	
	<!-- Displaying of the current patient's data -->
	
		<!-- personal data -->
		
		<div class="container text-center">
			<h1>${patient.surname} ${patient.name}</h1>
		</div>
			
		<div class="container text-center w-75 p-3">
			<ul class="list-group">
  				<li class="list-group-item list-group-item-light">${date_of_birth} ${patient.dateOfBirth}</li>
  				<li class="list-group-item list-group-item-light">${e_mail} ${patient.email}</li>  
			</ul>
		</div>
			
			
		<!-- Accordion -->
		
		<div class="content">
			<div class="main">
			 	<div id="accordion">	      	
		
				<!-- hospitalizations -->
		
				<c:if test="${!empty patient.hospitalizations}">
				
				<h2>${hospitalization}</h2>
		
				<div>
					<table class="table table-bordered">
						<thead>
							<tr class="table-secondary">
								<th scope="col">${medical_history_num}</th>
								<th scope="col">${entry_date}</th>
								<th scope="col">${discharge_date}</th>
							</tr>
						</thead>
			
						<tbody>
							<c:forEach items="${patient.hospitalizations}" var="hospital">
								<tr class="table-secondary">
									<th scope="row">${hospital.idMedicalHistory}</th>
									<td>${hospital.entryDate}</td>	
									<td>${hospital.dischargeDate}</td>
								</tr>	
							</c:forEach>
						</tbody>
					</table>
				</div>
		
				</c:if>
		
				<!-- diagnosis -->
		
				<c:if test="${!empty patient.diagnosisList}">
		
				<h2>${diagnosis}</h2>
		
				<div>
		
					<ul class="list-group">
		
					<c:forEach items="${patient.diagnosisList}" var="patientDiagnosis">
						<li class="list-group-item list-group-item-secondary">
							${diagnosis} ${patientDiagnosis.diagnosisName}
							<ul class="list-group">
  								<li class="list-group-item">${setting_date} ${patientDiagnosis.settingDate}</li>
  								<li class="list-group-item">${is_primary}
  									<c:choose> 
										<c:when test="${patientDiagnosis.primary == 'true'}">
											<strong><c:out value="${yes}"/></strong>
										</c:when>
										<c:otherwise>
											<c:out value="${no}"/>
										</c:otherwise>
									</c:choose> 
  								</li>  
							</ul> 
						</li>
					</c:forEach>
		
					</ul>
		
				</div>
		
				</c:if>
		
				<!-- treatment -->
		
				<c:if test="${!empty patient.prescriptions}">
		
				<h2>${treatment_heading}</h2>
		
				<div>
					<ul class="list-group">
						<c:forEach items="${patient.prescriptions}" var="treatment">
							<li class="list-group-item list-group-item-secondary">
								
								${treatment_heading}:
									<c:choose> 
										<c:when test="${treatment.treatmentType == 'SURGICAL'}">
											<c:out value="${surgical} - ${treatment.treatmentName}"/>
										</c:when>
										<c:when test="${treatment.treatmentType == 'PROCEDURES'}">
											<c:out value="${procedures} - ${treatment.treatmentName}"/>
										</c:when>
											<c:otherwise>
												<c:out value="${conservative} - ${treatment.treatmentName}"/>
											</c:otherwise>
										</c:choose>
										
									<!-- Display 'cancel treatment' button or treatment status -->
									
									<c:choose> 
										<c:when test="${treatment.getTreatmentStatus() == 'COMPLETED'}">
											<p class='text-success'>${completed}</p>
										</c:when>
										<c:when test="${treatment.getTreatmentStatus() == 'CANCELED'}">
											<p class='text-danger'>${canceled}</p>
										</c:when>
										<c:otherwise>
										
										<!-- Form 'delete treatment' -->
										
										<c:if test="${sessionScope.role == 'ADMINISTRATOR' or sessionScope.role == 'DOCTOR'}">
									
										<form name="cancel_treatment" method="POST" action="font">
											<input type="hidden" name="command" value="cancel_treatment" />
											<input type="hidden" name="id_appointment" value="${treatment.idAppointment}" />
											<input type="hidden" name="patient_id" value="${patient.id}">
											<input type="hidden" name="current_status" value="${treatment.getTreatmentStatus().getStatusValue()}" />						   						
          					
          									<button id="delete_btn" type="submit" class="btn btn-primary">
          									<i class="fa fa-trash"></i>${delete_btn}</button>	 		
										</form>
										
										</c:if>
										
										</c:otherwise>
									</c:choose>
								
										<ul class="list-group">
  											<li class="list-group-item">${date_begin} ${treatment.dateBeginning}</li>
  											<li class="list-group-item">${date_end} ${treatment.dateFinishing}</li>
  											<li class="list-group-item">${doctor} ${treatment.doctorSurname} ${treatment.doctorName}</li> 
  											<li class="list-group-item">${consent}
  											<c:choose> 
												<c:when test="${treatment.consent == 'true'}">
													<c:out value="${yes}"/>
												</c:when>
												<c:otherwise>
													<c:out value="${no}"/>
												</c:otherwise>
											</c:choose>  
  										</li>   
									</ul> 
								</li>
							</c:forEach>
						</ul>
					</div>
		
				</c:if>
				
				</div>
			</div>
		</div>
		
		<!-- Footer -->
	
		<div id="footer">
    		<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
		</div>
		
		<script src="js/jquery-ui.min.js"></script>
		
		<script>
		
			$('#accordion').accordion({
			  	active: false,
			  	collapsible: true,
			  	icons : {
			    	header: 'ui-icon-circle-plus',
			    	activeHeader: 'ui-icon-circle-minus'
			  	},
			  	animate: false
			}); //end accordion
			
			$("#delete_btn").click(function(){
				var deleteQuestion = '<c:out value="${delete_question}"/>';
			    if (!confirm(deleteQuestion)){
			        return false;
			    }
			}); //end click
			
	
	</script>
		
</body>
</html>