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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="/elements/header.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-lg-3 col-sm-6">

				<div class="card hovercard">
					<div class="cardheader"></div>
					<div class="avatar">
						<img alt="${member.getName()}"
							src="//graph.facebook.com/${member.getId()}/picture?type=large&width=300&height=300" />
					</div>
					<div class="info">
						<div class="title">
							<a target="_blank" href="https://m.me/${member.getId()}">${member.getName()}</a>
						</div>
						
					</div>
					<div class="bottom">
						<a class="fab fa-facebook" style="font-size:48px;" href="//facebook.com/profile.php?id=${member.getId()}" target = "_blank"></a>
						<a class="fab fa-facebook-messenger" style="font-size:60px;color:blue;" href="//m.me/${member.getId()}" target = "_blank"></a>
					</div>
				</div>

			</div>

		</div>
	</div>
	<div class="container">
		<div class="row">
			<c:if test = "${fbExist}">
         		<section>Introduction: ${intro}</section>
         		<section>Level: ${level}</section>
         		<section>Dedication: ${dedication}</section>
         		<section>Streak: ${streak}</section>
      		</c:if>
			<c:forEach items="${posts}" var="post"> 
			  <article>
			  <section><a href= "https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(post.id, '1784461175160264_', '')}" target = "_blank">Facebook Status</a></section>
			  <section>datetime: ${post.createdDate}</section>
			    <section>${post.picture}</section>
			    <section>${post.content}</section>
			    		    
			  </article>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="/elements/footer.jsp" />
</body>
</html>