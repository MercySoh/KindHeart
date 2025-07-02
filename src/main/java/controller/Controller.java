package controller;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.*;
import daos.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import miscellaneous.Miscellaneous;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        String dest = "index.jsp";

        if (action != null) {
            switch (action) {
                case "index":
                    response.sendRedirect("index.jsp");
                    return;

                case "logout":
                    session.invalidate();
                    response.sendRedirect("index.jsp");
                    return;

                case "show_login":
                    dest = "login.jsp";
                    break;

                case "do_login":
                    dest = doLogin(request, response);
                    break;

                case "show_register":
                    dest = "register.jsp";
                    break;

                case "do_register":
                    dest = doRegister(request, response);
                    break;

                case "add_donation":
                    dest = addDonation(request, response);
                    break;

                case "mark_requested":
                    updateDonationStatus(request, response, 2);
                    return;

                case "mark_collected":
                    updateDonationStatus(request, response, 3);
                    return;

                case "mark_available":
                    updateDonationStatus(request, response, 1);
                    return;

                case "delete_donation":
                    deleteDonation(request, response);
                    return;

                case "view_my_item_requests":
                    dest = viewMyItemRequests(request, response);
                    break;

                case "accept_request":
                    updateRequestStatus(request, response, 2);
                    return;

                case "reject_request":
                    updateRequestStatus(request, response, 3);
                    return;

                case "complete_request":
                    updateRequestStatus(request, response, 4);
                    return;

                case "browse_donations":
                    dest = browseDonations(request, response);
                    break;

                case "quick_request":
                    dest = quickRequest(request, response);
                    break;

                case "view_my_requests":
                    dest = viewMyRequests(request, response);
                    break;

                case "update_request_status":
                    int requestId = Integer.parseInt(request.getParameter("requestId"));
                    int newStatus = Integer.parseInt(request.getParameter("newStatus"));
                    updateRequestStatus(request, response, newStatus, requestId);
                    return;

                case "submit_custom_request":
                    dest = submitCustomRequest(request, response);
                    break;

                case "view_custom_requests":
                    dest = showCustomRequests(request, response);
                    break;

                case "view_notifications":
                    dest = viewNotifications(request, response);
                    break;

            }
        }

        // Forward the request to preserve form data and session attributes
        request.getRequestDispatcher(dest).forward(request, response);
    }

    public String doRegister(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        //1PassWord@25
        String password = request.getParameter("password");
        String roleStr = request.getParameter("role");

        if (username == null || email == null || password == null || roleStr == null ||
                username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()) {
            session.setAttribute("msg", "All fields are required.");
            return "register.jsp";
        }

        if (!Miscellaneous.checkEmail(email)) {
            session.setAttribute("msg", "Invalid email format.");
            return "register.jsp";
        }

        if (!Miscellaneous.checkPassword(password)) {
            session.setAttribute("msg", "Password must be 8–20 characters, include uppercase, lowercase, digit, and special character.");
            return "register.jsp";
        }

        int role;
        try {
            role = Integer.parseInt(roleStr);
            if (role != 1 && role != 2) {
                session.setAttribute("msg", "Invalid role selected.");
                return "register.jsp";
            }
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid role format.");
            return "register.jsp";
        }

        UsersDao usersDao = new UsersDao("kindheart");

        if (usersDao.checkEmail(email)) {
            session.setAttribute("msg", "Email is already registered.");
            return "register.jsp";
        }

        if (usersDao.checkUsername(username)) {
            session.setAttribute("msg", "Username is already taken.");
            return "register.jsp";
        }

        int userId = usersDao.register(username, email, password, role);

        if (userId != -1) {
            Users newUser = new Users(userId, username, email, "", "default.png", role);
            session.setAttribute("user", newUser);
            session.setAttribute("username", newUser.getUsername());
            session.setAttribute("role", newUser.getRole());
            session.setAttribute("msg", "Registration successful!");
            return "home.jsp";
        } else {
            session.setAttribute("msg", "Registration failed. Try again.");
            return "register.jsp";
        }
    }

    public String doLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsersDao usersDao = new UsersDao("kindheart");
        Users user = usersDao.login(email, password);

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("msg", "Login successful!");

            return "home.jsp";
        } else {
            session.setAttribute("msg", "Wrong email or password");
            return "login.jsp";
        }
    }

    public String addDonation(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please log in to donate.");
            return "login.jsp";
        }

        int userId = user.getUserId();
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String quantityStr = request.getParameter("quantity");
        String category = request.getParameter("category");
        String image = request.getParameter("image");

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid quantity.");
            return "donate.jsp";
        }

        if (image == null || image.trim().isEmpty()) {
            image = "default.png";
        }

        DonationsDao donationDao = new DonationsDao("kindheart");
        int result = donationDao.addDonation(userId, itemName, description, quantity, category, image);

        if (result > 0) {
            session.setAttribute("msg", "Donation added successfully!");
            return "home.jsp";
        } else {
            session.setAttribute("msg", "Failed to add donation. Try again.");
            return "donate.jsp";
        }
    }

    public void updateDonationStatus(HttpServletRequest request, HttpServletResponse response, int newStatus)
            throws IOException {
        HttpSession session = request.getSession();
        String donationIdStr = request.getParameter("id");

        try {
            int donationId = Integer.parseInt(donationIdStr);
            DonationsDao donationDao = new DonationsDao("kindheart");
            int updated = donationDao.updateDonationStatus(donationId, newStatus);

            if (updated > 0) {
                session.setAttribute("msg", "Donation status updated successfully.");
            } else {
                session.setAttribute("msg", "Failed to update donation status.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid donation ID.");
        }

        response.sendRedirect("myDonations.jsp");
    }

    public void deleteDonation(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        String donationIdStr = request.getParameter("id");

        try {
            int donationId = Integer.parseInt(donationIdStr);
            DonationsDao donationDao = new DonationsDao("kindheart");
            int deleted = donationDao.deleteDonationById(donationId);

            if (deleted > 0) {
                session.setAttribute("msg", "Donation deleted successfully.");
            } else {
                session.setAttribute("msg", "Failed to delete donation.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid donation ID.");
        }

        response.sendRedirect("myDonations.jsp");
    }

    public String viewMyItemRequests(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please login first.");
            return "login.jsp";
        }

        int donorId = user.getUserId();
        RequestsDao requestsDao = new RequestsDao("kindheart");
        List<Requests> requests = requestsDao.getRequestsForDonor(donorId);

        System.out.println("Requests fetched: " + requests.size());
        for (Requests r : requests) {
            System.out.println("Request ID: " + r.getRequestId() + ", Donation ID: " + r.getDonationId());
        }

        request.setAttribute("itemRequests", requests);
        return "requestsForMyItems.jsp";
    }

    public void updateRequestStatus(HttpServletRequest request, HttpServletResponse response, int newStatus)
            throws IOException {
        HttpSession session = request.getSession();
        String requestIdStr = request.getParameter("requestId");

        try {
            int requestId = Integer.parseInt(requestIdStr);
            RequestsDao requestsDao = new RequestsDao("kindheart");
            int updated = requestsDao.updateRequestStatus(requestId, newStatus);

            if (newStatus == 4 && updated > 0) {
                DonationsDao donationsDao = new DonationsDao("kindheart");
                donationsDao.updateDonationStatusByRequestId(requestId, 3);
            }

            if (updated > 0) {
                session.setAttribute("msg", "Request status updated.");
            } else {
                session.setAttribute("msg", "Failed to update request.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid request ID.");
        }

        response.sendRedirect("controller?action=view_my_item_requests");
    }

    public String browseDonations(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please login first.");
            return "login.jsp";
        }

        DonationsDao donationsDao = new DonationsDao("kindheart");
        List<business.Donations> availableDonations = donationsDao.getDonationsByStatus(1); // 1 = Available

        request.setAttribute("availableDonations", availableDonations);
        return "browseDonations.jsp";
    }

    public String quickRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null || user.getRole() != 2) {
            session.setAttribute("msg", "You must be logged in as a recipient to request items.");
            return "login.jsp";
        }

        int recipientId = user.getUserId();
        int donationId = Integer.parseInt(request.getParameter("donationId"));

        RequestsDao requestsDao = new RequestsDao("kindheart");
        int result = requestsDao.addRequest(donationId, recipientId, 1);

        if (result > 0) {
            // Get donation to fetch donor ID
            DonationsDao dDao = new DonationsDao("kindheart");
            Donations donation = dDao.getDonationById(donationId);
            int donorId = donation.getUserId();

            dDao.updateDonationStatus(donationId, 2);

            NotificationsDao nDao = new NotificationsDao("kindheart");
            String message = "You have a new request for your item: " + donation.getItemName();
            nDao.createNotification(donorId, message, 0);

            session.setAttribute("msg", "Request sent successfully!");
        } else {
            session.setAttribute("msg", "Failed to send request.");
        }


        return "browseDonations.jsp";
    }
    public String viewMyRequests(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        RequestsDao requestsDao = new RequestsDao("kindheart");
        List<Requests> myRequests = requestsDao.getRequestsByRecipientId(userId);

        request.setAttribute("myRequests", myRequests);
        return "myRequests.jsp";
    }

    public void updateRequestStatus(HttpServletRequest request, HttpServletResponse response, int newStatus, int requestId)
            throws IOException {
        HttpSession session = request.getSession();
        RequestsDao dao = new RequestsDao("kindheart");

        int updated = dao.updateRequestStatus(requestId, newStatus);
        if (updated > 0) {
            session.setAttribute("msg", "Request updated to Collected.");
        } else {
            session.setAttribute("msg", "Failed to update request.");
        }

        response.sendRedirect("controller?action=view_my_requests");
    }

    public String submitCustomRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please log in to submit a request.");
            return "login.jsp";
        }

        int recipientId = user.getUserId();
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String quantityStr = request.getParameter("quantity");
        String category = request.getParameter("category");

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            session.setAttribute("msg", "Invalid quantity.");
            return "customRequestForm.jsp";
        }

        ItemRequestsDao itemRequestsDao = new ItemRequestsDao("kindheart");
        int result = itemRequestsDao.addItemRequest(recipientId, itemName, description, quantity, category);

        if (result > 0) {
            session.setAttribute("msg", "Your custom request has been submitted successfully.");
            return "home.jsp";
        } else {
            session.setAttribute("msg", "Failed to submit your request. Please try again.");
            return "customRequestForm.jsp";
        }
    }

    public String showCustomRequests(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please log in to view requests.");
            return "login.jsp";
        }

        ItemRequestsDao dao = new ItemRequestsDao("kindheart");
        List<ItemRequests> requests = dao.getAllItemRequests();

        if (requests == null) {
            requests = new ArrayList<>();
        }

        request.setAttribute("requests", requests);
        return "viewCustomRequests.jsp";
    }

    public String viewNotifications(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("msg", "Please login first.");
            return "login.jsp";
        }

        int userId = user.getUserId();
        NotificationsDao notificationDAO = new NotificationsDao("kindheart");
        List<Notifications> notifications = notificationDAO.getNotificationsByUserId(userId);

        request.setAttribute("notifications", notifications);
        return "notifications.jsp";
    }

}
