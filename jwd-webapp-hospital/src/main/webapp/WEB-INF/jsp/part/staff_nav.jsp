<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="author"%>
	
	<meta charset="UTF-8">
	
	<title>Header</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.nav.main_page" var="nav_main" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.get_current" var="get_current_patient" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.nav_hospitalization" var="set_hospitalization" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.make_diagnosis" var="make_diagnosis" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.prescribe_treatment" var="prescribe_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.current_patient.perform_treatment" var="perform_treatment" />
	<fmt:message bundle="${loc}" key="local.staff.main.button.search_patient" var="search_patient" />
	
	<header>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
	
		<form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_staff_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${nav_main}</button>
        </form>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_current_patient_page" />
  			<input type="hidden" name="patient_id" value="${requestScope.patient.id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${get_current_patient}</button>
        </form>  
	
		<c:if test="${sessionScope.role == 'ADMINISTRATOR' or sessionScope.role == 'DOCTOR'}">
        
        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_hospitalization_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${set_hospitalization}</button>
        	</form>
        
        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_diagnosis_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${make_diagnosis}</button>
        	</form>

        	<form class="form-inline" action="font" method="GET">
  				<input type="hidden" name="command" value="get_prescriptions_page" />
  				<input type="hidden" name="patient_id" value="${param.patient_id}">
  				<button type="submit" class="btn btn-sm btn-outline-secondary">${prescribe_treatment}</button>
        	</form>
        
        </c:if>
        
        <form class="form-inline" action="font" method="GET">
  			<input type="hidden" name="command" value="get_treat_performance_page" />
  			<input type="hidden" name="patient_id" value="${param.patient_id}">
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${perform_treatment}</button>
        </form>
        
        <form action="font" method="GET" class="form-inline my-2 my-lg-0 ml-auto">
        	<input type="hidden" name="command" value="search_patient"/>
      		<input class="form-control mr-sm-2" type="search" name="query_search" 
      			placeholder="Surname" aria-label="Search the patient">
      		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">${search_patient}</button>
    	</form>

	</nav>
	
	</header>