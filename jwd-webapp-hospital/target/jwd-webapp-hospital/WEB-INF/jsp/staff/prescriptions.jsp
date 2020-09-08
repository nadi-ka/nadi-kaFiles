<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Prescriptions-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
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
	<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	
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
		<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>  
		<input type="hidden" name="redirect_command" value="get_prescriptions_page"/>     
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>  
		<input type="hidden" name="redirect_command" value="get_prescriptions_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<div id="staff_nav">
    	<jsp:include page="/WEB-INF/jsp/part/staff_nav.jsp"/>
	</div>
	
	<!-- Navigation menu -->
	
	<!-- Alerts -->
	
	<c:if test="${param.message == 'treatment_added_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${treatment_successfully} ${param.treatment_name}"/>
		</div>
	</c:if>
	
	<div class="container text-center">
		<h2>${patient.surname} ${patient.name}</h2> 	
		<h5><i>${patient.dateOfBirth}</i></h5>
	</div>
	
	<!-- Form for new treatment adding -->
	
	<div class="border border-secondary w-50 p-3 form-bcground ml-4">
	 
		<form id="prescriptions" name="prescriptions" action="font" method="POST">
			<p><b>${make_treatment}</b></p>
			<input type="hidden" name="command" value="add_new_treatment"/>
			<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>
			
			<div class="form-group">
				<label for="treatment_type">${treatment_type}</label>
				<select name="treatment_type">
					<option value="surgical">${surgical}</option>
					<option value="conservative">${conservative}</option>
					<option value="procedures">${procedures}</option>
				</select>
			</div>
			
			<div class="form-group">
    			<label for="treatment_name">${treatment_name}</label>
    			<input id="treatment_name" type="text" name="treatment_name" value="">
  			</div>
			
			<div class="form-group">
  				<label for="date_beginning">${date_beginning}</label>
  				<input id="date_beginning" type="date" name="date_beginning" value="" required>
			</div>
			
			<div class="form-group">
  				<label for="date_finishing">${date_finishing}</label>
  				<input id="date_finishing" type="date" name="date_finishing" value="" >
			</div>
			
			<c:if test="${param.message == 'error_data'}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error_data}: ${param.invalid_parameters}"/>
				</div>
			</c:if>
			
			<p class="text-info">${field_not_obvious}</p>

			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<hr/>
	
	<!-- Footer -->
		
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>
	
	<!-- Form validation -->
	
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/date_today.js"></script>
	
	<script>
	
		//Minimum value of date;
	
		document.getElementById("date_beginning").setAttribute("min", today);
		document.getElementById("date_finishing").setAttribute("min", today);
		
		//Required field;
		
		var requiredField = '<p class="text-danger">${field_required}</p>';

	 	$('#prescriptions').validate({
	 		
	   		rules: {	
	   			treatment_name: {
	        		required: true        
	     		}
	   		}, //end rules;
	   
	   		messages: {	
	   			treatment_name: {
	         		required: requiredField
	       		}
	   		} // end messages;

	  	}); // end validate;

	</script>

</body>
</html>