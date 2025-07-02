<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        session.removeAttribute("msg");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Custom Item Request</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/customRequestForm.css">
</head>

<body>
<div class="container">
    <h2>Custom Item Request Form</h2>

    <% if (msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
    <% } %>

    <form action="controller?action=submit_custom_request" method="post">
        <div class="form-group">
            <label for="itemName">Item Name:</label>
            <input type="text" id="itemName" name="itemName" class="form-control" required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" class="form-control" rows="3" required></textarea>
        </div>

        <div class="form-group">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" class="form-control" min="1" required>
        </div>

        <div class="form-group">
            <label for="category">Category:</label>
            <input type="text" id="category" name="category" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-success">Submit Request</button>
        <a href="home.jsp" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>

