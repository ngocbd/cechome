<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<a href="/login">Login</a>
		</c:when>
		<c:otherwise>
			<a href="/profile"><img src="//graph.facebook.com/${User.id}/picture?type=square" /></a>
			<a href="/logout">Logout</a>
		</c:otherwise>
	</c:choose>
</header>