<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Staff-main-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.staff.main.welcome" var="welcome" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_staff" var="add_staff" />
	<fmt:message bundle="${loc}" key="local.main.update_personal_data" var="update_personal_data" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth" />
	<fmt:message bundle="${loc}" key="local.staff.main.patients_data" var="patients_data" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.main.not_found" var="not_found" />
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>
	<fmt:message bundle="${loc}" key="local.validation.field_length" var="field_length"/>
	<fmt:message bundle="${loc}" key="local.validation.symbols_signes" var="symbols_signes"/>
	
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
		<input type="hidden" name="redirect_command" value="get_staff_main_page"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_staff_main_page"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
	
		<!-- Next button will be visible only for user-administrator -->
	
		<c:if test="${sessionScope.role == 'ADMINISTRATOR'}">	
  			<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_staff_data_page" />
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${add_staff}</button>
        	</form>
    	</c:if>
    	
    	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_update_personal_data_page" />
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${update_personal_data}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${surname}" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<div class="container text-center">
		<h1>${welcome}</h1>
	</div>
	
	<!-- Alert -->
	
  	<c:if test="${param.message == 'not_found'}">
  		<div class="alert alert-light" role="alert">
			<c:out value="${not_found}"/>
		</div>
	</c:if>
	
	<div class="container">
    	<div class="row">
    	
    	<!-- Form for adding new patient -->
	
		<c:if test="${sessionScope.role == 'ADMINISTRATOR' or sessionScope.role == 'DOCTOR'}">
	
    		<div class="col-md-8 col-xl-8">
	
				<div class="border border-secondary w-50 p-3 form-bcground">
					<form id="add_patient" name="patients_data" action="font" method="POST">
						<input type="hidden" name="command" value="add_new_patient" />
						<p><b>${patients_data}</b></p>
	
						<div class="form-group">
    						<label for="surname">${surname}:</label>
    						<input type="text" name="surname" value="" class="required" id="surname">
  						</div>
  		
  						<div class="form-group">
    						<label for="name">${name}</label>
    						<input type="text" name="name" value="" class="required" id="name">
  						</div>
  		
  						<div class="form-group">
  							<label for="date">${date_of_birth}</label>
  							<input type="date" name="date_of_birth" value="" id="birthdate" required>
						</div>
	
  						<div class="form-group">
  							<label for="email">${email}</label>
    						<input type="email" name="email" value="" class="required" id="email">
  						</div>
  		
  						<c:if test="${param.message == 'error_data'}">
							<div class="alert alert-danger" role="alert">
								<c:out value="${error_data}"/>
							</div>
						</c:if>

						<button type="submit" class="btn btn-primary" name="btn_signup">${submit_btn}</button>
					</form>
				</div>
	
			</div>
	
		</c:if>
	
	 	<div class="col-md-4 col-xl-4">
			<img src="img/staff.png" class="img-thumbnail" alt="medical_staff">
		</div>
	
		</div>
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
	var personalDataMessage = '<p class="text-danger">${field_length} 2-45 ${symbols_signes}</p>';

 	$('#add_patient').validate({
 		
   		rules: {
     		surname: {
        		required: true,
        		pattern: /^([\\p{L}]|[-]){2,45}$/
     		},
     		
     		name: {
        		required: true,
        		pattern: /^([\\p{L}]|[-]){2,45}$/
     		},
     		
     		email: {
     			required: true
     		}
   		}, //end rules;
   
   		messages: {
   			
      		surname: {
         		required: requiredField,
         		pattern: personalDataMessage
       		},
       		
      		name: {
      			required: requiredField,
      			pattern: personalDataMessage
      		},
      		
      		email: {
      			required: requiredField
      		}
   		} // end messages;

  	}); // end validate; 

</script>
  		
</body>
</html>