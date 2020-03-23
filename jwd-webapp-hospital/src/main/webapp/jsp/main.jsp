<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html>

<head><title>Welcome</title></head>

<body>

	<header>
	
		<nav>
      		<input type="hidden" name="command" value="showTreatment" />
		    <input type="submit" name="btn_showTreatment" value="Show treatment">
      	</nav>	
      	
      	<hr/>
		<h3>${user}, welcome at the main page of our hospital!</h3>
	    <hr/>
	
	</header>
	
	<form name="LogoutForm" method="POST" action="register">
	
		<input type="hidden" name="command" value="logout" />
		<input type="submit" name="btn_logout" value="Log out">
		
	</form>
	
	<a href="/index.jsp">Navigate to the login page</a>
	
	<br/>
	${accessDeniedMessage}
	<br/>
	
</body>
</html>