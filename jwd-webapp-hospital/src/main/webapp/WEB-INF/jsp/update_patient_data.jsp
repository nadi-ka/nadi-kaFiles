<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	
	<title>Patient-data-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.nav_main" var="navigate_main" />
	<fmt:message bundle="${loc}" key="local.main.calc_hospitalization" var="calc_hospitalization" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.update_patient.update_heading" var="update_heading" />
	<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>

	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn" />
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
		<input type="hidden" name="redirect_command" value="get_update_patient_data_page">   
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_update_patient_data_page">  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
  		<form class="form-inline" name="navigate_main" action="font" method="GET">
  			<input type="hidden" name="command" value="get_patient_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${navigate_main}</button>
        </form>
        
        <form class="form-inline" name="get_treatment" action="font" method="GET">
			<input type="hidden" name="command" value="get_treatment_page" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${show_treat}</button>
		</form>
		
		<form class="form-inline" name="calc_hospitalization" method="GET" action="font">
			<input type="hidden" name="command" value="get_hospitalization_plan" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${calc_hospitalization}</button>
		</form>

	</nav>
	
	<!-- Displaying of the current personal data -->

	<c:if test="${!empty patient}">
		<li class="list-group-item list-group-item-warning">
			<h3>${patient.surname} ${patient.name}</h3>  
			<ul class="list-group">
  				<li class="list-group-item">${date_of_birth} ${patient.dateOfBirth}</li>
  				<li class="list-group-item">${email} ${patient.email}</li>  
			</ul>         	
		</li>
	</c:if>
	
	<!-- Form update staff personal data -->
	
	<div class="ml-4">
		<h3>${update_heading}</h3>
	</div>
	
		<div class="border border-secondary w-50 p-3 form-bcground ml-4"> 
			<form id="update" name="update_patient_data" action="font" method="POST">
				<input type="hidden" name="command" value="update_patient_data" />
	
				<div class="form-group">
    				<label for="surname">${surname}:</label>
    				<input type="text"  name="surname" value="${requestScope.patient.surname}" class="required" id="surname">
  				</div>
  		
  				<div class="form-group">
    				<label for="name">${name}</label>
    				<input type="text" name="name" value="${requestScope.patient.name}" class="required" id="name">
  				</div>
  				
  				<div class="form-group">
  					<label for="date">${date_of_birth}</label>
  					<input type="date" name="date_of_birth" value="${requestScope.patient.dateOfBirth}" id="birthdate" required>
				</div>
	
  				<div class="form-group">
  					<label for="email">${email}</label>
  					<input type="hidden" name="old_email" value="${requestScope.patient.email}" />
    				<input type="email" name="email" value="${requestScope.patient.email}" class="required" id="email">
  				</div>
  		
  				<c:if test="${param.message == 'error_data'}">
						<div class="alert alert-danger" role="alert">
							<c:out value="${error_data}: ${param.invalid_parameters}"/>
						</div>
				</c:if>

				<button id="done" type="submit" class="btn btn-primary" name="btn_signup">${submit_btn}</button>

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
	
		//Maximum value of birthdate;
	
		document.getElementById("birthdate").setAttribute("max", today);
		
		//Required fields;
		
		var requiredField = '<p class="text-danger">${field_required}</p>';

	 	$('#update').validate({
	 		
	   		rules: {	
	   			surname: {
	        		required: true        
	     		},
	     		name: {
	        		required: true        
	     		},
	     		email: {
	     			required: true
	     		}
	   		}, //end rules;
	   
	   		messages: {	
	   			surname: {
	         		required: requiredField
	       		},
	       		name: {
	         		required: requiredField
	       		},
	       		email: {
	      			required: requiredField
	      		}
	   		} // end messages;

	  	}); // end validate;

	</script>
		
	</body>
</html>