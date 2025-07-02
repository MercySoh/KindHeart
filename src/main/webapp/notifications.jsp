<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="business.Notifications" %>

<!DOCTYPE html>
<html>
<head>
    <title>Notifications</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/notifications.css">

</head>
<body>
<div class="container">
    <h2>Your Notifications</h2>

    <%
        List<Notifications> notifications = (List<Notifications>) request.getAttribute("notifications");
        if (notifications != null && !notifications.isEmpty()) {
    %>
    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th>Message</th>
            <th>Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Notifications n : notifications) {
        %>
        <tr>
            <td><%= n.getMessage() %></td>
            <td><%= n.getCreatedAt() %></td>
            <td>
                <%= n.getIsRead() == 1 ? "Read" : "Unread" %>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <div class="alert alert-info">No notifications to display.</div>
    <%
        }
    %>

    <a href="home.jsp" class="btn btn-secondary mt-3">Back to Home</a>
</div>
</body>
</html>

