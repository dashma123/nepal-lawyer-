<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Online Legal Aid – Home</title>

  <!-- font-awesome -->
<linkrel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-p4VQ9+Dd2/DuQY2Fd+mV4Ym6mUXqLx5rKuXumj1HHo9tG77+avkl6o3hB0jY5Y/r2BzWD3jvYqXHPYBvYlXK7Q==" crossorigin="anonymous"referrerpolicy="no-referrer"/>

  <!-- your shared CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerIndex.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/clientHome.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
  <!-- same header.jsp you already have -->
  <jsp:include page="headerIndex.jsp"/>

  <!-- search + hero -->
  <div class="search-section">
    <div class="search-bar">
      <input type="text" placeholder="Search Lawyer" />
      <input type="text" placeholder="City, District or Province" />
      <button type="button" class="search-btn">
        <i class="fas fa-search"></i>
      </button>
    </div>
  </div>

  <!-- features -->
  <div class="features">
    <div class="feature-box">
      <h3>300+ Verified Lawyers</h3>
      <p>Across 25+ legal specialties, ready to help.</p>
    </div>
    <div class="feature-box">
      <h3>Case Tracking in Real Time</h3>
      <p>View status updates and reports anytime.</p>
    </div>
    <div class="feature-box">
      <h3>Easy Appointment Booking</h3>
      <p>Pick your lawyer and schedule online—fast.</p>
    </div>
    <div class="feature-box">
      <h3>100% Secure & Confidential</h3>
      <p>Your data and conversations are fully protected.</p>
    </div>
  </div>

  <div class="featured">
    <h1>Appointment with Our Featured Lawyers</h1>
    <div class="card-grid">
<c:forEach var="lawyer" items="${lawyers}">
  <a href="${pageContext.request.contextPath}/client/book?clientId=${client.clientID}&lawyerId=${lawyer.lawyerID}" class="card-link">
          <div class="card">
            <img src="${pageContext.request.contextPath}/images/lawyer.png" class="top-image"/>
            <div class="card-content">
              <div class="card-title">
                <c:out value="${lawyer.lawyerName}"/>
              </div>
              <div class="card-description">
                <c:out value="${lawyer.dateJoined}"/> Date Joined<br/>
                Practice Area: <c:out value="${lawyer.district}"/>
              </div>
            </div>
          </div>
        </a>
      </c:forEach>
    </div>
  </div>

  <div class="premium">
    <h1>Appointment with Our Premium Lawyers</h1>
    <div class="card-grid">
<c:forEach var="lawyer" items="${lawyers}">
  <a href="${pageContext.request.contextPath}/client/book?clientId=${client.clientID}&lawyerId=${lawyer.lawyerID}" class="card-link">
          <div class="card">
            <img src="${pageContext.request.contextPath}/images/lawyer.png" class="top-image"/>
            <div class="card-content">
              <div class="card-title">
                <c:out value="${lawyer.lawyerName}"/>
              </div>
              <div class="card-description">
                <c:out value="${lawyer.dateJoined}"/> Date Joined<br/>
                Practice Area: <c:out value="${lawyer.district}"/>
              </div>
            </div>
          </div>
        </a>
      </c:forEach>
    </div>
  </div>

  <!-- extra bottom calls to action -->
  <div class="call-to-action">
    <div class="cta-box">
      <h2>Become Our Client</h2>
      <a href="<c:url value='/register?role=client'/>" class="btn">Register Now</a>
    </div>
    <div class="cta-box">
      <h2>Become Our Lawyer</h2>
      <a href="<c:url value='/register?role=lawyer'/>" class="btn">Register Now</a>
    </div>
  </div>

  <!-- now include your shared footer.jsp (which we’ll enrich next) -->
  <jsp:include page="footer.jsp"/>
</body>
</html>


