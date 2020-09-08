<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html>
<head>

	<title>RuntimeError-page</title>
	<meta charset="UTF-8">

	<link rel="stylesheet" href="style/style.css"/>
	
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
	Sorry, the page is not available now. We are solving this problem!
	<br/>

	<!-- Navigate to the index-page -->

	<form name="To_index_page" method="GET" action="font" >
		<input type="hidden" name="command" value="get_index_page" /> 
		<button type="submit" class="btn btn-link">Navigate to the index-page</button>
	</form>
	
	<img src="img/sorry_runtime.png" alt="sorry_image" class="img-standart">
	
	
</body>
</html>

