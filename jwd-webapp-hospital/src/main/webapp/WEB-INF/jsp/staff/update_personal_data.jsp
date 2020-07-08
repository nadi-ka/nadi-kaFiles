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
	
	<title>Staff-data-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.nav_main" var="nav_main"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.staff.data.update_staff" var="update_staff" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.staff.data.specialty" var="specialty" />
	<fmt:message bundle="${loc}" key="local.staff.data.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.staff.data.nurse" var="nurse" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
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
		<input type="hidden" name="redirect_command" value="get_update_personal_data_page">   
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_update_personal_data_page">  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
  		<form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${nav_main}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${surname}" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Displaying of the current personal data -->

	<c:if test="${!empty medical_staff}">
		<li class="list-group-item list-group-item-warning">
			<h3>${medical_staff.surname} ${medical_staff.name}</h3>  
			<ul class="list-group">
  				<li class="list-group-item">
  					<c:choose> 
						<c:when test="${medical_staff.specialty == 'DOCTOR'}">
							<p>${specialty} ${doctor}</p>
						</c:when>
						<c:otherwise>
							<p>${specialty} ${nurse}</p>
						</c:otherwise>
					</c:choose>
  				</li>
  				<li class="list-group-item">${email} ${medical_staff.email}</li>  
			</ul>         	
		</li>
	</c:if>
	
	<!-- Form update staff personal data -->
	
		<div class="ml-4">
			<h3>${update_staff}:</h3>
		</div>
	
		<div class="border border-secondary w-50 p-3 form-bcground ml-4"> 
			<form id="update" name="update_staff_data" action="font" method="POST">
				<input type="hidden" name="command" value="update_personal_data" />
	
				<div class="form-group">
    				<label for="surname">${surname}:</label>
    				<input type="text"  name="surname" value="${requestScope.medical_staff.surname}" class="required" id="surname">
  				</div>
  		
  				<div class="form-group">
    				<label for="name">${name}</label>
    				<input type="text" name="name" value="${requestScope.medical_staff.name}" class="required" id="name">
  				</div>
	
  				<div class="form-group">
  					<label for="email">${email}</label>
  					<input type="hidden" name="old_email" value="${requestScope.medical_staff.email}" />
    				<input type="email" name="email" value="${requestScope.medical_staff.email}" id="email" required>
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
	
		<script>
		
		var requiredField = '<p class="text-danger">${field_required}</p>';

	 	$('#update').validate({
	 		
	   		rules: {	
	   			surname: {
	        		required: true        
	     		},
	     		name: {
	        		required: true        
	     		}
	   		}, //end rules;
	   
	   		messages: {	
	   			surname: {
	         		required: requiredField
	       		},
	       		name: {
	         		required: requiredField
	       		}
	   		} // end messages;

	  	}); // end validate;

	</script>
		
	</body>
</html>	