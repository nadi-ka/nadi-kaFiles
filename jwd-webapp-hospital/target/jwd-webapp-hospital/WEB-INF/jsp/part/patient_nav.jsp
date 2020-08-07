<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="author"%>
	
	<meta charset="UTF-8">
	
	<title>Header</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.nav.main_page" var="navigate_main" />
	<fmt:message bundle="${loc}" key="local.main.update_patient" var="update_patient_data" />
	<fmt:message bundle="${loc}" key="local.main.showtreat" var="show_treat" />
	<fmt:message bundle="${loc}" key="local.main.calc_hospitalization" var="calc_hospitalization" />
	
	<header>
	
	<!-- Navigation menu -->
	
	<nav class="navbar navbar-dark navbar-expand-lg bg-company-red">
  		
  		<form class="form-inline" name="navigate_main" action="font" method="GET">
  			<input type="hidden" name="command" value="get_patient_main_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${navigate_main}</button>
        </form>
        
        <form class="form-inline" name="update_patient_data" action="font" method="GET">
  			<input type="hidden" name="command" value="get_update_patient_data_page" />
  			<button type="submit" class="btn btn-sm btn-outline-secondary">${update_patient_data}</button>
        </form>
        
        <form class="form-inline" name="get_treatment" action="font" method="GET">
			<input type="hidden" name="command" value="get_treatment_page" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${show_treat}</button>
		</form>
		
		<form class="form-inline" name="calc_hospitalization" method="GET" action="font">
			<input type="hidden" name="command" value="get_hospitalization_plan" /> 
			<button type="submit" class="btn btn-sm btn-outline-secondary">${calc_hospitalization}</button>
		</form>
		
	</nav>
	
	</header>