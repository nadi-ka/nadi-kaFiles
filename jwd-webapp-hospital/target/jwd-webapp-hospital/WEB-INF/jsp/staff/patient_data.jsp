<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Patient-data-page</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.staff.patient_data.added_successfully" var="added_successfully"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.patient_exists" var="patient_exists"/>
	<fmt:message bundle="${loc}" key="local.date_of_birth" var="date_of_birth"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.current_patient" var="current_patient"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.btn_add" var="anyway_add"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.patients_found" var="patients_found"/>
	<fmt:message bundle="${loc}" key="local.staff.patient_data.choose" var="choose"/>
	<fmt:message bundle="${loc}" key="local.email" var="e_mail"/>
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.nav_main" var="nav_main"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	
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
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
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
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>	
	
	<!-- Alert -->
	
	<c:if test="${requestScope.message == 'patients_found'}">
		<div class="alert alert-warning" role="alert">
			<c:out value="${patients_found}"/>
		</div>
	</c:if>
				
	<!-- Displaying of the new patient's personal data, if after 'add_patient' patients with the same surnames were found -->
	
	<c:if test="${param.message == 'patient_exists'}">
	
	<ul class="list-group">
		<li class="list-group-item list-group-item-primary">
			<p>${current_patient}</p>
			<ul class="list-group">
				<li class="list-group-item">
					<p>${param.surname} ${param.name}</p>
				</li>
				<li class="list-group-item">
					<p>${date_of_birth} ${param.date_of_birth}</p>
				</li>
				<li class="list-group-item">
					<p>${e_mail} ${param.email}</p>
				</li>
				<li class="list-group-item">
		
            <!-- Button, which obviously add current patient without checking, if such surname already exists -->
            
            	<form name="add_patient" action="font" method="POST">
					<input type="hidden" name="command" value="add_new_patient" />
					<input type="hidden" name="surname" value="${param.surname}" />
					<input type="hidden" name="name" value="${param.name}" />
					<input type="hidden" name="date_of_birth" value="${param.date_of_birth}" />
					<input type="hidden" name="email" value="${param.email}" />
					<input type="hidden" name="add_obviously" value="true" />
					<button type="submit"  class="btn btn-info">
						${anyway_add}
					</button>
				</form>
				
				</li>
			</ul>
		</li>
	</ul>
	
  	<div class="alert alert-primary" role="alert">
			<c:out value="${patient_exists}"/>
	</div>
	
	</c:if>
	
	<!-- Displaying of the List of patients with the same surnames -->
	
	<div class="container">
    	<div class="row">
    	
    		<div class="col-md-8 col-xl-8">
	
				<ul class="list-group">
					<c:forEach var="patient" items="${patients}">
		
						<li class="list-group-item w-75">
							<form action="font" method="GET">
								<input type="hidden" name="command" value="get_current_patient_page"/>
								<input type="hidden" name="patient_id" value="${patient.id}" />
								<button type="submit" class="btn btn-link">${patient.surname} ${patient.name} 
									${date_of_birth} ${patient.dateOfBirth}</button>
							</form>
						</li>
					</c:forEach>
				</ul>
	
			</div>
		
			<div class="col-md-4 col-xl-4">
				<img class="w-100 p-3" src="img/image_medicine.png" alt="medicine image" >
			</div>
	
		</div>
	</div>
	
	<hr/>
	
	<!-- Footer -->
		
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>
		
</body>
</html>	