<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
  <c:when test="1==1">
  <a href="/login">Login</a>
  </c:when>
  <c:otherwise>
  <a href="/profile">Profile</a>
  </c:otherwise>
</c:choose>