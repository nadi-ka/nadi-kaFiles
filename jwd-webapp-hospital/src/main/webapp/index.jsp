<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<meta charset="UTF-8">
	
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	
	<title>Login-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.login.welcome" var="welcome_message"/>
<fmt:message bundle="${loc}" key="local.login.ready.help" var="ready_message"/>
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.login.have_account" var="account" />
<fmt:message bundle="${loc}" key="local.register" var="regist" />
<fmt:message bundle="${loc}" key="local.login.successfully.logout" var="successful_logout"/>
<fmt:message bundle="${loc}" key="local.login.successfully.registr" var="successful_sign_up"/>
<fmt:message bundle="${loc}" key="local.login.errordata" var="error_data"/>
<fmt:message bundle="${loc}" key="local.login.access_dinied" var="access_denied"/>
<fmt:message bundle="${loc}" key="local.signup.reflogin" var="reflogin" />

<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>
<fmt:message bundle="${loc}" key="local.validation.field_length" var="field_length"/>
<fmt:message bundle="${loc}" key="local.validation.symbols" var="symbols"/>

<fmt:message bundle="${loc}" key="local.login.button" var="login_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

</head>

<body>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_index_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_index_page"/>    
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>
	
	<div class="container text-center">
		<h1>${welcome_message}</h1>
		<p>${ready_message}</p>
	</div>
	
	<div class="mx-auto" style="width: 500px;">
		<img class="img-thumbnail" src="img/hospital_facade.png" alt="hospital facade image">
	</div>
	
	<!-- Alerts -->
	
		<c:if test="${param.message == 'successful_logout'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${successful_logout}"/>
			</div>
		</c:if>
		
		<c:if test="${param.message == 'successful_registration'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${successful_sign_up}"/>
			</div>
		</c:if>
		
		<c:if test="${param.message == 'access_denied'}">
			<div class="alert alert-primary" role="alert">
				<c:out value="${access_denied}"/>
			</div>
		</c:if>
	
	<!-- Login-form -->
	
	<div class="ml-4">
		<h3>${reflogin}:</h3>
	</div>
	
	<div class="border border-secondary w-50 p-3 form-bcground ml-4">	
	
		<form id="signup" name="loginForm" method="POST" action="font">
			<input type="hidden" name="command" value="login" />
				
				<div class="form-group">
				
					<label for="login">${login}</label>
					<input type="text" name="login" value="" class="required" id="login" /> 
					
				</div>
				
				<div class="form-group">
				
					<label for="password">${password}</label>
					<input type="password" name="password" value="" class="required" id="password"/>  
					
				</div>
				
				<c:if test="${param.message == 'error_data'}">
					<div class="alert alert-danger" role="alert">	
						<c:out value="${error_data}"/>
					</div>
				</c:if>
				
				<button type="submit" class="btn btn-secondary">${login_button}</button>
	    </form>
	</div>
	
	<hr/>
	
	<!-- Go to signUp-page -->
	
	<div class="container">
    	<div class="row">
    
    		<div class="col-md-8 col-xl-3">
				<p>${account}</p>
			</div>
			
			<div class="col-md-8 col-xl-3">
				<form name="To_signup_page" method="GET" action="font" >
					<input type="hidden" name="command" value="get_signup_page" /> 
					<button type="submit" class="btn btn-link">${regist}</button>
				</form>
			</div>
			
		</div>
	</div>
	
	<!-- Footer -->
	
	<div id="footer">
    	<jsp:include page="/WEB-INF/jsp/part/footer.jsp"/>
	</div>
	
	<!-- Form validation -->
	
	<script src="js/jquery.validate.min.js"></script>
	
	<script>
	
	var requiredField = '<p class="text-danger">${field_required}</p>';
	var loginLength = '<p class="text-danger">${field_length} 3-20 ${symbols}</p>';
	var passwordLength = '<p class="text-danger">${field_length} 5-20 ${symbols}</p>';

 	$('#signup').validate({
 		
   		rules: {
     		login: {
        		required: true,
        		rangelength:[3,20]
     		},
     		
     		password: {
        		required: true,
        		rangelength:[5,20]
     		}
   		}, //end rules;
   
   		messages: {
   			
      		login: {
         		required: requiredField,
         		rangelength: loginLength
       		},
       		
      		password: {
      			required: requiredField,
         		rangelength: passwordLength
      		}
   		} // end messages;

  	}); // end validate; 

</script>

</body>
</html>