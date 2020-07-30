<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

	<title>Hospitalization-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.nav_main" var="navigate_main" />
	<fmt:message bundle="${loc}" key="local.main.update_patient" var="update_patient_data" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.already_discharged" var="already_discharged" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.hospitalization_calc" var="hospitalization_calc" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.average_bed_days" var="average_bed_days" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.expected_discharge" var="expected_discharge" />
	<fmt:message bundle="${loc}" key="local.hospital_plan.diagnosis_absent" var="diagnosis_absent" />
	
	<fmt:message bundle="${loc}" key="local.staff.current_patient.entry_date" var="entry_date" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.discharge_date" var="discharge_date" />
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
		<input type="hidden" name="redirect_command" value="get_hospitalization_plan_page"/>     
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_hospitalization_plan_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
	
		<form class="form-inline" name="update_patient_data" action="font" method="GET">
  			<input type="hidden" name="command" value="get_update_patient_data_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${update_patient_data}</button>
        </form>
  		
  		<form class="form-inline" name="navigate_main" action="font" method="GET">
  			<input type="hidden" name="command" value="get_patient_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${navigate_main}</button>
        </form>
        
        <form class="form-inline" name="get_treatment" action="font" method="GET">
			<input type="hidden" name="command" value="get_treatment_page" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${show_treat}</button>
		</form>
		
	</nav>
	
	<!-- Alerts -->
	
	<c:if test="${requestScope.message == 'already_discharged'}">
  		<div class="alert alert-warning" role="alert">
			<c:out value="${already_discharged} ${requestScope.date_finishing}"/>
		</div>
	</c:if>
	<c:if test="${requestScope.message == 'diagnosis_absent'}">
  		<div class="alert alert-warning" role="alert">
			<c:out value="${diagnosis_absent}"/>
		</div>
	</c:if>
	
	<!-- If current, actual hospitalization was found, display expected amount of bed-days and discharge date -->
	
	<c:if test="${!empty bed_days}">
		<h5><strong>${hospitalization_calc}:</strong></h5>
		<ul class="list-group">
  			<li class="list-group-item">${average_bed_days} ${requestScope.bed_days}</li>
  			<li class="list-group-item">${expected_discharge} ${requestScope.date_finishing}</li>
  		</ul>
  	</c:if>
	
	<!-- Displaying of the dates of the last hospitalization -->
	
	<c:if test="${!empty hospitalization}">
		
		<table class="table table-bordered">
			<thead>
				<tr class="table-active">
					<th scope="col">${entry_date}</th>
					<th scope="col">${discharge_date}</th>
				</tr>
			</thead>
			
			<tbody>
				<tr>
					<td>${hospitalization.entryDate}</td>	
					<td>${hospitalization.dischargeDate}</td>
				</tr>	
			</tbody>
		</table>
		
	</c:if>
	
	<!-- Footer -->
	
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>

</body>
</html>