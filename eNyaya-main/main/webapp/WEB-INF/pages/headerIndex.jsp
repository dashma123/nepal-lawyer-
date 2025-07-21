<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />	
		<style>
			body {
				margin: 0;
				font-family: Arial, sans-serif;
			}
			
			.top-bar {
				background: white;
				padding: 10px 20px;
				display: flex;
				justify-content: flex-end;
				align-items: center;
				border-bottom: 1px solid #ccc;
			}
			
			.top-bar a {
				color: inherit;
				text-decoration: none;
			}
			
			.top-bar i {
				margin-left: 15px;
				font-size: 18px;
			}
			
			.language-dropdown {
				position: relative;
				margin-left: 15px;
			}
			
			.language-select {
				display: flex;
				align-items: center;
				cursor: pointer;
			}
			
			.language-select span {
				margin-right: 5px;
			}
			
			.language-options {
				position: absolute;
				top: 100%;
				right: 0;
				background-color: white;
				border: 1px solid #ccc;
				border-top: none;
				z-index: 10;
				display: none;
				min-width: 100px;
			}
			
			.language-options a {
				display: block;
				padding: 8px 15px;
				text-decoration: none;
				color: black;
				text-align: left;
			}
			
			.language-options a:hover {
				background-color: #f0f0f0;
			}
			
			.language-dropdown:hover .language-options {
				display: block;
			}
			
			.nav-bar {
				background-color: #1c2f50;
				display: flex;
				justify-content: space-between;
				align-items: center;
				padding: 10px 20px;
				color: white;
			}
			
			.logo-section {
				display: flex;
				align-items: center;
				margin-right: 40px;
			}
			
			.logo-section a {
				color: inherit;
				text-decoration: none;
			}
			
			.logo-section img {
				height: 40px;
				margin-right: 10px;
			}
			
			.logo-text {
				font-size: 12px;
				line-height: 1.5;
			}
			
			.nav-links {
				display: flex;
				gap: 30px;
			}
			
			.nav-links a {
				color: #ccc;
				text-decoration: none;
			}
			
			.nav-links a:hover {
				color: #fff;
			}
			
			.nav-links div {
				font-size: 14px;
			}
			
			.username-btn {
				border: 1px solid white;
				border-radius: 20px;
				padding: 5px 15px;
				display: flex;
				align-items: center;
			}
			
			.username-btn a {
				color: inherit;
				text-decoration: none;
				display: flex;
				align-items: center;
			}
			
			.username-btn i {
				margin-right: 8px;
}
</style>
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
			<a href="#">
				<img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="logo">
			</a>

			<div class="logo-text">
				Online<br>
				Legal Aid<br>
				System
			</div>

		</div>

		<div class="nav-links">
			<a href="#">Find Lawyers</a>
			<a href="#">Court</a>
			<a href="<c:url value='/aboutus'/>">About Us</a>
			<a href="#">Contact Us</a>
		</div>
		
  <div class="auth-links">
    <a href="<c:url value='/login'/>"><i class="fas fa-sign-in-alt"></i> Log In</a>
    <a href="<c:url value='/register'/>" class="register-btn">Register</a>
  </div>
  </div>
</body>
</html>