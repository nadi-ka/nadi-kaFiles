<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script>
	$(function() {
		  $("#treat_performance_form").each(function() {
			  var status = ${item.status.getStatusValue()};
		      if (status === 'completed') {
		      $(this).hide();
		    }
		  });
		});
	</script>

	<title>Treat-performance-page</title>
	
	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />
	
	<fmt:message bundle="${loc}" key="local.main.logout_btn" var="logout_button" />
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
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.prescribed" var="prescribed"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.canceled" var="canceled"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.date" var="date"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.performer" var="performer"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.status" var="status"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.surgical_treatment" var="surgical_treatment"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.doctor_cancel" var="doctor_cancel"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.date_cancel" var="date_cancel"/>
	<fmt:message bundle="${loc}" key="local.staff.treat_perform.access_denied" var="access_denied"/>
	<fmt:message bundle="${loc}" key="local.treatment.request_denied" var="request_denied" />
	<fmt:message bundle="${loc}" key="local.staff.main.error_data" var="error_data" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.true" var="agree" />
	<fmt:message bundle="${loc}" key="local.treatment.consent.false" var="disagree" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.surgical" var="surgical" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.conservative" var="conservative" />
	<fmt:message bundle="${loc}" key="local.staff.prescriptions.procedures" var="procedures" />
	<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>
	
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
	
	<div id="staff_nav">
    	<jsp:include page="/WEB-INF/jsp/part/staff_nav.jsp"/>
	</div>
	
	<!-- Navigation menu -->
	
	<!-- Alert -->
          						
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
	
	<c:if test="${param.message == 'wrong_request'}">
    	<div class="alert alert-danger" role="alert">
			<c:out value = "${request_denied}" />
		</div>
	</c:if>  
	
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
					
					<td>
						<!-- Treatment type displaying -->
						
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
					</td>
					
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
								<p class='text-danger'>${disagree}</p>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					
						<!-- Display data in column 'executions' only if consent was given -->
					
						<c:if test="${treatment.consent == 'true'}">
						
							<c:choose> 
								<c:when test="${treatment.getTreatmentStatus() == 'COMPLETED'}">
									<strong><c:out value="${completed}"/></strong>
								</c:when>
								<c:when test="${treatment.getTreatmentStatus() == 'CANCELED'}">
									<strong><c:out value="${canceled}"/></strong>
								</c:when>
								<c:when test="${sessionScope.role == 'NURSE' and treatment.treatmentType == 'SURGICAL'}">
									<p class='text-danger'>${surgical_treatment}</p>
								</c:when>
								<c:otherwise>
								
									<!-- 'Perform treatment' form, won't display in case of surgical treatment for nurses-->			
						
									<h5>${form_name}</h5>
						
									<div id="treat_performance_form" class="border border-secondary w-50 p-3 form-bcground">
										<form id="treat_performance" name="treatment_performance" method="POST" action="font">
											<input type="hidden" name="command" value="perform_treatment" />
											<input type="hidden" name="id_appointment" value="${treatment.idAppointment}" />
											<input type="hidden" name="consent" value="${treatment.consent}" />
											<input type="hidden" name="treatment_type" value="${treatment.treatmentType.getTypeValue()}" />
											<input type="hidden" name="patient_id" value="${param.patient_id}">
											<input type="hidden" name="current_status" value="${treatment.getTreatmentStatus().getStatusValue()}" />
								
											<div class="form-group">
  												<label for="setting_date">${date}</label>
  												<input type="date" name="setting_date" value="" id="date" required>
											</div>
						
											<div class="form-check">
          										<input class="form-check-input" type="radio" name="status" value="in progress" checked>
          										<label class="form-check-label">${in_progress}</label>
        									</div>
        					
        									<div class="form-check">
          										<input class="form-check-input" type="radio" name="status" value="completed" >
          										<label class="form-check-label">${completed}</label>
          									</div>	     						
          					
          									<button type="submit" class="btn btn-primary">${submit_btn}</button>	 		
										</form>
									</div>
									
								</c:otherwise>
							</c:choose>	
						
						</c:if>
						
						<!-- Display performed procedures -->
						
						<c:if test="${!empty treatment.performingList}">
							<ul class="list-group">
								<c:forEach items="${treatment.performingList}" var="item">
									<c:choose> 
										<c:when test="${item.status == 'CANCELED'}">
											<li class="list-group-item">
  												<ul class="list-group inner">
                    								<li class="list-group-item">${date_cancel} ${item.datePerforming}</li>
                    								<li class="list-group-item">${doctor_cancel} ${item.performerSurname} ${item.performerName}</li>
                    								<li class="list-group-item">${status} 
														<p class='text-danger'>${canceled}</p>
													</li>
												</ul>
											</li>
										</c:when>
										<c:otherwise>
  									<li class="list-group-item">
  										<ul class="list-group inner">
                    						<li class="list-group-item">${date} ${item.datePerforming}</li>
                    						<li class="list-group-item">${performer} ${item.performerSurname} ${item.performerName}</li>
                    						<li class="list-group-item">${status} 
                    							<c:choose> 
													<c:when test="${item.status == 'COMPLETED'}">
														<p class='text-success'>${completed}</p>
													</c:when>
													<c:otherwise>
														<p class='text-warning'>${in_progress}</p>
													</c:otherwise>
												</c:choose>
                    						</li>	
                						</ul>
  									</li>
  									</c:otherwise>
  									</c:choose>
  								</c:forEach>
  							</ul>
  						</c:if>
						
					</td>
				</tr>
				
			</c:forEach>
			</tbody>
			
		</table>
		
		<!-- Footer -->
		
		<div id="footer">
    		<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
		</div>
		
		<!-- Form validation -->
	
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/date_today.js"></script>
	
	<script>
	
		//Minimum value of date;
	
		document.getElementById("date").setAttribute("min", today);

	</script>
	
</body>
</html>