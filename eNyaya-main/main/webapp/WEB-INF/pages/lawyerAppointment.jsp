<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lawyer Appointments</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lawyerAppointments.css" />
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
    <button class="sidebar-button" onclick="location.href='${pageContext.request.contextPath}/lawyerDashboard'">Dashboard</button>
    <button class="active">Appointments</button>
  </div>


  <div class="container">
    <h2>Appointments</h2>

    <table class="user-table">
      <thead>
        <tr>
          <th>No.</th>
          <th>Appointment ID</th>
          <th>Client ID</th>
          <th>Client Name</th>
          <th>Date</th>
          <th>Time</th>
          <th>Status</th>
          <th>Description</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="app" items="${appointmentList}" varStatus="loop">
          <tr>
			  <td>${loop.index + 1}</td>
			  <td>${app.appointmentID}</td>
			  <td>${app.clientID}</td>
			  <td>${app.clientName}</td>
			  <td>${app.appointmentDate}</td>
			  <td>${app.startTime} - ${app.endTime}</td>
			  <td>${app.status}</td>
			  <td>${app.description}</td>
			</tr>

        </c:forEach>
        <c:if test="${empty appointmentList}">
		  <tr><td colspan="8" style="text-align:center;">No appointments found.</td></tr>
		</c:if>

      </tbody>
    </table>
  </div>
  </main>
    <jsp:include page="footer.jsp" />
</body>
</html>