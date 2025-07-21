<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Booking Confirmation</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/confirmation.css"/>
  <script>
    window.onload = function() {
      // Use the correct property names
      var msg = "Your appointment with " + "${lawyer.lawyerName}" +
                " on " + "${appointment.appointmentDate}" +
                " from " + "${appointment.startTime}" +
                " to " + "${appointment.endTime}" +
                " has been booked successfully.";
      alert(msg);
      // Redirect back to client home (matches your servlet mapping)
      window.location = "${pageContext.request.contextPath}/clientHome";
    };
  </script>
</head>
<body>
  <noscript>
    <div class="confirmation-fallback">
      <p>Your appointment has been booked.</p>
      <!-- No need for an ID param; clientHome pulls clientID from session -->
      <a href="${pageContext.request.contextPath}/clientHome"
         class="btn-home">Return to Home</a>
    </div>
  </noscript>
</body>
</html>
