<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Staff-main-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.staff.main.welcome" var="welcome" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_staff" var="add_staff" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth" />
	<fmt:message bundle="${loc}" key="local.staff.main.patients_data" var="patients_data" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.main.not_found" var="not_found" />
	
	<fmt:message bundle="${loc}" key="local.staff.main.button.add" var="submit_btn" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

    <!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_staff_main_page"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_staff_main_page"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	
		<!-- Next button will be visible only for user-administrator -->
	
		<c:if test="${sessionScope.role == 'ADMINISTRATOR'}">	
  			<form class="form-inline" action="font" method="post">
  				<input type="hidden" name="command" value="get_staff_data_page" />
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${add_staff}</button>
        	</form>
    	</c:if>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${surname}" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<h1>${welcome}</h1>
	
  	<c:if test="${param.message == 'not_found'}">
  		<div class="alert alert-light" role="alert">
			<c:out value="${not_found}"/>
		</div>
	</c:if>
	
	<!-- Form for adding new patient -->
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;">
	
		<form name="patients_data" action="font" method="POST">
			<input type="hidden" name="command" value="add_new_patient" />
			<p><b>${patients_data}</b></p>
	
				<div class="form-group">
    				<label for="surname">${surname}:</label>
    				<input type="text" name="surname" value="">
  				</div>
  		
  				<div class="form-group">
    				<label for="name">${name}</label>
    				<input type="text" name="name" value="">
  				</div>
  		
  				<div class="form-group">
  					<label for="date">${date_of_birth}</label>
  					<input type="date" name="date_of_birth" value="" >
				</div>
	
  				<div class="form-group">
  					<label for="email">${email}</label>
    				<input type="email" name="email" value="" >
  				</div>
  		
  				<c:if test="${param.message == 'error_data'}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error_data}"/>
					</div>
				</c:if>

			<button type="submit" class="btn btn-primary" name="btn_signup">${submit_btn}</button>
		</form>
	</div>
  		
</body>
</html>