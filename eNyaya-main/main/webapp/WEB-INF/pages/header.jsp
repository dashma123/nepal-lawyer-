<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" type="text/css" /> 		
	</head>
	
<body>

	<div class="top-bar">
	
		<a href="#">
			<i class="fab fa-facebook-f"></i>
			<i class="fab fa-instagram"></i>
		</a>
		
		<div class="language-dropdown">
		
			<div class="language-select">
				<span>EN</span> <i class="fas fa-caret-down"></i>
			</div>
			
			<div class="language-options">
				<a href="#">English</a>
				<a href="#">Nepali</a>
				<a href="#">Hindi</a>
			</div>
			
		</div>
	</div>

	<div class="nav-bar">

		<div class="logo-section">
			<a href="${pageContext.request.contextPath}/clientHome">
				<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo">
			</a>

			<div class="logo-text">
				Online<br>
				Legal Aid<br>
				System
			</div>

		</div>

		<div class="nav-links">
			<a href="${pageContext.request.contextPath}/findLawyer">Find Lawyers</a>
			<a href="<c:url value='/aboutus'/>">About Us</a>
			<a href="${pageContext.request.contextPath}/contactUs">Contact Us</a>
		</div>

<div class="username-btn">
  <c:if test="${not empty sessionScope.clientID}">
    <a href="<c:url value='/client/profile'/>"> <%-- Change this line --%>
      <i class="fas fa-user"></i> My Profile
    </a>
  </c:if>
  <c:if test="${empty sessionScope.clientID}">
    <a href="<c:url value='/login'/>">
      <i class="fas fa-user"></i> Login
    </a>
  </c:if>
</div>
		
	</div>
	
</body>
</html>