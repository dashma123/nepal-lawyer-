<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contactUs.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
	<script>
        window.onload = function() {
            var urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('success') === 'true') {
                alert('Thank you for contacting us. We will get back to you soon!');
            } else if (urlParams.get('success') === 'false') {
                alert('There was an error submitting your message. Please try again.');
            }
        }
    </script>
</head>
<body>
	<jsp:include page="header.jsp" />

	<h2 class="section-title">CONTACT US</h2>

	<section class="contact-icons">
		<div class="icon-box">
			<i class="fas fa-phone"></i>
			<p>+977 9083712491<br><br>+977 9827985687</p>
		</div>
		<div class="icon-box">
			<i class="fas fa-map-marker-alt"></i>
			<p>Kamalpokhari,<br><br>Kathmandu, Nepal</p>
		</div>
		<div class="icon-box">
			<i class="fas fa-envelope"></i>
			<p>eNyaya@gmail.com<br><br>eNyaya@eNyaya.com</p>
		</div>
	</section>

	<div class="social-section">
		<p>We are also available at:</p>
		<div class="social-icons">
			<i class="fab fa-facebook"></i>
			<i class="fab fa-instagram"></i>
		</div>
	</div>

	<section class="form-section">
		<h2>Get in touch with us.</h2>
		<form action="${pageContext.request.contextPath}/contactUs" method="post">
			<div class="row">
				<div class="field">
					<label for="first-name">First Name</label>
					<input type="text" id="first-name" name="firstName" placeholder="Enter your first name" required>
				</div>
				<div class="field">
					<label for="last-name">Last Name</label>
					<input type="text" id="last-name" name="lastName" placeholder="Enter your last name" required>
				</div>
			</div>
			
			<div class="row">
				<div class="field">
					<label for="email">Email Address</label>
					<input type="email" id="email" name="email" placeholder="example@gmail.com" required>
				</div>
				<div class="field">
					<label for="phone">Phone Number</label>
					<input type="text" id="phone" name="phone" placeholder="98-XXXXXXXXXX" required>
				</div>
			</div>
			
			<div class="row">
				<div class="field full">
					<label for="subject">Subject</label>
					<input type="text" id="subject" name="subject" placeholder="Enter message subject" required>
				</div>
			</div>
			
			<div class="row">
				<div class="field full">
					<label for="message">Message</label>
					<textarea id="message" name="message" placeholder="Enter your message" required></textarea>
				</div>
			</div>
			
			<button type="submit">Submit</button>
		</form>

	</section>
	
	<section class="map-section">
		<iframe
		src="https://maps.google.com/maps?q=Nakkhu%20Bazar,%20Kathmandu&t=&z=13&ie=UTF8&iwloc=&output=embed"
		width="100%"
		height="300"
		frameborder="0"
		style="border:0;"
		allowfullscreen>
		</iframe>
	</section>
	
	<jsp:include page="footer.jsp" />
	
	
</body>
</html>