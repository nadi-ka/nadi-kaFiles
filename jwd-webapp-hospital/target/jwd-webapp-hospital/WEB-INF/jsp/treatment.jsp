<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
	<meta charset="UTF-8">
	
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>

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
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.status" var="status"/>
	<fmt:message bundle="${loc}" key="local.treatment.request_denied" var="request_denied" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.surgical" var="surgical" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.conservative" var="conservative" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.procedures" var="procedures" />
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.in_progress" var="in_progress"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.completed" var="completed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.prescribed" var="prescribed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.canceled" var="canceled"/>
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

	<!--  Change language buttons -->

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
	
	<div id="patient_nav">
    	<jsp:include page="/WEB-INF/jsp/part/patient_nav.jsp"/>
	</div>
	
	<!-- Navigation menu -->
	
	<!-- Alert -->
	
	<c:if test="${param.message == 'wrong_request'}">
    	<div class="alert alert-danger" role="alert">
			<c:out value = "${request_denied}" />
		</div>
	</c:if>
	
	<jsp:useBean id="prescriptions" type="java.util.List<by.epam.ts.bean.Treatment>" scope="request"/>
	
	<!--  Table 'Treatment' -->

		<table class="table table-bordered">
			<caption>${treat}</caption>
			
			<thead>
				<tr>
					<th scope="col">${type}</th>
					<th scope="col">${name}</th>
					<th scope="col">${doctor}</th>
					<th scope="col">${date_begin}</th>
					<th scope="col">${date_finish}</th>
					<th scope="col">${status}</th>
					<th scope="col">${consent}</th>
				</tr>
			</thead>
			
			<tbody>
			
			<c:forEach var="treatment" items="${prescriptions}">
			
				<tr>
					<th scope="row">
						<c:choose> 
							<c:when test="${treatment.treatmentType == 'SURGICAL'}">
								<c:out value="${surgical}"/>
							</c:when>
							<c:when test="${treatment.treatmentType == 'PROCEDURES'}">
								<c:out value="${procedures}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${conservative}"/>
							</c:otherwise>
						</c:choose>
					</th>
					<td>${treatment.treatmentName}</td>
					<td>${treatment.doctorSurname}<br> 
						${treatment.doctorName}</td>	
					<td>${treatment.dateBeginning}</td>
					<td>${treatment.dateFinishing}</td>
					<td>
						<c:choose> 
							<c:when test="${treatment.getTreatmentStatus() == 'CANCELED'}">
								<c:out value="${canceled}"/>
							</c:when>
							<c:when test="${treatment.getTreatmentStatus() == 'IN_PROGRESS'}">
								<c:out value="${in_progress}"/>
							</c:when>
							<c:when test="${treatment.getTreatmentStatus() == 'COMPLETED'}">
								<c:out value="${completed}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${prescribed}"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose> 
							<c:when test="${treatment.consent == true}">
								<strong><c:out value="${agree}"/></strong>
							</c:when>
							<c:otherwise>
								<strong><c:out value="${disagree}"/></strong>
							</c:otherwise>
						</c:choose>
						
						<!-- Change consent form -->
						
						<c:if test="${treatment.getTreatmentStatus() != 'COMPLETED' and treatment.getTreatmentStatus() != 'CANCELED'}">	
						
							<form name="consentForm" method="POST" action="font">
						
								<input type="hidden" name="command" value="give_consent" />
								<input type="hidden" name="id_appointment" value="${treatment.idAppointment}" />
								<input type="hidden" name="status" value="${treatment.getTreatmentStatus().getStatusValue()}" />
						
								<div class="form-check">
          							<input class="form-check-input" type="radio" name="consent" value=true>
          							<label class="form-check-label">${agree}</label>
        						</div>
        					
        						<div class="form-check">
          							<input class="form-check-input" type="radio" name="consent" value=false checked>
          							<label class="form-check-label">${disagree}</label>
          						</div>
          					
          						<button type="submit" class="btn btn-secondary">${submit_button}</button>	 
          						
							</form>
						</c:if>
						
					</td>
				</tr>
				
			</c:forEach>
			</tbody>
			
		</table>
		
		<hr/>
		
		<!-- Footer -->
		
		<div id="footer">
    		<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
		</div>

</body>
</html>