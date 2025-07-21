<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lawyer Dashboard</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lawyerDashboard.css" />
</head>
<body>
	<nav>
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
		
		
		<div class="username-btn">
		  <c:if test="${not empty sessionScope.lawyerID}">
		    <a href="<c:url value='/client/profile'/>">
		      <i class="fas fa-user"></i> My Profile
		    </a>
		  </c:if>
		</div>
			
	</div>
	</nav>
	<main>
	<div class="sidebar">
		<button class="active">Dashboard</button>
		<button class="sidebar-button" onclick="location.href='${pageContext.request.contextPath}/lawyerAppointment'">Appointments</button>
	</div>

	<div class="container">
		<h2>Hi! ${sessionScope.lawyerName}</h2>

		<div class="summary-cards">
			<div class="card">
				<div class="card-title">Total Appointments</div>
				<div class="card-value">${totalAppointments}</div>
			</div>
			<div class="card">
				<div class="card-title">Ongoing Appointments</div>
				<div class="card-value">${ongoingAppointments}</div>
			</div>
			<div class="card">
				<div class="card-title">Today Appointments</div>
				<div class="card-value">${todayAppointments}</div>
			</div>
			<div class="card">
				<div class="card-title">Rating</div>
				<div class="card-value">${rating}</div>
			</div>
		</div>

		<div class="table-section">
			<h2>Today Appointments</h2>
			<table>
				<thead>
					<tr>
						<th>No.</th>
						<th>Client ID</th>
						<th>Name</th>
						<th>Practice</th>
						<th>Date</th>
						<th>Time</th>
						<th>Mode</th>
						<th>Status</th>
					</tr>
				</thead>
			      
				<tbody>
					<c:forEach var="toapp" items="${todayList}" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${toapp.clientID}</td>
						<td>${toapp.clientName}</td>
						<td>${toapp.appointmentDate}</td> 
						<td>${toapp.startTime}</td> 
						<td>${toapp.mode}</td>
						<td>${toapp.status}</td>
						<td>${toapp.appointmentDescription}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="table-section">
			<h2>Ongoing Appointment</h2>
			<table>
				<thead>
					<tr>
						<th>No.</th>
						<th>Client ID</th>
						<th>Court Date</th>
						<th>Time</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="onapp" items="${ongoingList}" varStatus="loop">
					<tr>
						<td>${loop.index + 1}</td>
						<td>${onapp.clientID}</td>
						<td>${onapp.appointmentDate}</td>
						<td>${onapp.startTime}</td>      
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="table-section">
			<h2>Upcoming Appointment</h2>
			<table>
				<thead>
					<tr>
					<th>No.</th>
					<th>Client ID</th>
					<th>Court Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="upapp" items="${upcomingList}" varStatus="loop">
						<tr>
							<td>${loop.index + 1}</td>
							<td>${upapp.clientID}</td>
							<<td>${upapp.appointmentDate}</td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</main>
  <jsp:include page="footer.jsp" />
</body>
</html>