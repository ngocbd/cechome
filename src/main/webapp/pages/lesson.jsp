<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="/elements/header.jsp" />
	
	<div class="container">
		<div class="row">
		accId: ${User.id}
		  	<section>
		  		<img alt = "requester review" src="${imgUrl1}"/>
		  	</section>
			<section>
		  		<img alt = "requester review" src="${imgUrl2}"/> 
		  	</section>
		  	<section>
		  		<audio controls>
	  				<source src="${mp3Url}" type="audio/mpeg">
				</audio>
		  	</section>
		  	<section>
		  		<video width="320" height="240" controls>
	  				<source src="${videoUrl}" type="video/mp4">
				</video>
		  	</section>	
		</div>
	</div>
	<jsp:include page="/elements/footer.jsp" />
</body>
</html>
