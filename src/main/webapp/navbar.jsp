<%@ page import="business.Notifications" %>
<%@ page import="java.util.List" %>
<%@ page session="true" %>
<%
  String username = (String) session.getAttribute("username");
  Integer role = (Integer) session.getAttribute("role");
  List<Notifications> notifications = (List<Notifications>) request.getAttribute("notifications");
%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet" href="css/navbar.css">

<nav class="navbar navbar-expand-lg custom-navbar shadow-sm">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="home.jsp">KindHeart</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
      <ul class="navbar-nav align-items-center">
        <% if (username != null && role != null) { %>

        <!-- Notification dropdown -->
        <li class="nav-item dropdown me-3">
          <a class="nav-link dropdown-toggle position-relative" href="#" id="notificationDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-bell" style="font-size: 1.5rem; color: #5c4600;"></i>
            <% if (notifications != null && !notifications.isEmpty()) { %>
            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
        <%= notifications.size() %>
        <span class="visually-hidden">unread notifications</span>
      </span>
            <% } %>
          </a>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="notificationDropdown" style="min-width: 300px;">
            <% if (notifications == null || notifications.isEmpty()) { %>
            <li class="dropdown-item text-center text-muted">No notifications</li>
            <% } else {
              for (Notifications notif : notifications) { %>
            <li>
              <a class="dropdown-item" href="#">
                <small class="text-muted"><%= notif.getCreatedAt() %></small><br/>
                <%= notif.getMessage() %>
              </a>
            </li>
            <li><hr class="dropdown-divider"></li>
            <% } } %>
            <li><a class="dropdown-item text-center" href="controller?action=view_notifications">View All</a></li>
          </ul>
        </li>

        <!-- Welcome and logout -->
        <li class="nav-item">
          <a class="nav-link">Welcome, <strong><%= username %></strong></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="controller?action=logout">Logout</a>
        </li>
        <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="controller?action=show_login">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="controller?action=show_register">Register</a>
        </li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>




