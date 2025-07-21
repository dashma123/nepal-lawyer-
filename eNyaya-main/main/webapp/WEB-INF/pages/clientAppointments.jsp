<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Appointments</title>
  
  <!-- Reuse sidebar & layout styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientProfile.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientAppointments.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
  <jsp:include page="header.jsp" />

  <div class="layout">
    <aside class="sidebar">
      <ul>
        <li><a href="${pageContext.request.contextPath}/client/profile">User Profile</a></li>
        <li><a href="${pageContext.request.contextPath}/client/appointments">Appointments</a></li>
      </ul>
      <div class="logout">
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
      </div>
    </aside>

    <main class="content">
      <div class="appointments-container">
        <h1>My Appointments</h1>
        <c:if test="${not empty appointments}">
          <table>
            <thead>
              <tr>
                <th>No.</th>
                <th>Lawyer Name</th>
                <th>Date</th>
                <th>Time</th>
                <th>Mode</th>
                <th>Status</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="appointment" items="${appointments}" varStatus="loop">
                <tr>
                  <td><c:out value="${loop.index + 1}"/></td>
                  <td><c:out value="${appointment.lawyerName}"/></td>
                  <td><c:out value="${appointment.appointmentDate}"/></td>
                  <td><c:out value="${appointment.startTime}"/> - <c:out value="${appointment.endTime}"/></td>
                  <td><c:out value="${appointment.mode}"/></td>
                  <td><c:out value="${appointment.status}"/></td>
                  <td><c:out value="${appointment.description}"/></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:if>
        <c:if test="${empty appointments}">
          <p>No appointments booked yet.</p>
        </c:if>
      </div>
    </main>
  </div>

  <jsp:include page="footer.jsp" />
</body>
</html>