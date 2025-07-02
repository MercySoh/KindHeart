<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<jsp:include page="navbar.jsp" />

<%
    String username = (String) session.getAttribute("username");
    Integer role = (Integer) session.getAttribute("role");

    if (username == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>KindHeart - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
</head>

<body class="d-flex flex-column min-vh-100">
<div class="container py-5 flex-grow-1">
    <div class="text-center dashboard-header">
        <h2 class="fw-bold text-warning">KindHeart Dashboard</h2>
    </div>

    <div class="row g-4">
        <% if (role == 1) { %>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="donate.jsp">
                    <h5>Donate Items</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="myDonations.jsp">
                    <h5>My Donations</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="controller?action=view_my_item_requests">
                <h5>Requests Received</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="controller?action=view_custom_requests">
                    <h5>View Custom Requests</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="comingSoon.jsp">
                    <h5>Donation History</h5>
                </a>
            </div>
        </div>

        <% } else if (role == 2) { %>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="controller?action=browse_donations">
                    <h5>Browse Available Donations</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="controller?action=view_my_requests">
                    <h5>View My Requests</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="customRequestForm.jsp">
                    <h5>Create Custom Request</h5>
                </a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-3 text-center h-100">
                <a href="comingSoon.jsp">
                    <h5>Request History</h5>
                </a>
            </div>
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>


