<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LogIn</title>
<!-- Set contextPath variable for reuse -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/login.css" />
</head>
<body>
<div class="container">
	<div class="login-box">
	
		<div class="logo">
                <img src="${contextPath}/resources/images/logo.png" alt="Logo" width="100" height="auto">
        </div>
        
		<h2>Login</h2>
		<form action="${pageContext.request.contextPath}/login" method="post">
		
			<div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Password" required>
                </div>
			
			<div class="remember-me-container">
	            <label>
	                <input type="checkbox" name="remember"> Remember me
	            </label>
            </div>
                
			<button type="submit" class="login-button">Login</button>
			
			<div class="links-below-button">
            	<p>Don't have an account? <a href="register.jsp">Register here</a></p>
            </div>
            
		</form>

		<!-- Display error message if available -->
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
	</div>
</div>
</body>
</html>