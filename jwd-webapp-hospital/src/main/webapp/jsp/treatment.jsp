<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<html>

<head><title>Treatment</title></head>

<body>

	<c:forEach var="elem" items="${treatment}">
	
	<table>
		<caption>Treatment</caption>
		<tr>
			<th scope="col">Treatment type</th>
			<th scope="col">Treatment name</th>
			<th scope="col">Doctor, who assigned</th>
			<th scope="col">Date of beginning/holding</th>
			<th scope="col">Date of finishing</th>
			<th scope="col">Consent</th>
		</tr>
		<tr>
			<td><c:out value="${treatment.treatmentType}"/></td>
			<td><c:out value="${treatment.treatmentName}"/></td>
			<td><c:out value="${treatment.doctorSurname}"/><br>
			<c:out value="${treatment.doctorName}"/></td>
			<td><c:out value="${treatment.dateBeginning}"/></td>
			<td><c:out value="${dateFinishing}"/></td>
			<td>
				<form name="consentForm" method="POST" action="register">
					<input type="hidden" name="command" value="getConsent"/>
					
						<input type="radio" name="consent" value="agree">
						<input type="radio" name="consent" value="don't agree">				
				</form>
			</td>
		</tr>	
	</table>
	
	</c:forEach>
	
	<a href="main.jsp">Navigate to the main page</a>
	
</body>
</html>