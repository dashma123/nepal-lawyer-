<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Book an Appointment</title>

  <!-- client-home styles for header/footer -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />

  <!-- this page’s styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bookAppointment.css" />
</head>
<body>

  <!-- include your existing client header -->
  <jsp:include page="header.jsp"/>

  <div class="booking-container">
    <!-- Sidebar -->
    <aside class="lawyer-sidebar">
      <img src="${pageContext.request.contextPath}/resources/images/lawyer-placeholder.png"
           alt="Lawyer Photo" class="lawyer-photo" />

      <h2>Book Appointment with<br/>
        <span><c:out value="${lawyer.lawyerName}"/></span>
      </h2>

      <ul class="details-list">
        <li><strong>Lawyer Since:</strong> <c:out value="${lawyer.dateJoined}"/></li>
        <li><strong>Practices:</strong> <c:out value="${lawyer.district}"/></li>
        <li><strong>Phone:</strong> <c:out value="${lawyer.lawyerNumber}"/></li>
      </ul>

      <div class="social-media">
        <a href="#"><i class="fab fa-facebook-f"></i></a>
        <a href="#"><i class="fab fa-instagram"></i></a>
        <a href="#"><i class="fab fa-linkedin-in"></i></a>
        <a href="#"><i class="fab fa-whatsapp"></i></a>
      </div>
    </aside>

   <main class="appointment-main">
  <form action="${pageContext.request.contextPath}/bookingAppointment"
        method="post"
        class="appointment-form">

    <input type="hidden" name="clientID"
           value="${sessionScope.clientID}"/>
    <input type="hidden" name="lawyerID"
           value="${lawyer.lawyerID}"/>

    <div class="form-group">
      <label for="mode">Appointment Type</label>
      <select id="mode" name="mode" required>
        <option value="" disabled selected>Select Appointment Type</option>
        <option>In-Person</option>
        <option>Online</option>
      </select>
    </div>

    <div class="form-group">
      <label for="date">Select Your Date</label>
      <input type="date" id="date" name="date" value="${selectedDate}" required />
    </div>

    <!-- <<< Time Slot Buttons, exactly as before >>> -->
    <div class="form-group slots">
      <label>Select Your Time Slot</label>
      <div class="slots-container">

        <c:if test="${not empty bookedSlots}">
          <c:forEach var="slot" items="${bookedSlots}">
            <button type="button" class="slot booked">
              <c:out value="${slot.startTime}"/> – <c:out value="${slot.endTime}"/><br/>
              Booked
            </button>
          </c:forEach>
        </c:if>

        <!-- static examples of available slots -->
        <button type="button" class="slot available"
                data-start="11:00" data-end="11:30">
          11:00 – 11:30<br/>Book Appointment
        </button>
        <button type="button" class="slot available"
                data-start="12:00" data-end="12:30">
          12:00 – 12:30<br/>Book Appointment
        </button>
        <button type="button" class="slot available"
                data-start="13:00" data-end="13:30">
          13:00 – 13:30<br/>Book Appointment
        </button>
      </div>

      <!-- hidden fields to carry the chosen start/end -->
      <input type="hidden" id="startTime" name="startTime" />
      <input type="hidden" id="endTime"   name="endTime"   />
    </div>

    <div class="form-group">
      <label for="description">Notes (optional)</label>
      <textarea id="description" name="description" rows="3"></textarea>
    </div>

    <button type="submit" class="btn-submit">Confirm Appointment</button>
  </form>
</main>
  </div>

  <!-- client-home footer -->
  <jsp:include page="footer.jsp"/>

<script>
  document.querySelectorAll('.slot.available').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.slot').forEach(b => b.classList.remove('selected'));
      btn.classList.add('selected');
      document.getElementById('startTime').value = btn.dataset.start;
      document.getElementById('endTime').value   = btn.dataset.end;
    });
  });
</script>

</body>
</html>
