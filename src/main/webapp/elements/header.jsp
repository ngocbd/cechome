<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<c:choose>
		<c:when test="${ User ==null }">
			<a href="/login">Login</a>
		</c:when>
		<c:otherwise>
			<a href="/profile"><img src="http://graph.facebook.com/${User.getId()}/picture?type=square" /></a>
			<a href="/logout">Logout</a>
		</c:otherwise>
	</c:choose>
</header>