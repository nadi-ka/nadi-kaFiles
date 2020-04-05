<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>

<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<meta charset="UTF-8">
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
<fmt:message bundle="${loc}" key="local.treatment.consent.true" var="agree_radio" />
<fmt:message bundle="${loc}" key="local.treatment.consent.false" var="disagree_radio" />
<fmt:message bundle="${loc}" key="local.navigate_main" var="navigate_main" />

<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
	var="en_button" />
</head>

<body>

	<form action="register" method="POST">
		<input type="hidden" name="local" value="ru" /> 
		<input type="submit" value="${ru_button}" /><br />
	</form>

	<form action="register" method="POST">
		<input type="hidden" name="local" value="en" /> 
		<input type="submit" value="${en_button}" /><br />
	</form>
	
	<jsp:useBean id="treatment" class="by.epam.ts.bean.Treatment" scope="request"/>

	<c:forEach var="elem" items="${treatment}">

		<table>
			<caption>${treat}</caption>
			<tr>
				<th scope="col">${type}</th>
				<th scope="col">${name}</th>
				<th scope="col">{doctor}</th>
				<th scope="col">${date_begin}</th>
				<th scope="col">${date_finish}</th>
				<th scope="col">{consent}</th>
			</tr>
			<tr>
				<td>${requestScope.treatment.treatmentType}</td>
				<td>${requestScope.treatment.treatmentName}</td>
				<td>${requestScope.treatment.doctorSurname}<br> 
				${requestScope.treatment.doctorName}</td>
				<td>${requestScope.treatment.dateBeginning}</td>
				<td>${requestScope.treatment.dateFinishing}</td>
				<td>
					<form name="consentForm" method="POST" action="register">
						<input type="hidden" name="command" value="getConsent" /> 
						<input type="radio" name="consent" value="${agree_radio}"/> 
						<input type="radio" name="consent" value="${disagree_radio}"/>
					</form>
				</td>
			</tr>
		</table>

	</c:forEach>

	<a href="main.jsp">${navigate_main}</a>

</body>
</html>