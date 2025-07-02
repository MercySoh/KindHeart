<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="business.Requests" %>
<%@ page import="business.Donations" %>

<%
    List<Requests> myRequests = (List<Requests>) request.getAttribute("myRequests");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Requests</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/myRequests.css">

</head>
<body>
<div class="container">
    <h2>My Item Requests</h2>

    <%
        if (myRequests == null || myRequests.isEmpty()) {
    %>
    <p>You haven't requested any items yet.</p>
    <%
    } else {
    %>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Request ID</th>
            <th>Donation Item</th>
            <th>Status</th>
            <th>Requested Date</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Requests r : myRequests) {
                String statusClass = "";
                String statusText = "";
                switch (r.getStatus()) {
                    case 1: statusClass = "status-pending"; statusText = "Pending"; break;
                    case 2: statusClass = "status-approved"; statusText = "Approved"; break;
                    case 3: statusClass = "status-rejected"; statusText = "Rejected"; break;
                    case 4: statusClass = "status-collected"; statusText = "Collected"; break;
                    default: statusText = "Unknown";
                }
        %>
        <tr>
            <td><%= r.getRequestId() %></td>
            <td><%= r.getDonation() != null ? r.getDonation().getItemName() : "N/A" %></td>
            <td class="<%= statusClass %>"><%= statusText %></td>
            <td><%= r.getRequestedDate() %></td>
            <td>
                <% if (r.getStatus() == 2) { %>
                <form action="controller" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update_request_status">
                    <input type="hidden" name="requestId" value="<%= r.getRequestId() %>">
                    <input type="hidden" name="newStatus" value="4">
                    <button type="submit" class="btn btn-success btn-sm">Mark as Collected</button>
                </form>
                <% } else if (r.getStatus() == 4) { %>
                <span class="text-success">Already Collected</span>
                <% } %>
            </td>
        </tr>

        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>

    <div class="mt-4">
        <a href="home.jsp" class="btn btn-primary">Back to Home</a>
    </div>
</div>
</body>
</html>

