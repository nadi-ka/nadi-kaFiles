<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Current-patient-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.nav_main" var="nav_main"/>
	<fmt:message bundle="${loc}" key="local.staff.main.button.get_current" var="get_current_patient" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	<fmt:message bundle="${loc}" key="local.treatment" var="treat" />
	<fmt:message bundle="${loc}" key="local.treatment.type" var="type" />
	<fmt:message bundle="${loc}" key="local.treatment.name" var="name" />
	<fmt:message bundle="${loc}" key="local.treatment.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.treatment.datebegin" var="date_begin" />
	<fmt:message bundle="${loc}" key="local.treatment.datefinish" var="date_finish" />
	<fmt:message bundle="${loc}" key="local.treatment.consent" var="consent" />
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_btn"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.executions" var="executions"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.form_name" var="form_name"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.in_progress" var="in_progress"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.completed" var="completed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.performed" var="performed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.date" var="date"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.performer" var="performer"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.status" var="status"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.access_denied" var="access_denied"/>
	<fmt:message bundle="${loc}" key="local.error_data" var="error_data"/>
	<fmt:message bundle="${loc}" key="local.treatment.consent.true" var="agree" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.false" var="disagree" />
	
	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>
<body>

	<!-- Logout button -->
		
	<form name="Logout_form" method="POST" action="register" class="float-right">
		<input type="hidden" name="command" value="logout" /> 
		<button type="submit" class="btn btn-link">${logout_button}</button>
	</form>

	<!-- Change language buttons -->

	<form action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="font" method="POST">
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
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_current_patient_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${get_current_patient}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>	
	
	<!--  Table 'Treatment' -->

		<table class="table table-bordered">
			<caption>${treat}</caption>
			
			<thead>
				<tr>
					<th scope="col">${name}</th>
					<th scope="col">${type}</th>
					<th scope="col">${doctor}</th>
					<th scope="col">${date_begin}</th>
					<th scope="col">${date_finish}</th>
					<th scope="col">${consent}</th>
					<th scope="col" style="width:40%">${executions}</th>
				</tr>
			</thead>
			
			<tbody>
			
			<c:forEach var="treatment" items="${prescriptions}">
			
				<tr>
					<th scope="row">${treatment.treatmentName}</th>
					<td>${treatment.treatmentType}</td>
					<td>${treatment.doctorSurname}<br> 
						${treatment.doctorName}</td>	
					<td>${treatment.dateBeginning}</td>
					<td>${treatment.dateFinishing}</td>
					<td>
						<c:choose> 
							<c:when test="${treatment.consent == 'true'}">
								<c:out value="${agree}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${disagree}"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					
						<c:if test="${treatment.consent == 'true'}">	
						
						<!-- 'Perform treatment' form -->
						
						<h5>${form_name}</h5>
						
						<div class="border border-secondary w-50 p-3" style="background-color: #eee;">
							<form name="treatment_performance" method="POST" action="font">
								<input type="hidden" name="command" value="perform_treatment" />
								<input type="hidden" name="id_appointment" value="${treatment.idAppointment}" />
								<input type="hidden" name="consent" value="${treatment.consent}" />
								<input type="hidden" name="treatment_type" value="${treatment.treatmentType}" />
								<input type="hidden" name="patient_id" value="${param.patient_id}">
								
								<div class="form-group">
  									<label for="setting_date">${date}</label>
  									<input type="date" name="setting_date" value="" >
								</div>
						
								<div class="form-check">
          							<input class="form-check-input" type="radio" name="status" value="in progress" checked>
          							<label class="form-check-label">${in_progress}</label>
        						</div>
        					
        						<div class="form-check">
          							<input class="form-check-input" type="radio" name="status" value="completed" >
          							<label class="form-check-label">${completed}</label>
          						</div>
          						
          						<!-- Alerts -->
          						
          						<c:if test="${param.message == 'access_denied'}">
          							<div class="alert alert-danger" role="alert">
										<c:out value = "${access_denied}" />
									</div>
								</c:if>
								
								<c:if test="${param.message == 'error_data'}">
          							<div class="alert alert-danger" role="alert">
										<c:out value = "${error_data} ${param.invalid_parameters}" />
									</div>
								</c:if>       						
          					
          						<button type="submit" class="btn btn-primary">${submit_btn}</button>	 		
							</form>
						</div>
						
						</c:if>
						
						<c:if test="${!empty treatment.performingList}">
						<h5>${performed}</h5>
							<ul class="list-group">
								<c:forEach items="${treatment.performingList}" var="item">
  									<li class="list-group-item">
  										<ul class="list-group inner">
                    						<li class="list-group-item">${date} ${item.datePerforming}</li>
                    						<li class="list-group-item">${performer} ${item.performerSurname} ${item.performerName}</li>
                    						<li class="list-group-item">${status} ${item.status.getStatusValue()}</li>
                						</ul>
  									</li>
  								</c:forEach>
  							</ul>
  						</c:if>
						
					</td>
				</tr>
				
			</c:forEach>
			</tbody>
			
		</table>
	
	
</body>
</html>