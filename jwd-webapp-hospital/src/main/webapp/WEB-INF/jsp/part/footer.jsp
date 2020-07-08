<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="author"%>
	
	<meta charset="UTF-8">

	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="style/style.css"/>
	
	<title>Footer</title>

	<fmt:setLocale value="${sessionScope.local}" />
	<fmt:setBundle basename="localization.locale" var="loc" />

	<fmt:message bundle="${loc}" key="local.footer.company_name" var="company_name"/>
	<fmt:message bundle="${loc}" key="local.footer.ophthalmological_hospital" var="ophthalmological_hospital"/>
	<fmt:message bundle="${loc}" key="local.footer.contact" var="contact"/>
	<fmt:message bundle="${loc}" key="local.footer.map" var="map"/>

	<!-- Footer -->

	<footer class="page-footer font-small mdb-color pt-4" style="background-color: #ffd9b3;">

  		<!-- Footer Links -->
  		<div class="container text-center text-md-left">

    		<!-- Footer links -->
    		<div class="row text-center text-md-left mt-3 pb-3">

      			<!-- Grid column -->
      			<div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
        			<h6 class="mb-4 font-weight-bold">${company_name}</h6>
        			<p>${ophthalmological_hospital}</p>
      			</div>
      	
      			<!-- Grid column -->
      			<div class="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
        			<h6 class="mb-4 font-weight-bold">${contact}</h6>
        			<p>Belarus, city Minsk, st. "Masherova"-1, 220111</p>
        			<p>+(375)172-11-11-11</p>
      			</div>
     
      			<!-- Grid column -->
      			<div class="col-md-3 col-lg-2 col-xl-2 mx-auto mt-3">
        			<h6 class="mb-4 font-weight-bold">${map}</h6>
        			<div id="imgtab" class="small">
        				<img class="img-thumbnail" src="img/map.png" alt="the map of minsk center image" />
        			</div>
      			</div>
      			
    		</div>

    	<hr>

    	<!-- Grid row -->
    	<div class="row d-flex align-items-center" style="background-color: #ffcc99;">
    	
        	<!--Copyright-->
        	<p class="text-center text-md-left">© 2020 Copyright:
            	<author:signature/>
        	</p>
      	</div>

    </div>
 
  </footer>
  
  <script src="js/jquery-3.4.1.min.js"></script>
  <script>
	
		var small={width: "200px",height: "116px"};
	    var large={width: "400px",height: "232px"};
	    var count=1; 
	    $("#imgtab").css(small).on('click',function () { 
	         $(this).animate((count==1)?large:small);
	         count = 1-count;
	    }); // end click;			
	
	</script>
  
 </body>
 </html>