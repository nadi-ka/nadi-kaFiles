<%@ page language="java" contentType = "text/html; charset=UTF-8;" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>logIn-page</title>
</head>

<body>

<h2>Login</h2>
            
<form method="post" action="LoginController" name="LoginForm" onsubmit="return validate();">
                
    Login    :<input type="text" name="txt_login">
    Password    :<input type="password" name="txt_password">
                
    <input type="submit" name="btn_login" value="Login">
                
    <h3>Don't have an account? <a href="signUp.jsp">Register</a></h3>
    
                
</form>

</body>
</html>