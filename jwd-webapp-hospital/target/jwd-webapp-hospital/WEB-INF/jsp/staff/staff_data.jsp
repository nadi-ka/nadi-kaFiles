<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Staff-data-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.nav.main_page" var="nav_main" />
	<fmt:message bundle="${loc}" key="local.staff.data.add_staff" var="add_staff" />
	<fmt:message bundle="${loc}" key="local.staff.data.search_staff" var="search_staff" />
	<fmt:message bundle="${loc}" key="local.staff.data.staff_surname" var="staff_surname" />
	<fmt:message bundle="${loc}" key="local.staff.data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.data.was_added" var="added"/>
	<fmt:message bundle="${loc}" key="local.surname" var="surname" />
	<fmt:message bundle="${loc}" key="local.name" var="name" />
	<fmt:message bundle="${loc}" key="local.email" var="email" />
	<fmt:message bundle="${loc}" key="local.staff.data.staff_heading" var="staff_heading" />
	<fmt:message bundle="${loc}" key="local.staff.data.email_heading" var="email_heading" />
	<fmt:message bundle="${loc}" key="local.staff.data.update_personal_data" var="update_personal_data" />
	<fmt:message bundle="${loc}" key="local.staff.data.valid" var="valid" />
	<fmt:message bundle="${loc}" key="local.staff.data.not_valid" var="not_valid" />
	<fmt:message bundle="${loc}" key="local.staff.data.form_heading" var="form_heading" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.staff.data.specialty" var="specialty" />
	<fmt:message bundle="${loc}" key="local.staff.data.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.staff.data.nurse" var="nurse" />
	<fmt:message bundle="${loc}" key="local.staff.data.staff_found" var="staff_found" />
	<fmt:message bundle="${loc}" key="local.staff.main.not_found" var="not_found" />
	<fmt:message bundle="${loc}" key="local.staff.data.user_role_heading" var="user_role_heading" />
	<fmt:message bundle="${loc}" key="local.staff.data.administrator" var="administrator" />
	<fmt:message bundle="${loc}" key="local.staff.data.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.staff.data.change_role" var="change_role" />
	<fmt:message bundle="${loc}" key="local.staff.data.user_status_heading" var="user_status_heading" />
	<fmt:message bundle="${loc}" key="local.staff.data.change_status" var="change_status" />
	<fmt:message bundle="${loc}" key="local.staff.data.current_role" var="current_role" />
	<fmt:message bundle="${loc}" key="local.staff.data.current_status" var="current_status" />
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
		<input type="hidden" name="redirect_command" value="get_staff_data_page">   
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_staff_data_page">  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
  		<form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${nav_main}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
        	<input type="hidden" name="command" value="search_staff"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="${staff_surname}" aria-label="Search_staff">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_staff}</button>
    	</form>

	</nav>
	
	<!-- Alerts -->
	
	<div class="alert alert-warning" role="alert">
  		<c:if test="${param.message == 'added_successfully'}">
			<c:out value="${added_successfully}"/>
		</c:if>
	</div>
	
  	<c:if test="${param.message == 'not_found'}">
  		<div class="alert alert-warning" role="alert">
			<c:out value="${not_found}"/>
		</div>
	</c:if>
	
	<!-- Displaying of the new added staff data -->

	<c:if test="${!empty medical_staff}">
		<h5>${added}</h5>
		<li class="list-group-item list-group-item-secondary">
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
  				<li class="list-group-item">${email} ${medical_staff.email}</li>  
			</ul>         	
		</li>
	</c:if>
	
	<!-- Staff found after search command -->
	
	<c:if test="${!empty staff_list}">
		<h5>${staff_found}</h5>

		<table class="table table-bordered">
			
			<thead>
				<tr>
					<th scope="col">${staff_heading}</th>
					<th scope="col">${specialty}</th>
					<th scope="col">${email_heading}</th>
					<th scope="col">${user_role_heading}</th>
					<th scope="col">${user_status_heading}</th>
				</tr>
			</thead>
			
			<tbody>
			
			<c:forEach var="staff" items="${staff_list}">
			
				<tr>
					<th scope="row">${staff.surname} ${staff.name}</th>
					<td>
						<c:choose> 
							<c:when test="${staff.specialty == 'DOCTOR'}">
								<p>${doctor}</p>
							</c:when>
							<c:otherwise>
								<p>${nurse}</p>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${staff.email}</td>	
					<td>
					
					<!-- Display current user role -->
						<p>${current_role}
							<c:choose> 
								<c:when test="${staff.getRole() eq 'DOCTOR'}">
									<strong><c:out value="${doctor}"/></strong>
								</c:when>
								<c:otherwise>
									<strong><c:out value="${administrator}"/></strong>
								</c:otherwise>
							</c:choose>
						</p>
						
						<!-- Form update user role -->  
												
						<form action="font" method="POST">
							<input type="hidden" name="command" value="update_user_role"/>
							<input type="hidden" name="staff_id" value="${staff.id}"/>
							<input type="hidden" name="query_search" value="${requestScope.query_search}"/>
							
							<div class="form-check">
          						<input class="form-check-input" type="radio" name="user_role" value='DOCTOR' checked>
          						<label class="form-check-label">${doctor}</label>
          					</div>
							
							<div class="form-check">
          						<input class="form-check-input" type="radio" name="user_role" value='ADMINISTRATOR'>
          						<label class="form-check-label">${administrator}</label>
        					</div>
						
							<button type="submit" class="btn btn-secondary">${change_role}</button>
						</form>
					</td>
					
					<td>
					
						<!-- Display current user status -->
						<p>${current_status}
							<c:choose> 
								<c:when test="${staff.userStatus == true}">
									<strong><c:out value="${valid}"/></strong>
								</c:when>
								<c:otherwise>
									<strong><c:out value="${not_valid}"/></strong>
								</c:otherwise>
							</c:choose>
						</p> 
						
						<!-- Form update user status -->
						
						<form action="font" method="POST">
							<input type="hidden" name="command" value="update_user_status"/>
							<input type="hidden" name="staff_id" value="${staff.id}"/>
							<input type="hidden" name="query_search" value="${requestScope.query_search}"/>
							
							<div class="form-check">
          						<input class="form-check-input" type="radio" name="user_status" value='true' checked>
          						<label class="form-check-label">${valid}</label>
        					</div>
        					
        					<div class="form-check">
          						<input class="form-check-input" type="radio" name="user_status" value='false'>
          						<label class="form-check-label">${not_valid}</label>
          					</div>
							
							<button type="submit" class="btn btn-secondary">${change_status}</button>
						</form>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
					
	<!-- Form for adding new staff -->
	
	<div class="ml-4">
		<h3>${add_staff}</h3>
	</div>
	
	<div class="border border-secondary w-50 p-3 form-bcground ml-4"> 
		<form id="add_staff" name="staff_data" action="font" method="POST">
			<input type="hidden" name="command" value="add_new_staff" />
			<p><b>${form_heading}</b></p>
	
			<div class="form-group">
			<label for="specialty">${specialty}</label>
				<select name="specialty">
					<option value="doctor">${doctor}</option>
					<option value="nurse">${nurse}</option>
				</select>
			</div>
	
			<div class="form-group">
    		<label for="surname">${surname}:</label>
    			<input type="text" name="surname" value="" class="required" id="surname">
  			</div>
  		
  			<div class="form-group">
    			<label for="name">${name}</label>
    			<input type="text" name="name" value="" class="required" id="name">
  			</div>
	
  			<div class="form-group">
  				<label for="email">${email}</label>
    			<input type="email" name="email" value="" required>
  			</div>
  		
  			<c:if test="${param.message == 'error_data'}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error_data}: ${param.invalid_parameters}"/>
				</div>
			</c:if>

			<button type="submit" class="btn btn-primary" name="btn_signup">${submit_btn}</button>

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

	 	$('#add_staff').validate({
	 		
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