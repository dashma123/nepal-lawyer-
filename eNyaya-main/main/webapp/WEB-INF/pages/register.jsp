<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register</title>
  <link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/register.css" />
  <style>
    .hidden { display: none; }
  </style>
</head>
<body>
  <div class="container">

    <!-- Left Side Text -->
    <div class="info-panel">
      <h1>Get the Legal Help<br>You Deserve</h1>
      <p>-- Anytime, Anywhere</p>
      <p>Access legal services and professional support with ease. Whether you're seeking justice or offering it, our platform connects clients with trusted lawyers in just a few clicks.</p>
    </div>
    
    <!-- Registration Form -->
    <div class="form-panel">
      <h1>Create an Account</h1>
  
      <div class="toggle-buttons">
        <button type="button" id="clientBtn" class="active" aria-pressed="true">Client</button>
        <button type="button" id="lawyerBtn" aria-pressed="false">Lawyer</button>
      </div>
  
      <form id="registrationForm" method="post" action="${pageContext.request.contextPath}/register">
        <!-- Hidden role field -->
        <input type="hidden" id="role" name="role" value="Client" />

        <!-- Client Fields -->
        <div id="clientFields">
          <div class="form-group">
            <label for="clientName">Name</label>
            <input type="text" id="clientName" name="clientName" placeholder="Enter your full name" required>
          </div>
          <div class="form-group">
            <label for="clientEmail">Email</label>
            <input type="email" id="clientEmail" name="clientEmail" placeholder="Enter your email address" required>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="clientPhone">Phone</label>
              <input type="text" id="clientPhone" name="clientPhone" placeholder="98-XXXXXXXX" required>
            </div>
            <div class="form-group">
              <label for="clientGender">Gender</label>
              <select id="clientGender" name="clientGender">
                <option value="" disabled selected>Choose</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="clientAddress">Address</label>
            <input type="text" id="clientAddress" name="clientAddress" placeholder="Enter your address">
          </div>
          <div class="form-group">
            <label for="clientDOB">Date of Birth</label>
            <input type="date" id="clientDOB" name="clientDOB" placeholder="DD/MM/YYYY">
          </div>
        </div>

        <!-- Lawyer Fields (hidden by default) -->
        <div id="lawyerFields" class="hidden">
          <div class="form-group">
            <label for="lawyerName">Name</label>
            <input type="text" id="lawyerName" name="lawyerName" placeholder="Enter your full name" required>
          </div>
          <div class="form-group">
            <label for="lawyerEmail">Email</label>
            <input type="email" id="lawyerEmail" name="lawyerEmail" placeholder="Enter your email address" required>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="lawyerPhone">Phone</label>
              <input type="text" id="lawyerPhone" name="lawyerPhone" placeholder="98-XXXXXXXX" required>
            </div>
            <div class="form-group">
              <label for="lawyerGender">Gender</label>
              <select id="lawyerGender" name="lawyerGender">
                <option value="" disabled selected>Choose</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="licenseNumber">License Number</label>
              <input type="text" id="licenseNumber" name="licenseNumber" placeholder="Enter license number">
            </div>
            <div class="form-group">
              <label for="dateJoined">Date Joined</label>
              <input type="date" id="dateJoined" name="dateJoined">
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="district">District</label>
              <input type="text" id="district" name="district" placeholder="Enter district">
            </div>
            <div class="form-group">
              <label for="province">Province</label>
              <select id="province" name="province">
                <option value="" disabled selected>Choose</option>
                <option value="koshi">Koshi</option>
                <option value="madhesh">Madhesh</option>
                <option value="bagmati">Bagmati</option>
                <option value="gandaki">Gandaki</option>
                <option value="lumbini">Lumbini</option>
                <option value="karnali">Karnali</option>
                <option value="sudurpaschim">Sudurpashchim</option>
              </select>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" placeholder="Enter new password" required>
        </div>
        <div class="form-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your new password" required>
        </div>

        <button type="submit" class="submit-btn">Create an Account</button>

        <div class="login-link">
          Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a>
        </div>
      </form>
    </div>
  </div>

  <!-- Error Messages -->
  <%
    String error = request.getParameter("error");
    if ("email_exists".equals(error)) {
  %>
    <p style="color:red;">This email is already registered.</p>
  <% 
    } else if ("password_mismatch".equals(error)) {
  %>
    <p style="color:red;">Passwords do not match.</p>
  <%
    } else if ("phone".equals(error)) {
  %>
    <p style="color:red;">Invalid phone number format.</p>
  <%
    }
  %>

  <!-- JavaScript to toggle form -->
  <script>
  
  const clientBtn = document.getElementById("clientBtn");
  const lawyerBtn = document.getElementById("lawyerBtn");
  const clientFields = document.getElementById("clientFields");
  const lawyerFields = document.getElementById("lawyerFields");

  clientBtn.addEventListener("click", () => {
    clientBtn.classList.add("active");
    lawyerBtn.classList.remove("active");
    clientFields.classList.remove("hidden");
    lawyerFields.classList.add("hidden");
    document.getElementById("role").value = "Client";
  });

  lawyerBtn.addEventListener("click", () => {
    lawyerBtn.classList.add("active");
    clientBtn.classList.remove("active");
    clientFields.classList.add("hidden");
    lawyerFields.classList.remove("hidden");
    document.getElementById("role").value = "Lawyer";
  });
  
 
  </script>
</body>
</html>
