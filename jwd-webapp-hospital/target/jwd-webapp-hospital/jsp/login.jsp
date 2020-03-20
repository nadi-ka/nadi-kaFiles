<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored = "false"%>

<html><head><title>Login</title></head>

<body>
	<form name="loginForm" method="POST" action="register">
		<input type="hidden" name="command" value="login" />
		Login:<br/>
		<input type="text" name="login" value=""/>
		<br/>Password:<br/>
		<input type="password" name="password" value=""/>
		<br/>
			${errorLoginPassMessage}
		<br/>
			${wrongAction}
		<br/>
			${nullPage}
		<br/>
 		<input type="submit" value="Log in"/>
 		<br/>
 		
 		<h3>Don't have an account? <a href="/jsp/signUp.jsp">Register</a></h3>
	</form><hr/>
	
</body>
</html>