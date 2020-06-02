<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Prescriptions-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.make_treatment" var="make_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.type" var="treatment_type" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.name" var="treatment_name" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.surgical" var="surgical" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.conservative" var="conservative" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.procedures" var="procedures" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.beginning" var="date_beginning" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.finishing" var="date_finishing" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.not_obvious" var="field_not_obvious" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.treatment_successfully" var="treatment_successfully"/>
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	
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
		<input type="hidden" name="redirect_command" value="get_prescriptions_page"/>     
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient_id}"/>  
		<input type="hidden" name="redirect_command" value="get_prescriptions_page"/>    
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
	
	<!-- Alerts -->
	
	<c:if test="${param.message == 'treatment_added_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${treatment_successfully}"/>
		</div>
	</c:if>
	
	<!-- Displaying of the form for new treatment adding -->
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;">
	 
		<form name="prescriptions" action="font" method="POST">
			<p><b>${make_treatment}</b></p>
			<input type="hidden" name="command" value="add_new_treatment"/>
			<input type="hidden" name="patient_id" value="${patient_id}"/>
			
			<div class="form-group">
				<label for="treatment_type">${treatment_type}</label>
				<select name="treatment_type">
					<option value="хирургическое">${surgical}</option>
					<option value="консервативное">${conservative}</option>
					<option value="процедуры">${procedures}</option>
				</select>
			</div>
			
			<div class="form-group">
    			<label for="treatment_name">${treatment_name}</label>
    			<input type="text" name="treatment_name" value="">
  			</div>
			
			<div class="form-group">
  				<label for="date_beginning">${date_beginning}</label>
  				<input type="date" name="date_beginning" value="" >
			</div>
			
			<div class="form-group">
  				<label for="date_finishing">${date_finishing}</label>
  				<input type="date" name="date_finishing" value="" >
			</div>
			
			<c:if test="${param.message == 'error_data'}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error_data}"/>
				</div>
			</c:if>

			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<p class="text-info">${field_not_obvious}</p>

</body>
</html>