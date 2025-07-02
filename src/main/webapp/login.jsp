<%
  String msg = (String) session.getAttribute("msg");
  session.removeAttribute("msg");
  if (msg == null) {
    msg = "";
  } else {
    msg = "<p>" + msg + "</p>";
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>KindHeart | Login</title>
  <link rel="icon" href="img/favicon.png">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="css/form.css">
  <meta name="theme-color" content="#fff3b0">
</head>
<body style="background-color: #fffbea; font-family: 'Poppins', sans-serif;">

<div class="container mt-5">
  <div class="warning-msg text-center text-danger">
    <%= msg %>
  </div>

  <main class="mx-auto" style="max-width: 400px; background-color: #fff3b0; padding: 30px; border-radius: 10px;">
    <h1 class="text-center mb-4" style="color: #c79300;">Login</h1>

    <form action="controller" method="post">
      <label>Email</label>
      <input type="email" class="form-control mb-3" name="email" placeholder="Enter email" required />

      <label>Password</label>
      <input type="password" class="form-control mb-4" name="password" placeholder="Enter password" required />

      <input type="hidden" name="action" value="do_login" />
      <button class="btn btn-warning w-100 fw-bold" type="submit">Login</button>
    </form>
    <br>
    <div class="d-flex justify-content-between">
      <a href="register.jsp" class="btn btn-warning w-45" style="background-color: #fdd835; border-color: #fdd835; color: #333;">Want to Register</a><br>
      <a href="index.jsp" class="btn btn-warning w-45" style="background-color: #fdd835; border-color: #fdd835; color: #333;">Back to Home</a>
    </div>

  </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/app.js"></script>
</body>
</html>


