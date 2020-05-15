<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html>
<head>

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/style.css"/>
	
	<title>Error-page</title>
	
</head>

<body>
	Request from: ${pageContext.errorData.requestURI} is failed
	<br/>
	Servlet name or type: ${pageContext.errorData.servletName}
	<br/>
	Status code: ${pageContext.errorData.statusCode}
	<br/>
	Exception: ${pageContext.errorData.throwable}
	
	<div class="alert alert-danger" role="alert">
	
		<h5><c:if test="${param.message == 'technical_error'}">
				<c:out value = "Sorry, the web-site is undergoing technical work." />
			</c:if>
			<c:if test="${param.message == 'wrong_request'}">
				<c:out value = "Sorry, this comand is not allowed." />
			</c:if>
			<c:if test="${param.message == 'access_denied'}">
				<c:out value = "Sorry, you haven't appropriate rights for this operation." />
			</c:if>
		</h5>
		
	</div>
	
	<a href="${pageContext.request.contextPath}/index.jsp">Navigate to the start-page</a>
	
</body>
</html>