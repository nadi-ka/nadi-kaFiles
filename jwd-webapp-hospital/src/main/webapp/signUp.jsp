<%@ page language="java" contentType="text/html; charset=UTF-8;"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	
	<title>SignUp-page</title>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.locale" var="loc" />
<fmt:message bundle="${loc}" key="local.register" var="register" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.signup.button" var="signup_btn" />
<fmt:message bundle="${loc}" key="local.signup.account" var="account" />
<fmt:message bundle="${loc}" key="local.signup.reflogin" var="reflogin" />
<fmt:message bundle="${loc}" key="local.signup.errordata" var="error_data" />

<fmt:message bundle="${loc}" key="local.validation.required" var="field_required"/>
<fmt:message bundle="${loc}" key="local.validation.field_length" var="field_length"/>
<fmt:message bundle="${loc}" key="local.validation.symbols" var="symbols"/>

<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
	
</head>

<body>

	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="ru" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_signup_page"/>  
		<button type="submit" class="btn btn-secondary">${ru_button}</button>
	</form>
	
	<br/>
	
	<form class="ml-2" action="font" method="POST">
		<input type="hidden" name="command" value="change_language"/>
		<input type="hidden" name="local" value="en" />
		<input type="hidden" name="query_string" value="${requestScope['javax.servlet.forward.query_string']}"/>
		<input type="hidden" name="redirect_command" value="get_signup_page"/>  
		<button type="submit" class="btn btn-secondary">${en_button}</button>
	</form>

	<div class="ml-4">
		<h1>${register}</h1>
	</div>
		 
		 <div class="border border-secondary w-50 p-3 form-bcground ml-4">
		 	
		 	<img class="rounded float-right" src="img/registration.png" 
		 	alt="pen and paper image" style="width:6em; height:7em;" />
		 	
			<form id="signup" name="registerForm" method="POST" action="font">
				<input type="hidden" name="command" value="sign_up" />
			
				<div class="form-group">
					<label class="field-label">${login}</label>
					<input type="text" name="login" value="" class="required" id="login"/> 		
				</div>
			
				<div class="form-group">
					<label class="field-label">${password}</label>
					<input type="password" name="password" value="" class="required" id="password"/> 	
				</div>
			
				<div class="form-group">
					<label class="field-label">${email}</label>
					<input type="email" name="email" value="" class="required" id="email"/> 	
				</div>
			
				<c:if test="${param.message == 'error_data'}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error_data}: ${param.invalid_parameters}"/>
					</div>
				</c:if>

				<button type="submit" class="btn btn-primary" name="btn_signup">${signup_btn}</button>

			</form>
		</div>
		
		<hr/>
	
	<!-- Go to index-page -->
	
	<div class="container">
    	<div class="row">
    
    		<div class="col-md-8 col-xl-3">
				<p>${account}</p>
			</div>
			
			<div class="col-md-8 col-xl-3">
				<form name="To_index_page" method="GET" action="font" >
					<input type="hidden" name="command" value="get_index_page" /> 
					<button type="submit" class="btn btn-link">${reflogin}</button>
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
     		},
     		
     		email: {
     			required: true
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
      		},
      		
      		email: {
      			required: requiredField
      		}
   		} // end messages;

  	}); // end validate; 

</script>
	
</body>
</html>