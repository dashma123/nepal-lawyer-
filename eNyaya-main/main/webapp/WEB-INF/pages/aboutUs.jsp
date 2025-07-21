<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>About Us</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutUs.css" />
</head>
<body>
  <jsp:include page="/WEB-INF/pages/header.jsp" />

  <main class="about-container">
    <section class="hero">
      <h1>ABOUT US</h1>
      <p class="subtitle">We provide expert legal aid and support for all your needs.</p>
      <p class="description">
        Largest online web portal for Lawyers in Nepal. Choose from 100's of lawyers across the major cities in Nepal. 
        FREE legal advice from top rated lawyers. Lawyers and law-firms can register and verify their profile for free and clients 
        (users) are able to search them by location, area of practice, city, gender, review and more. Clients are able to book 
        lawyers, ask questions, participate in discussion forum, and many more. This is the place to find one of the best 
        lawyers in the country. So, now, you can find them, hire them and rate them easily.
      </p>
    </section>

    <section class="features">
      <div class="feature-card">
        <i class="fas fa-balance-scale"></i>
        <h3>Free Consultation</h3>
        <p>Get a no-obligation consultation with our expert attorneys.</p>
      </div>
      <div class="feature-card">
        <i class="fas fa-gavel"></i>
        <h3>Experienced Lawyers</h3>
        <p>Our team has decades of combined experience across practice areas.</p>
      </div>
      <div class="feature-card">
        <i class="fas fa-headset"></i>
        <h3>24/7 Support</h3>
        <p>We are available around the clock to assist you when you need us.</p>
      </div>
    </section>

    <section class="showcase">
      <div class="showcase-image">
        <img src="${pageContext.request.contextPath}/resources/images/team_work.png" alt="Our Team at Work">
      </div>
      <div class="showcase-text">
        <button class="btn-outline">OUR TEAM AT WORK !!</button>
      </div>
    </section>

    <hr />

    <section class="team">
      <h2>Meet Our Team</h2>
      <p class="team-subtitle">Our dedicated legal professionals are here to help you.</p>
      <div class="team-grid">
        <!-- Static team cards without controller/model -->
        <div class="team-card">
          <img src="${pageContext.request.contextPath}/resources/images/aaliya.png" alt="Aaliya Singh Bhandari" />
          <h4>Aaliya Singh Bhandari</h4>
          <p class="role">Co-founder</p>
          <div class="social-icons">
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
          </div>
        </div>
        <div class="team-card">
          <img src="${pageContext.request.contextPath}/resources/images/dashma.png" alt="Dashma Shrestha" />
          <h4>Dashma Shrestha</h4>
          <p class="role">Co-founder</p>
          <div class="social-icons">
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
          </div>
        </div>
        <div class="team-card">
          <img src="${pageContext.request.contextPath}/resources/images/dipshikha.png" alt="Dipshikha Basnet" />
          <h4>Dipshikha Basnet</h4>
          <p class="role">Co-founder</p>
          <div class="social-icons">
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
          </div>
        </div>
        <div class="team-card">
          <img src="${pageContext.request.contextPath}/resources/images/shidharth.jpg" alt="Shidharth Kharga" />
          <h4>Shidharth Kharga</h4>
          <p class="role">Co-founder</p>
          <div class="social-icons">
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
          </div>
        </div>
        <div class="team-card">
          <img src="${pageContext.request.contextPath}/resources/images/shreejan.jpg" alt="Shreejan Giri" />
          <h4>Shreejan Giri</h4>
          <p class="role">Co-founder</p>
          <div class="social-icons">
            <a href="#"><i class="fab fa-twitter"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
          </div>
        </div>
      </div>
    </section>
  </main>

  <jsp:include page="/WEB-INF/pages/footer.jsp" />

</body>
</html>