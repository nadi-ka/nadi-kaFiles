<%@ page language="java" contentType = "text/html; charset=UTF-8;" pageEncoding="UTF-8" isELIgnored = "false"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>signUp-page</title>
</head>

<body>

	<h1>Register</h1>
	
	<form name="registerForm" method="POST" action="register">
		<input type="hidden" name="command" value="sign_up" />
	
		<fieldset>
    		<label>Login:
        	<input type="text" name="login"><br />
    		</label>

    		<label>Password:
        	<input type="password" name="password"><br />
    		</label>
    		
    		<label>E-mail:
    		<input type="email" name="email"><br/>
    		</label>
    		
    		<input type="radio" name="type-of-user" value="patient">
			<input type="radio" name="type-of-user" value="medical-staff">
			
			<br/>
			${errorRegistrationDada}
			<br/>
			${wrongAction}
			<br/>
			${nullPage}
			<br/>
			
    		<input type="submit" name="btn_register" value="Sign Up">
    	</fieldset>
    	
	</form>
	
	<h3>You already have an account? <a href="login.jsp">LogIn</a></h3>
	
</body>
</html>