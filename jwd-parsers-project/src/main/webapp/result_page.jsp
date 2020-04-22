<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<html>
	<head>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/bootstrap.min.css"/>
	
		<title>Result-page</title>
		
	</head>
	
	<body>
	
	<h1>Results of parsing of your file:</h1>
		<h3>${file_name}</h3>
		<h3>${parser}</h3>
	
	<jsp:useBean id="patients" type="java.util.List<by.epam.ts.bean.Patient>" scope="request"/>

		<table class="table table-bordered">
			<caption>Results of parsing</caption>
			
			<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Surname</th>
					<th scope="col">Name</th>
					<th scope="col">Date of birth</th>
					<th scope="col">Treatment</th>
				</tr>
			</thead>
			
			<tbody>
			
			<c:forEach var="patient" items="${patients}">
			
				<tr>
					<th scope="row">${patient.id}</th>
					<td>${patient.surname}</td>
					<td>${patient.name}</td>	
					<td>${patient.dateOfBirth}</td>
					<td>
						<ul class="list-group">
							<c:forEach var="treatment" items="${patient.prescriptions}">
								<li class="list-group-item list-group-item-info">
								<p>Number: ${treatment.idAppointment}</p>
								<p>Appointment: ${treatment.treatmentName}</p>
								<p>Doctor surname: ${treatment.doctorSurname}</p>
								</li>
							</c:forEach>
						</ul>			
					</td>
				</tr>	
			</c:forEach>		
			</tbody>	
		</table>
		
		<form method="get" action="controller">
			<input type="hidden" name="command" value="get_index_page"/>
			<button type="submit" class="btn btn-secondary">Back to the start-page</button>
		</form>
		
	</body>
</html>