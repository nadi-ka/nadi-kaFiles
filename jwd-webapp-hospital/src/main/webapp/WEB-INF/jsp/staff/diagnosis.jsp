<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Diagnosis-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />

	<fmt:message bundle="${loc}" key="local.staff.diagnosis.choose_from" var="choose_from"/>
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.choose_main" var="choose_main_diagnosis"/>
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.choose_secondary" var="choose_secondary_diagnosis"/>
	<fmt:message bundle="${loc}" key="local.staff.setting_date" var="setting_date"/>
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.nothing_chosen" var="nothing_chosen"/>
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.nothing_selected" var="nothing_selected" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.prescribe_treatment" var="prescribe_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.nav_hospitalization" var="set_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.get_current" var="get_current_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.missing_diagnosis" var="missing_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.add_here" var="add_here" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.enter_diagnosis" var="enter_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.code" var="code" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.name" var="diagnosis_name" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.bed_days" var="bed_days" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.not_obvious" var="field_not_obvious" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.diagnosis_successfully" var="diagnosis_successfully"/>
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
		<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>  
		<input type="hidden" name="redirect_command" value="get_diagnosis_page"/>
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>  
		<input type="hidden" name="redirect_command" value="get_diagnosis_page"/> 
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
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
  			<input type="hidden" name="command" value="get_prescriptions_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${prescribe_treatment}</button>
        </form>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_current_patient_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${get_current_patient}</button>
        </form>        
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 float-right">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Alerts -->
	
	<c:if test="${param.message == 'nothing_chosen'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${nothing_chosen}"/>
		</div>
	</c:if>
	
	
	<c:if test="${param.message == 'error_data'}">
		<div class="alert alert-danger" role="alert">
			<c:out value="${error_data}"/>
		</div>
	</c:if>
	
	<!-- Form with possible diagnosis to choose from -->
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;"> 
		<form name="patients_diagnosis" action="font" method="POST">
			<p><b>${choose_from}</b></p>
			<input type="hidden" name="command" value="add_patient_diagnosis"/>
			<input type="hidden" name="patient_id" value="${patient_id}"/>
			
			<div class="form-group">
				<label for="primary_diagnosis">${choose_main_diagnosis}</label>
				<select name="primary_diagnosis">
					<option value="NONE">${nothing_selected}</option>
					<c:forEach items="${diagnosis_list}" var="diagnosis">
						<option value="${diagnosis.codeByICD}">${diagnosis.diseaseName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="form-group">
				<label for="secondary_diagnosis">${choose_secondary_diagnosis}:</label>
				<select name="secondary_diagnosis" multiple="multiple">
					<option value="NONE">${nothing_selected}</option>
					<c:forEach items="${diagnosis_list}" var="diagnosis">
						<option value="${diagnosis.codeByICD}">${diagnosis.diseaseName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="form-group">
  				<label for="date">${setting_date}</label>
  				<input type="date" name="setting_date" value="" >
			</div>

			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<div class="p-3 mb-2 bg-white text-dark">
   			<h5>${missing_diagnosis}</h5>
			<p>${add_here}</p>
	</div>
	
	<!-- Form for adding new diagnosis to the list of possible diagnosis -->
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;">
	 
		<form name="diagnosis" action="font" method="POST">
			<p><b>${enter_diagnosis}</b></p>
			<input type="hidden" name="command" value="add_new_diagnosis"/>
			<input type="hidden" name="patient_id" value="${patient_id}"/>
			
			<div class="form-group">
				<label for="code">${code}</label>
				<input type="text" class="form-control" style="width: 60%" name="code" value="">
			</div>
			
			<div class="form-group">
				<label for="diagnosis_name">${diagnosis_name}</label>
				<input type="text" class="form-control" style="width: 60%" name="diagnosis_name" value="">
			</div>
			
			<div class="form-group">
				<label for="bed_days">${bed_days}</label>
				<input type="text" class="form-control" style="width: 60%" name="bed_days" value="">
			</div>
			
			<div class="alert alert-primary" role="alert">
				<c:if test="${param.message == 'diagnosis_added_successfully'}">
					<c:out value="${diagnosis_successfully}"/>
				</c:if>
			</div>

			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<p class="text-info">${field_not_obvious}</p>

</body>
</html>