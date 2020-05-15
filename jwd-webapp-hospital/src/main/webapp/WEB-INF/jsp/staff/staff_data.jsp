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
	
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_patient" var="add_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.new_staff" var="add_staff" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.data.form_heading" var="form_heading" />
	<fmt:message bundle="${loc}" key="local.staff.data.specialty" var="specialty" />
	<fmt:message bundle="${loc}" key="local.staff.data.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.staff.data.nurse" var="nurse" />

	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>
<body>

    <!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="redirect_command" value="get_staff_data_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="redirect_command" value="get_staff_data_page"/> 
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
      		<input class="form-control mr-sm-2" type="search" name="query_string" 
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	<!-- Form for adding new staff -->
	
	<h3>${add_staff}:</h3>
	
	<div class="border border-secondary w-50 p-3" style="background-color: #eee;"> 
		<form name="staff_data" action="font" method="POST">
			<input type="hidden" name="command" value="add_new_staff" />
			<p><b>${form_heading}</b></p>
	
			<div class="form-group">
			<label for="specialty">${specialty}</label>
				<select name="specialty">
					<option value="врач">${doctor}</option>
					<option value="медсестра">${nurse}</option>
				</select>
			</div>
	
			<div class="form-group">
    		<label for="surname">${surname}</label>
    			<input type="text" name="surname" value="">
  			</div>
  		
  			<div class="form-group">
    			<label for="name">${name}</label>
    			<input type="text" name="name" value="">
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