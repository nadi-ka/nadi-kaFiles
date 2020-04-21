<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>

	<title>Treatment</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	<fmt:message bundle="${loc}" key="local.treatment" var="treat" />
	<fmt:message bundle="${loc}" key="local.treatment.type" var="type" />
	<fmt:message bundle="${loc}" key="local.treatment.name" var="name" />
	<fmt:message bundle="${loc}" key="local.treatment.doctor" var="doctor" />
	<fmt:message bundle="${loc}" key="local.treatment.datebegin" var="date_begin" />
	<fmt:message bundle="${loc}" key="local.treatment.datefinish" var="date_finish" />
	<fmt:message bundle="${loc}" key="local.treatment.consent" var="consent" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.true" var="agree" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.false" var="disagree" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.indefined" var="indefined" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.general" var="general_consent" />
	<fmt:message bundle="${loc}" key="local.navigate_main" var="navigate_main" />
	<fmt:message bundle="${loc}" key="local.treatment.button.consent" var="give_consent" />
	<fmt:message bundle="${loc}" key="local.button.submit" var="submit_button" />

	<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
	<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="redirect_command" value="show_treatment"/> 
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="redirect_command" value="show_treatment"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<jsp:useBean id="prescriptions" type="java.util.List<by.epam.ts.bean.Treatment>" scope="request"/>

		<table class="table table-bordered">
			<caption>${treat}</caption>
			
			<thead>
				<tr>
					<th scope="col">${type}</th>
					<th scope="col">${name}</th>
					<th scope="col">${doctor}</th>
					<th scope="col">${date_begin}</th>
					<th scope="col">${date_finish}</th>
					<th scope="col">${consent}</th>
				</tr>
			</thead>
			
			<tbody>
			
			<c:forEach var="treatment" items="${prescriptions}">
			
				<tr>
					<th scope="row">${treatment.treatmentType}</th>
					<td>${treatment.treatmentName}</td>
					<td>${treatment.doctorSurname}<br> 
						${treatment.doctorName}</td>	
					<td>${treatment.dateBeginning}</td>
					<td>${treatment.dateFinishing}</td>
					<td>
						<c:choose> 
							<c:when test="${treatment.consent == true}">
								<strong><c:out value="${agree}"/></strong>
							</c:when>
							<c:when test="${treatment.consent == false}">
								<strong><c:out value="${disagree}"/></strong>
							</c:when>
							<c:otherwise>
								<strong><c:out value="${indefined}"/></strong>
							</c:otherwise>
						</c:choose>
						
						<form name="consentForm" method="POST" action="register">
						
						<input type="hidden" name="command" value="give_consent" />
						<input type="hidden" name="id_appointment" value="${treatment.idAppointment}" />
						
							<div class="form-check">
          						<input class="form-check-input" type="radio" name="consent" value=true>
          						<label class="form-check-label">${agree}</label>
        					</div>
        					
        					<div class="form-check">
          						<input class="form-check-input" type="radio" name="consent" value=false checked>
          						<label class="form-check-label">${disagree}</label>
          					</div>
          					
          					<button type="submit" class="btn btn-primary">${submit_button}</button>	 
          						
						</form>
						
					</td>
				</tr>
				
			</c:forEach>
			</tbody>
			
		</table>

</body>
</html>