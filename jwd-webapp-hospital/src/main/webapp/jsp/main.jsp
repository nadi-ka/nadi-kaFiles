<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>

<head><title>Welcome</title></head>

<body>

	<h3>You are welcome!</h3>
	<hr/>
	${user}, welcome!
	<hr/>
	
	<form name="LogoutForm" method="POST" action="register">
		<input type="hidden" name="command" value="logout" />
		
		<input type="submit" name="btn_logout" value="Log out">
		
	</form>
	
	<a href="/index.jsp">Navigate to the main page</a>
	
	<br/>
    ${LoggedOut};
	
</body>
</html>