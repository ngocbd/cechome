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
		<c:forEach items="${lessonNameLists}" var="lessonNameList"> 
		  	<section>
		  		<a href = "https://cec.net.vn/lesson/${lessonNameList.getKey()}" target = "_blank">${lessonNameList.getValue()}</a><br/>
		  	</section>
		</c:forEach>
		<c:forEach items="${lessonNameNotExistLists}" var="lessonNameNotExistList"> 
		  	<section>
		  		${lessonNameNotExistList.getValue()}<br/>
		  	</section>
		</c:forEach>
		</div>
	</div>
	<jsp:include page="/elements/footer.jsp" />
</body>
</html>