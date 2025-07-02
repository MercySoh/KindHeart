<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String currentUser = (String) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dry Goods Donation Platform</title>
  <link rel="stylesheet" href="css/index.css">
</head>

<body>

<header>
  <div class="container">
    <a href="index.jsp"><img src="img/logo.png" alt="Kind Heart donation logo" style="float:left;width:120px;height:100px;"></a>
    <h1>Kind Heart Dry Goods Donation</h1>
    <nav>
      <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="comingSoon.jsp">About</a></li>
        <li><a href="login.jsp">Login</a></li>
        <li><a href="register.jsp">Sign Up</a></li>

      </ul>
    </nav>
  </div>
</header>

<img src="img/landing.jpg" alt="landing image" style="width:100%; height: 150%;">

<main>
  <section class="intro">
    <div class="container">
      <h2>Kind Heart — Giving Made Simple.</h2>
      <p>Donate or request dry goods to help those in your community who need it most.</p>

      <div class="cta-buttons">
        <button class="donate-btn">
          <a href="<%= (currentUser != null) ? "donate.jsp" : "login.jsp" %>">Donate Now</a>
        </button>
        <button class="request-btn">
          <a href="<%= (currentUser != null) ? "request.jsp" : "login.jsp" %>">Request Donations</a>
        </button>
      </div>


    </div>
  </section>

  <section class="how-it-works">
    <div class="container">
      <h3>How It Works</h3>
      <div class="steps">
        <div class="step">
          <h4>Step 1</h4>
          <p>Create a donation or a request for dry goods.</p>
        </div>
        <br>
        <div class="step">
          <h4>Step 2</h4>
          <p>Wait for donations to be fulfilled or request to be accepted.</p>
        </div>
        <br>
        <div class="step">
          <h4>Step 3</h4>
          <p>Once your donation is accepted, arrange for delivery or pickup.</p>
        </div>
        <br>
      </div>
    </div>
  </section>
</main>
<jsp:include page="footer.jsp" />
</body>
</html>