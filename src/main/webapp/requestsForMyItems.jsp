<%@ page import="java.util.List" %>
<%@ page import="business.Requests" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Requests> itemRequests = (List<Requests>) request.getAttribute("itemRequests");
    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        session.removeAttribute("msg");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Requests For My Items</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/requestsForMyItems.css">

</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Requests For My Items</h2>

    <% if (msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
    <% } %>

    <% if (itemRequests == null || itemRequests.isEmpty()) { %>
    <p>No one has requested your donated items yet.</p>
    <% } else { %>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>Item ID</th>
            <th>Recipient ID</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Requests req : itemRequests) {
            int status = req.getStatus();
            String statusText;
            switch (status) {
                case 2:
                    statusText = "Approved";
                    break;
                case 3:
                    statusText = "Rejected";
                    break;
                case 4:
                    statusText = "Collected";
                    break;
                default:
                    statusText = "Pending";
                    break;
            }
        %>
        <tr>
            <td><%= req.getDonation().getDonationId() %></td>
            <td><%= req.getRecipient().getUserId() %></td>
            <td><%= statusText %></td>
            <td>
                <% if (req.getStatus() == 1) { %>
                <form action="controller" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="accept_request">
                    <input type="hidden" name="requestId" value="<%= req.getRequestId() %>">
                    <button type="submit" class="btn btn-success btn-sm">Accept</button>
                </form>
                <form action="controller" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="reject_request">
                    <input type="hidden" name="requestId" value="<%= req.getRequestId() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Reject</button>
                </form>
                <% } else if (req.getStatus() == 2) { %>
                <form action="controller" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="complete_request">
                    <input type="hidden" name="requestId" value="<%= req.getRequestId() %>">
                    <button type="submit" class="btn btn-primary btn-sm">Mark as Collected</button>
                </form>
                <% } else { %>
                <span>No Action</span>
                <% } %>

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

