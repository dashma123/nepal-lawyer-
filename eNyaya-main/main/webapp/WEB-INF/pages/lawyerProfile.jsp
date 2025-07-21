<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Lawyer Profile</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lawyerProfile.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/updateLawyerProfile.css" />  
</head>

<body>

  <jsp:include page="header.jsp" />

  <div class="layout">
  
    <aside class="sidebar">
      <ul>
        <li><a href="#">User Profile</a></li>
      </ul>
    </aside>

    <main class="content">
    
      <div class="profile-card">
      
        <div class="profile-card__header">
          <div class="avatar"><i class="fas fa-user"></i></div>
          <div class="profile-card__info">
            <span><strong>Lawyer ID:</strong> #<c:out value="${lawyer.id}"/></span>
            <a href="#" class="btn-edit" onclick="openEditProfilePopup()">Edit Profile</a>
          </div>
        </div>
        
        <div class="profile-card__details">
          <div><strong>Full Name:</strong>   <c:out value="${lawyer.name}"/></div>
          <div><strong>License Number:</strong> <c:out value="${lawyer.license}"/></div>
          <div><strong>Experience Year:</strong> <c:out value="${lawyer.expYear}"/> years</div>
          <div><strong>Email:</strong>         <c:out value="${lawyer.email}"/></div>
          <div><strong>Date of Birth:</strong> <c:out value="${lawyer.dob}"/></div>
          <div><strong>Gender:</strong>        <c:out value="${lawyer.gender}"/></div>
          <div><strong>Phone:</strong>         <c:out value="${lawyer.phone}"/></div>
          <div><strong>Address:</strong>       <c:out value="${lawyer.address}"/></div>
          <div><strong>District:</strong>      <c:out value="${lawyer.district}"/></div>
          <div><strong>Province:</strong>      <c:out value="${lawyer.province}"/></div>
        </div>
        
      </div>
    </main>
  </div>
  
   <div id="editProfilePopup" class="modal">
   
    <div class="modal-content">
    
      <span class="close-button">&times;</span>
      
      <h2>Edit Profile</h2>
      
      <form action="${pageContext.request.contextPath}/lawyer/profile" method="post">
      
        <input type="hidden" name="id" value="${lawyer.id}">

        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" name="email" value="${lawyer.email}">
        </div>

        <div class="form-group">
          <label for="phone">Phone:</label>
          <input type="text" id="phone" name="phone" value="${lawyer.phone}">
        </div>

        <div class="form-group">
          <label for="address">Address:</label>
          <input type="text" id="address" name="address" value="${lawyer.address}">
        </div>

        <div class="form-group">
          <label for="district">District:</label>
          <input type="text" id="district" name="district" value="${lawyer.district}">
        </div>

        <div class="form-group">
          <label for="province">Province:</label>
          <input type="text" id="province" name="province" value="${lawyer.province}">
        </div>

        <div class="form-actions">
          <button type="submit" class="save-button">Save Changes</button>
          <button type="button" class="cancel-button" onclick="closeEditProfilePopup()">Cancel</button>
        </div>
        
      </form>
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