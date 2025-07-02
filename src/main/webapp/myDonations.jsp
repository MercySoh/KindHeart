<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.*, business.Donations, daos.DonationsDao" %>

<%
    String username = (String) session.getAttribute("username");
    Integer role = (Integer) session.getAttribute("role");
    Integer donorIdObj = (Integer) session.getAttribute("userId");

    if (username == null || role == null || donorIdObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        session.removeAttribute("msg");
    }
    int donorId = donorIdObj;

    DonationsDao dao = new DonationsDao("kindheart");
    List<Donations> donations = dao.getDonationsByDonorId(donorId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Donations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/myDonations.css">

</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">My Donations</h2>

    <% if (msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
    <% } %>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Image</th>
            <th>Item Name</th>
            <th>Description</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Donations donation : donations) { %>
        <tr>
            <td>
                <% if (donation.getImage() != null && !donation.getImage().isEmpty()) { %>
                <img src="<%= request.getContextPath() %>/img/<%= donation.getImage() %>" class="thumbnail" alt="Item Image" style="max-width: 100px;">
            <%--                <img src="<%= donation.getImage() %>" class="thumbnail" alt="Item Image">--%>
                <% } else { %>
                No Image
                <% } %>
            </td>
            <td><%= donation.getItemName() %></td>
            <td><%= donation.getDescription() %></td>
            <td><%= donation.getQuantity() %></td>
            <td><%= donation.getCategory() %></td>
            <td>
                <%
                    int status = donation.getStatus();
                    String statusText = "";
                    switch (status) {
                        case 1:
                            statusText = "Available";
                            break;
                        case 2:
                            statusText = "Requested";
                            break;
                        case 3:
                            statusText = "Collected";
                            break;
                    }
                %>
                <%= statusText %>
            </td>
        <%--            <td><%= donation.getStatus() == 1 ? "Completed" : "Pending" %></td>--%>
            <td>
                <%-- Action Buttons --%>
                <a href="editDonation.jsp?id=<%= donation.getDonationId() %>" class="btn btn-warning btn-sm">Edit</a>
                <a href="controller?action=delete_donation&id=<%= donation.getDonationId() %>" class="btn btn-danger btn-sm"
                   onclick="return confirm('Are you sure you want to delete this donation?')">Delete</a>

                <% if (donation.getStatus() == 1) { %>
                <a href="controller?action=mark_requested&id=<%= donation.getDonationId() %>" class="btn btn-info btn-sm">Mark as Requested</a>
                <% } else if (donation.getStatus() == 2) { %>
                <a href="controller?action=mark_collected&id=<%= donation.getDonationId() %>" class="btn btn-success btn-sm">Mark as Collected</a>
                <% } else if (donation.getStatus() == 3) { %>
                <a href="controller?action=mark_available&id=<%= donation.getDonationId() %>" class="btn btn-secondary btn-sm">Reset to Available</a>
                <% } %>
            </td>

        </tr>
        <% } %>
        </tbody>
    </table>

    <a href="home.jsp" class="btn btn-secondary mt-3">Back to Home</a>
</div>
</body>
</html>
