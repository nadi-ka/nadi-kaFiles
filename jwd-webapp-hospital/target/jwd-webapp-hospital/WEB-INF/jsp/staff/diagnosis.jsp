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
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.invalid_date" var="invalid_date"/>
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.nothing_selected" var="nothing_selected" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.missing_diagnosis" var="missing_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.add_here" var="add_here" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.enter_diagnosis" var="enter_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.code" var="code" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.name" var="diagnosis_name" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.bed_days" var="bed_days" />
	<fmt:message bundle="${loc}" key="local.staff.diagnosis.not_obvious" var="field_not_obvious" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.diagnosis_successfully" var="diagnosis_successfully"/>
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
		<input type="hidden" name="redirect_command" value="get_diagnosis_page"/>
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>  
		<input type="hidden" name="redirect_command" value="get_diagnosis_page"/> 
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<div id="staff_nav">
    	<jsp:include page="/WEB-INF/jsp/part/staff_nav.jsp"/>
	</div>
	
	<!-- Navigation menu -->
	
	<!-- Alerts -->
	
	<c:if test="${param.message == 'nothing_chosen'}">
  		<div class="alert alert-primary" role="alert">
			<c:out value="${nothing_chosen}"/>
		</div>
	</c:if>	
	
	<c:if test="${param.message == 'error_data'}">
		<div class="alert alert-danger" role="alert">
			<c:out value="${error_data} ${param.invalid_parameters}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'invalid_date'}">
		<div class="alert alert-danger" role="alert">
			<c:out value="${invalid_date}"/>
		</div>
	</c:if>
	
	<c:if test="${param.message == 'diagnosis_added_successfully'}">
		<div class="alert alert-primary" role="alert">
			<c:out value="${diagnosis_successfully}"/>
		</div>
	</c:if>
	
	<div class="container text-center">
		<h2>${patient.surname} ${patient.name}</h2> 	
		<h5><i>${patient.dateOfBirth}</i></h5>
	</div>
	
	<!-- Form with possible diagnosis to choose from -->
	
	<div class="border border-secondary w-50 p-3 form-bcground ml-4"> 
		<form name="patients_diagnosis" action="font" method="POST">
			<p><b>${choose_from}</b></p>
			<input type="hidden" name="command" value="add_patient_diagnosis"/>
			<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>
			
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
  				<input id="setting_date" type="date" name="setting_date" value="" required>
			</div>

			<button type="submit"  class="btn btn-primary">${submit_btn}</button>
		</form>
	</div>
	
	<div class="p-3 mb-2 bg-white text-dark">
   			<h5>${missing_diagnosis}</h5>
			<p>${add_here}</p>
	</div>
	
	<!-- Form for adding new diagnosis to the list of possible diagnosis -->
	
	<div class="border border-secondary w-50 p-3 form-bcground ml-4">
	 
		<form id="new_diagnosis_form" name="diagnosis" action="font" method="POST">
			<p><b>${enter_diagnosis}</b></p>
			<input type="hidden" name="command" value="add_new_diagnosis"/>
			<input type="hidden" name="patient_id" value="${requestScope.patient.id}"/>
			
			<div class="form-group">
				<label for="code">${code}</label>
				<input type="text" name="code" value="" class="required" id="code">
			</div>
			
			<div class="form-group">
				<label for="diagnosis_name">${diagnosis_name}</label>
				<input type="text" name="diagnosis_name" value="" class="required" id="diagnosis_name">
			</div>
			
			<div class="form-group">
				<label for="bed_days">${bed_days}</label>
				<input type="text" name="bed_days" value="">
			</div>
			
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
	
		//Minimum value of date for the first form;
	
		document.getElementById("setting_date").setAttribute("min", today);
		
		//The second form validation;
		
		var requiredField = '<p class="text-danger">${field_required}</p>';

	 	$('#new_diagnosis_form').validate({
	 		
	   		rules: {	
	   			code: {
	        		required: true        
	     		},
	     		
	     		diagnosis_name: {
	        		required: true	
	     		}
	   		}, //end rules;
	   
	   		messages: {	
	      		code: {
	         		required: requiredField
	       		},
	       		
	       		diagnosis_name: {
	      			required: requiredField
	      		}
	   		} // end messages;

	  	}); // end validate ;

	</script>

</body>
</html>