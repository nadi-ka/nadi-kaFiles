<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html>
<head><title>Error-page</title>
</head>

<body>
	Request from: ${pageContext.errorData.requestURI} is failed
	<br/>
	Servlet name or type: ${pageContext.errorData.servletName}
	<br/>
	Status code: ${pageContext.errorData.statusCode}
	<br/>
	Exception: ${pageContext.errorData.throwable}
	
	<br/>
	${requestScope.technical_error}
	<br/>
	${requestScope.wrong_action}
	<br/>
	
	<a href="${pageContext.request.contextPath}/index.jsp">Navigate to the start-page</a>
	
</body>
</html>