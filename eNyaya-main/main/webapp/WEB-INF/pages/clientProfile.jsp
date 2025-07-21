<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Client Profile</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/clientProfile.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/updateClientProfile.css" />  
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
    
      <div class="profile-card">
      
        <div class="profile-card__header">
          <div class="avatar"><i class="fas fa-user"></i></div>
          <div class="profile-card__info">
            <span><strong>Client ID:</strong> #<c:out value="${client.clientID}"/></span>
            <a href="#" class="btn-edit" onclick="openEditProfilePopup()">Edit Profile</a>
          </div>
        </div>
        
        <div class="profile-card__details">
          <div><strong>Full Name:</strong>   <c:out value="${client.clientName}"/></div>
          <div><strong>Email:</strong>         <c:out value="${client.clientEmail}"/></div>
          <div><strong>Date of Birth:</strong> <c:out value="${client.dateOfBirth}"/></div>
          <div><strong>Gender:</strong>        <c:out value="${client.gender}"/></div>
          <div><strong>Phone:</strong>         <c:out value="${client.clientNumber}"/></div>
          <div><strong>Address:</strong>       <c:out value="${client.clientAddress}"/></div>
        </div>
        
      </div>
    </main>
  </div>
  
   <div id="editProfilePopup" class="modal">
    <div class="modal-content">
      <span class="close-button">&times;</span>
      <h2>Edit Profile</h2>
      <form action="${pageContext.request.contextPath}/client/profile" method="post">

        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" name="email" value="${client.clientEmail}" required>
        </div>

        <div class="form-group">
          <label for="phone">Phone:</label>
          <input type="text" id="phone" name="number" value="${client.clientNumber}">
        </div>

        <div class="form-group">
          <label for="address">Address:</label>
          <input type="text" id="address" name="address" value="${client.clientAddress}">
        </div>

        <div class="form-group">
          <label for="password">New Password:</label>
          <input type="password" id="password" name="password">
        </div>

        <div class="form-actions">
          <button type="submit" class="save-button">Save Changes</button>
          <button type="button" class="cancel-button" onclick="closeEditProfilePopup()">Cancel</button>
        </div>
      
    </div>
  </div>

  <jsp:include page="footer.jsp" />

  <script>
  
  function openEditProfilePopup() {
	  document.getElementById("editProfilePopup").style.display = "block";
	}

	function closeEditProfilePopup() {
	  document.getElementById("editProfilePopup").style.display = "none";
	}

	document.addEventListener('DOMContentLoaded', function() {
	  const editButton = document.querySelector('.btn-edit');
	  if (editButton) {
	    editButton.addEventListener('click', function(event) {
	      event.preventDefault();
	      openEditProfilePopup();
	    });
	  }

	  const modal = document.getElementById("editProfilePopup");
	  window.addEventListener('click', function(event) {
	    if (event.target == modal) {
	      closeEditProfilePopup();
	    }
	  });

	  const closeButton = document.querySelector('.close-button');
	  if (closeButton) {
	    closeButton.addEventListener('click', closeEditProfilePopup);
	  }
	});
    
  </script>
  
</body>
</html>