<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<html>
	<head>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/bootstrap.min.css"/>
	
		<title>Login-page</title>
		
	</head>
	
	<body>
	
		<h1>Please, upload your file and choose, how it should be parsed.</h1>

		<form method="post" action="controller?command=upload_file" enctype="multipart/form-data">
    		Choose a file: <input type="file" name="multiPartServlet"/>
    		
    		<div class="form-check">
    			<input class="form-check-input" type="radio" name="parser" id="sax" value="sax_parser" checked>
  				<label class="form-check-label" for="sax">SAX parser</label>
  			</div>
			<div class="form-check">
  				<input class="form-check-input" type="radio" name="parser" id="stax" value="stax_parser">
  				<label class="form-check-label" for="stax">StaX parser</label>
            </div>
            <div class="form-check">
  				<input class="form-check-input" type="radio" name="parser" id="dom" value="dom_parser">
  				<label class="form-check-label" for="dom">DOM parser</label>
            </div>
				
    		<button type="submit" class="btn btn-secondary">Upload and parse</button>
    		
    		<div class="alert alert-danger" role="alert">
    		
    			<c:if test="${param.message == 'need_upload_file'}">
    				<c:out value="You haven't upload any file. Please, do it."/>
    			</c:if>
    			<c:if test="${param.message == 'need_choose_parser'}">
    				<c:out value="You haven't choosen any parser. Please, do it."/>
    			</c:if>
    			<c:if test="${param.message == 'invalid_document'}">
    				<c:out value="You xml document is not valid. Please, try to upload another document or change current one."/>
    			</c:if>
    			
    		</div>
    		
		</form>

	</body>
	
</html>
