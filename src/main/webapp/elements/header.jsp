<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));</script>
<header>
	<c:choose>
		<c:when test="${ User ==null }">
			<a href="https://cec.net.vn/login?go=https://cec.net.vn/">Login</a>
		</c:when>
		<c:otherwise>
			<a href="/profile"><img src="//graph.facebook.com/${User.id}/picture?type=square" /></a>
			<a href="/logout">Logout</a>
		</c:otherwise>
	</c:choose>
</header>