<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    Integer role = (Integer) session.getAttribute("role");

    if (username == null || role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        session.removeAttribute("msg");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Donate Item</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/donate.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">Donate Item</h2>

    <% if (msg != null) { %>
    <div class="alert alert-info"><%= msg %></div>
    <% } %>

    <form action="controller?action=add_donation" method="post">
        <div class="mb-3">
            <label for="itemName" class="form-label">Item Name</label>
            <input type="text" class="form-control" id="itemName" name="itemName" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3" placeholder="e.g., description of best before date and item condition" required></textarea>
        </div>
        <div class="mb-3">
            <label for="quantity" class="form-label">Quantity</label>
            <input type="number" class="form-control" id="quantity" name="quantity" required>
        </div>
        <div class="mb-3">
            <label for="category" class="form-label">Category</label>
            <select class="form-control" id="category" name="category" required>
                <option value="">Select Category</option>
                <option value="Clothing">Clothing</option>
                <option value="Furniture">Furniture</option>
                <option value="Food">Food</option>
                <option value="Electronics">Electronics</option>
                <option value="Books">Books</option>
                <option value="Toys">Toys</option>
                <option value="Other">Other</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="image" class="form-label">Image Filename (optional)</label>
            <input type="text" class="form-control" id="image" name="image" placeholder="e.g., rice.png">
        </div>
        <button type="submit" class="btn btn-primary">Submit Donation</button>
    </form>

    <a href="home.jsp" class="btn btn-secondary mt-3">Back to Home</a>
</div>
</body>
</html>