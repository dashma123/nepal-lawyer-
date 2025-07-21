<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>Find Lawyers</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/findLawyers.css"/>
</head>
<body>
  <jsp:include page="header.jsp"/>

  <main class="main-content">
   <aside class="filters">
  <h2>FILTER</h2>

  <!-- SEARCH FORM -->
  <form action="<c:url value='/client/findLawyers'/>" method="get" class="filter-search-form">
    <div class="filter-group">
      <input
        type="text"
        name="q"
        placeholder="Search lawyers…"
        value="${searchQuery}"
        class="filters-input"/>
      <button type="submit" class="search-btn">
        <i class="fas fa-search"></i>
      </button>
    </div>

    <!-- Practice -->
    <div class="filter-group">
      <select id="filterPractice" name="filterPractice" class="filters-select">
        <option value="">Practice</option>
        <option>Criminal Law</option>
        <option>Family Law</option>
        <option>Corporate Law</option>
      </select>
    </div>

    <!-- Language -->
    <div class="filter-group">
      <select id="filterLanguage" name="filterLanguage" class="filters-select">
        <option value="">Language</option>
        <option>English</option>
        <option>Nepali</option>
        <option>Hindi</option>
      </select>
    </div>

    <!-- Service -->
    <div class="filter-group">
      <select id="filterService" name="filterService" class="filters-select">
        <option value="">Service</option>
        <option>Consultation</option>
        <option>Drafting Documents</option>
        <option>Court Representation</option>
      </select>
    </div>

    <!-- Gender -->
    <div class="filter-group">
      <select id="filterGender" name="filterGender" class="filters-select">
        <option value="">Gender</option>
        <option>Any</option>
        <option>Male</option>
        <option>Female</option>
      </select>
    </div>

    <!-- Rating -->
    <div class="filter-group">
      <select id="filterRating" name="filterRating" class="filters-select">
        <option value="">Rating</option>
        <option>★ and up</option>
        <option>★★ and up</option>
        <option>★★★ and up</option>
        <option>★★★★ and up</option>
        <option>★★★★★</option>
      </select>
    </div>

    <!-- RESET -->
    <div class="filter-actions">
      <button type="reset" class="reset-btn">RESET</button>
    </div>
  </form>
</aside>

<section class="card-grid">   <!-- renamed to match clientHome.css -->
  <c:choose>
    <c:when test="${not empty lawyers}">
      <c:forEach var="lawyer" items="${lawyers}">
  <c:url var="bookUrl" value="/bookingAppointment">
    <c:param name="lawyerID" value="${lawyer.lawyerID}" />
  </c:url>
  <a href="${pageContext.request.contextPath}/bookingAppointment?lawyerID=${lawyer.lawyerID}"
   class="card-link">
    <div class="card">
      <img
        src="${pageContext.request.contextPath}/images/lawyer.png"
        alt="Lawyer Photo"
        class="top-image"/>
      <div class="card-content">
        <div class="card-title">
          <c:out value="${lawyer.lawyerName}"/>
        </div>
        <div class="card-description">
          Joined on <c:out value="${lawyer.dateJoined}"/><br/>
          Practice Area: <c:out value="${lawyer.district}"/>
        </div>
      </div>
    </div>
  </a>
</c:forEach>
    </c:when>
    <c:otherwise>
      <p>No lawyers found.</p>
    </c:otherwise>
  </c:choose>
</section>

  </main>

  
</body>
<jsp:include page="footer.jsp"/>
</html>