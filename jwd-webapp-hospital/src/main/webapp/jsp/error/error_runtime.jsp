<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>RuntameError-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.error.runtime.message" var="runtimemsg" />
<fmt:message bundle="${loc}" key="local.error.requestfailed" var="requestfailed" />
<fmt:message bundle="${loc}" key="local.error.servletname" var="servletname" />
<fmt:message bundle="${loc}" key="local.error.code" var="statuscode" />
<fmt:message bundle="${loc}" key="local.error.exception" var="exception" />
<fmt:message bundle="${loc}" key="local.navigate_index" var="navigate_index" />

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
	
	<br/>
	<h1><c:out value="${runtimemsg}"/></h1>
	<br/>
	
	<ul>

		<li><c:out value="${requestfailed}"/>: ${pageContext.errorData.requestURI}</li>
		<li><c:out value="${servletname}"/>: ${pageContext.errorData.servletName}</li>
		<li><c:out value="${statuscode}"/>: ${pageContext.errorData.statusCode}</li>
		<li><c:out value="${exception}"/>: ${pageContext.errorData.throwable}</li>

	
	</ul>

	<a href="../index.jsp"><c:out value="${navigate_index}"/></a>

</body>
</html>