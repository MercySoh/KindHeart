<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="business.ItemRequests" %>

<!DOCTYPE html>
<html>
<head>
    <title>Custom Item Requests</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/viewCustomRequests.css">
</head>
<body>
<div class="container">
    <h2>All Custom Item Requests</h2>

    <%
        List<ItemRequests> requests = (List<ItemRequests>) request.getAttribute("requests");

        if (requests != null && !requests.isEmpty()) {
    %>
    <table class="table">
        <thead>
        <tr>
            <th>Item Name</th>
            <th>Description</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Date Requested</th>
            <th>Fulfilled</th>
        </tr>
        </thead>
        <tbody>
        <% for (ItemRequests req : requests) { %>
        <tr>
            <td><%= req.getItemName() %></td>
            <td><%= req.getDescription() %></td>
            <td><%= req.getQuantity() %></td>
            <td><%= req.getCategory() %></td>
            <td><%= req.getItemRequestDate() %></td>
            <td><%= req.getFulfilled() == 1 ? "Yes" : "No" %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <div class="alert alert-info">No custom requests found.</div>
    <%
        }
    %>

    <a href="home.jsp" class="btn btn-secondary mt-3">Back to Home</a>
</div>
</body>
</html>

