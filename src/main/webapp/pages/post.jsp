<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div class="col-lg-3 col-sm-6">

				<div class="card hovercard">
					<div class="cardheader"></div>
					<div class="avatar">
						habogay
					</div>
					<div class="info">
						
						<div class="desc">
							<i class="fas fa-check-circle"></i>Passionate designer
						</div>
						<div class="desc">Curious developer</div>
						<div class="desc">Tech geek</div>
					</div>
					<div class="bottom">
						<a class="btn btn-primary btn-twitter btn-sm"
							href="https://twitter.com/webmaniac"> <i
							class="fa fa-twitter"></i>
						</a> <a class="btn btn-danger btn-sm" rel="publisher"
							href="https://plus.google.com/+ahmshahnuralam"> <i
							class="fa fa-google-plus"></i>
						</a> <a class="btn btn-primary btn-sm" rel="publisher"
							href="https://plus.google.com/shahnuralam"> <i
							class="fa fa-facebook"></i>
						</a> <a class="btn btn-warning btn-sm" rel="publisher"
							href="https://plus.google.com/shahnuralam"> <i
							class="fa fa-behance"></i>
						</a>
					</div>
				</div>

			</div>

		</div>
	</div>
	<div class="container">
		<div class="row">
			
			  <article>
			  <section>${post.id}</section>
			    <section>${post.content}</section>
			    <section>${post.picture}</section>
			    <section>${post.permalink}</section>			    
			  </article>
			
		</div>
	</div>
	<jsp:include page="/elements/footer.jsp" />
</body>
</html>