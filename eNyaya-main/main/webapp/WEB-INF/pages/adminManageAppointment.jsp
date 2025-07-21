<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
 <meta charset="UTF-8" />
 <title>Manage Appointments</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminManageAppointment.css" />
</head>
<body>

  <div class="sidebar">
    <button onclick="location.href='${pageContext.request.contextPath}/adminDashboard'">Dashboard</button>
    <button onclick="location.href='${pageContext.request.contextPath}/adminManageUser'" >Manage Users</button>
    <button>Verify Lawyers</button>
    <button class="active">Manage Appointment</button>
  </div>

  <div class="manage-appointments-container">
    <h2>Manage Appointments</h2>

    <div class="filters">
      <input type="date" placeholder="Enter date" />
      <input type="text" placeholder="Enter Client Name" />
      <input type="text" placeholder="Enter Lawyer Name" />
      <select>
        <option value="" disabled selected>Select status</option>
        <option value="completed">Completed</option>
        <option value="scheduled">Scheduled</option>
      </select>
    </div>

    <table class="user-table">
      <thead>
        <tr>
          <th>S.N.</th>
          <th>Appointment ID</th>
          <th>Client name</th>
          <th>Lawyer name</th>
          <th>Date</th>
          <th>Time</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="appt" items="${appointmentList}" varStatus="loop">
          <tr>
            <td>${loop.index + 1}</td>
            <td>${appt.AppointmentID}</td>
            <td>${appt.clientName}</td>
            <td>${appt.lawyerName}</td>
            <td class="date-cell">${appt.date}</td>
            <td class="time-cell">${appt.time}</td>
            <td>${appt.status}</td>
            <td>
              

              <!-- Reschedule button -->
              <button type="button" class="reschedule-btn" onclick="openRescheduleModal(this)">Reschedule</button>


              <!-- Cancel form -->
              <form method="post" action="AppointmentController">
                <input type="hidden" name="action" value="cancel" />
                <input type="hidden" name="appointmentId" value="${app.appointmentId}" />
                <button type="submit" class="remove-btn">Remove</button>
              </form>
            </td>
          </tr>
        </c:forEach>

        <c:if test="${empty appointmentList}">
          <tr>
            <td colspan="9" style="text-align:center;">No appointments found.</td>
          </tr>
        </c:if>
      </tbody>
    </table>
  </div>

	<c:if test="${not empty appointmentList}">
	 <!-- Modal -->
	 <div class="modal-overlay" id="rescheduleModal" style="display: none;">
	   <div class="modal">
	     <h3>Reschedule Appointment</h3>
	     <!-- Modal form -->
	     <form id="rescheduleForm" method="post" action="AppointmentController">
	       <input type="hidden" id="rescheduleAppointmentId" name="appointmentId" />
	       <input type="hidden" name="action" value="reschedule" />
	
	       <label for="newDate">Select new date:</label>
	       <input type="date" id="newDate" name="newDate" required />
	
	       <label>Select time slot:</label>
	       <div class="time-slots">
	         <button type="button" class="time-slot-btn" data-time="11:00 - 12:00">11:00 - 12:00</button>
	         <button type="button" class="time-slot-btn" data-time="12:00 - 13:00">12:00 - 13:00</button>
	         <button type="button" class="time-slot-btn" data-time="14:00 - 15:00">14:00 - 15:00</button>
	         <button type="button" class="time-slot-btn" data-time="15:00 - 16:00">15:00 - 16:00</button>
	       </div>
	
	       <input type="hidden" id="selectedTime" name="selectedTime" required />
	
	       <div class="modal-buttons">
	         <button type="submit" class="save-btn">Save</button>
	         <button type="button" class="close-btn" onclick="closeRescheduleModal()">Cancel</button>
	       </div>
	     </form>
	   </div>
	 </div>
	</c:if>
	
  <script>
  let currentRow = null;

  function openRescheduleModal(button) {
    currentRow = button.closest("tr");
    const appointmentId = currentRow.querySelector("td:nth-child(2)").textContent.trim();
    document.getElementById("rescheduleAppointmentId").value = appointmentId;

    const dateText = currentRow.querySelector(".date-cell").textContent.trim();
    // convert dd-mm-yyyy to yyyy-mm-dd for input type=date
    const parts = dateText.split("-");
    const formattedDate = `${parts[2]}-${parts[1]}-${parts[0]}`;
    document.getElementById("newDate").value = formattedDate;

    document.getElementById("selectedTime").value = "";
    document.querySelectorAll(".time-slot-btn").forEach(btn => btn.classList.remove("active"));

    document.getElementById("rescheduleModal").style.display = "flex";
  }

  function closeRescheduleModal() {
    document.getElementById("rescheduleModal").style.display = "none";
    currentRow = null;
  }

  // Time slot button click handling
  document.querySelectorAll(".time-slot-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      document.querySelectorAll(".time-slot-btn").forEach(b => b.classList.remove("active"));
      btn.classList.add("active");
      document.getElementById("selectedTime").value = btn.dataset.time;
    });
  });

  // Optional: validate time slot selected before submit
  document.getElementById("rescheduleForm").addEventListener("submit", function(e) {
    if (!document.getElementById("selectedTime").value) {
      e.preventDefault();
      alert("Please select a time slot.");
    }
  });

  </script>

  <script src="${pageContext.request.contextPath}/scripts/rescheduleModal.js"></script>
</body>
</html>
