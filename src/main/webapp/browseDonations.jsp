<%@ page import="java.util.List" %>
<%@ page import="business.Donations" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Donations> donations = (List<Donations>) request.getAttribute("availableDonations");
    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        session.removeAttribute("msg");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Browse Donations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/browseDonations.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Available Donation Items</h2>

    <% if (msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
    <% } %>

    <% if (donations == null || donations.isEmpty()) { %>
    <p>No available donation items at the moment.</p>
    <% } else { %>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Image</th>
            <th>Item Name</th>
            <th>Description</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Donations d : donations) { %>
        <tr>
            <td>
                <% if (d.getImage() != null && !d.getImage().isEmpty()) { %>
                <img src="img/<%= d.getImage() %>" class="thumbnail" alt="Item Image">
                <% } else { %>
                No Image
                <% } %>
            </td>
            <td><%= d.getItemName() %></td>
            <td><%= d.getDescription() %></td>
            <td><%= d.getQuantity() %></td>
            <td><%= d.getCategory() %></td>
            <td>
                <form action="controller" method="POST">
                    <input type="hidden" name="action" value="quick_request">
                    <input type="hidden" name="donationId" value="<%= d.getDonationId() %>">
                    <button type="submit" class="btn btn-request btn-sm">Request</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>

    <a href="home.jsp" class="btn btn-secondary mt-3">Back to Home</a>
</div>
</body>
</html>
