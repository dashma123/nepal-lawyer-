<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Manage User</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminManageUser.css" />
</head>
<body>
	<div class="sidebar">
		<button onclick="location.href='${pageContext.request.contextPath}/adminManageUser'">Dashboard</button>
		<button class="active" >Manage Users</button>
		<button>Verify Lawyers</button>
		<button>Manage Appointment</button>
	</div>

	<div class="manage-users-container">
		<h2>Manage Users</h2>

	  <div class="toggle-buttons">
			<button id="clientBtn" class="active" type="button" onclick="showTable('client')">Clients</button>
			<button id="lawyerBtn" type="button" onclick="showTable('lawyer')">Lawyers</button>
		</div>

	<form class="filters" action="adminManageUser" method="get"> <%-- Corrected action URL --%>
			<input type="hidden" name="role" id="searchRole" value="${selectedRole}"> <%-- Hidden field for current role --%>
			<input type="text" name="id" placeholder="Search by ID" value="${param.id}"> <%-- Corrected parameter name --%>
			<input type="text" name="name" placeholder="Search by Name" value="${param.name}"> <%-- Corrected parameter name --%>
			<input type="text" name="email" placeholder="Search by Email" value="${param.email}"> <%-- Corrected parameter name --%>
			<input type="text" name="phone" placeholder="Search by Phone" value="${param.phone}"> <%-- Corrected parameter name --%>
			<button type="submit">Search</button>
		</form>



		<table class="user-table" id="clientTable">
			<thead>
				<tr>
					<th>No.</th>
					<th>Client ID</th>
					<th>Client Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Remove</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="client" items="${clientList}" varStatus="i">
					<tr>
						<td>${i.index + 1}</td>
						<td>${client.clientID}</td>
						<td>${client.clientName}</td>
						<td>${client.clientEmail}</td>
						<td>${client.clientNumber}</td> <%-- CRITICAL FIX: Use client.clientNumber --%>
						<td>
							<form action="adminManageUser" method="post" onsubmit="return confirm('Are you sure you want to remove this client?');">
								<input type="hidden" name="action" value="delete">
								<input type="hidden" name="role" value="client">
								<input type="hidden" name="id" value="${client.clientID}">
								<button type="submit" class="cancel-btn">Remove</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<table class="user-table hidden" id="lawyerTable">
			<thead>
				<tr>
					<th>No.</th>
					<th>Lawyer ID</th>
					<th>Lawyer Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Remove</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="lawyer" items="${lawyerList}" varStatus="i">
					<tr>
						<td>${i.index + 1}</td>
						<td>${lawyer.lawyerID}</td>
						<td>${lawyer.lawyerName}</td>
						<td>${lawyer.lawyerEmail}</td>
						<td>${lawyer.lawyerNumber}</td> <%-- CRITICAL FIX: Use lawyer.lawyerNumber --%>
						<td>
							<form action="adminManageUser" method="post" onsubmit="return confirm('Are you sure you want to remove this lawyer?');">
								<input type="hidden" name="action" value="delete">
								<input type="hidden" name="role" value="lawyer">
								<input type="hidden" name="id" value="${lawyer.lawyerID}">
								<button type="submit" class="cancel-btn">Remove</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script>
	    function showTable(role) {
	      const clientTable = document.getElementById("clientTable");
	      const lawyerTable = document.getElementById("lawyerTable");

	      document.getElementById("clientBtn").classList.remove("active");
	      document.getElementById("lawyerBtn").classList.remove("active");

	      if (role === "client") {
	        clientTable.classList.remove("hidden");
	        lawyerTable.classList.add("hidden");
	        document.getElementById("clientBtn").classList.add("active");
	      } else {
	        clientTable.classList.add("hidden");
	        lawyerTable.classList.remove("hidden");
	        document.getElementById("lawyerBtn").classList.add("active");
	      }

	      // Update the hidden role input in the search form
	      document.getElementById("searchRole").value = role;

	      // Submit the form to re-fetch data based on the selected role
	      document.querySelector('.filters').submit();
	    }

	    // Set the active button and display the correct table on page load based on selectedRole
	    document.addEventListener('DOMContentLoaded', function() {
	        const selectedRole = "${selectedRole}"; // Get the role from the request attribute
	        if (selectedRole === "lawyer") {
	            showTable("lawyer");
	        } else {
	            showTable("client"); // Default to client if no role or client is selected
	        }
	    });
	</script>
</body>
</html>