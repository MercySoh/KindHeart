package daos;

import business.Donations;
import business.Requests;
import business.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestsDao extends Dao implements RequestsDaoInterface{

    public RequestsDao(String dbName) {
        super(dbName);
    }
    public RequestsDao(Connection con) {
        super(con);
    }

    @Override
    public int addRequest(int dntId, int recId, int status) {
        int rowsAffected = -1;

        try {
            con = getConnection();

            String query = "INSERT INTO requests (donationId, recipientId, status) VALUES (?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, dntId);
            ps.setInt(2,recId);
            ps.setInt(3,status);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the addRequest() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the addRequest() final method:");
        }
        return rowsAffected;
    }

    @Override
    public List<Requests> getRequestsByUserId(int userId) {
        List<Requests> requests = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM requests WHERE recipientId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int recipientId = rs.getInt("recipientId");
                int status = rs.getInt("status");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
                requests.add(r);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getRequestsByUserId() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getRequestsByUserId() method:");
        }
        return requests;
    }

    @Override
    public List<Requests> getRequestsByDonationId(int dntId) {
        List<Requests> requests = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM requests WHERE donationId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, dntId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int recipientId = rs.getInt("recipientId");
                int status = rs.getInt("status");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
                requests.add(r);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getRequestsByDonationId() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getRequestsByDonationId() method:");
        }
        return requests;
    }

    @Override
    public int updateRequestStatus(int reqId, int newStatus) {
        int rowsAffected = 0;

        try {
            con = getConnection();

            String command = "UPDATE requests SET status = ? WHERE requestId = ?";
            ps = con.prepareStatement(command);

            ps.setInt(1, newStatus);
            ps.setInt(2, reqId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateRequestStatus() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateRequestStatus() method: ");
        }
        return rowsAffected;
    }

    @Override
    public Requests getRequestById(int reqId) {
        Requests r = null;
        try{
            con = getConnection();

            String query = "SELECT * FROM requests WHERE requestId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, reqId);
            rs = ps.executeQuery();

            if (rs.next())
            {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int recipientId = rs.getInt("recipientId");
                int status = rs.getInt("status");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                r = new Requests(requestId, donationId, recipientId, status, requestedDate);
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getRequestById() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getRequestById() final method:");
        }
        return r;
    }

    @Override
    public int deleteRequestById(int reqId) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();
            String command = "DELETE FROM requests WHERE requestId = ?";
            ps = con.prepareStatement(command);
            ps.setInt(1, reqId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the deleteRequestById() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) freeConnection("");
            } catch (SQLException e) {
                System.out.println("An error occurred when shutting down the deleteRequestById() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

    @Override
    public List<Requests> getAllRequests() {
        List<Requests> requests = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM requests";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int recipientId = rs.getInt("recipientId");
                int status = rs.getInt("status");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
                requests.add(r);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllRequests() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getAllRequests() method:");
        }
        return requests;
    }

    public List<Requests> getRequestsForDonor(int donorId) {
        List<Requests> requests = new ArrayList<>();
        try {
            con = this.getConnection();
            String query = "SELECT r.requestId, r.donationId, r.recipientId, r.status, r.requestedDate " +
                    "FROM requests r " +
                    "JOIN donations d ON r.donationId = d.donationId " +
                    "WHERE r.status = 1 AND d.userId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, donorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int recipientId = rs.getInt("recipientId");
                int status = rs.getInt("status");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                // Create Requests object
                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);

                // Load Donations
                DonationsDao donationsDao = new DonationsDao("kindheart");
                Donations donation = donationsDao.getDonationById(donationId);
                r.setDonation(donation);

                // Load Recipient User
                UsersDao usersDao = new UsersDao("kindheart");
                Users recipient = usersDao.getUserById(recipientId);
                r.setRecipient(recipient);

                requests.add(r);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred in getRequestsForDonor(): " + e.getMessage());
        } finally {
            freeConnection("An error occurred when shutting down getRequestsForDonor():");
        }
        return requests;
    }

    public List<Requests> getRequestsByRecipientId(int recipientId) {
        List<Requests> requests = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT r.requestId, r.donationId, r.recipientId, r.status AS requestStatus, r.requestedDate, " +
                    "d.itemName, d.description, d.quantity, d.category, d.image, d.status AS donationStatus, d.datePosted, " +
                    "u.userId, u.username, u.email " +
                    "FROM requests r " +
                    "JOIN donations d ON r.donationId = d.donationId " +
                    "JOIN users u ON r.recipientId = u.userId " +
                    "WHERE r.recipientId = ?";

            ps = con.prepareStatement(query);
            ps.setInt(1, recipientId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int requestId = rs.getInt("requestId");
                int donationId = rs.getInt("donationId");
                int status = rs.getInt("requestStatus");
                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();

                // Create Donations object and set its fields
                Donations donation = new Donations();
                donation.setDonationId(donationId);
                donation.setItemName(rs.getString("itemName"));
                donation.setDescription(rs.getString("description"));
                donation.setQuantity(rs.getInt("quantity"));
                donation.setCategory(rs.getString("category"));
                donation.setImage(rs.getString("image"));
                donation.setStatus(rs.getInt("donationStatus"));
                donation.setDatePosted(rs.getDate("datePosted").toLocalDate());

                // Create Users object for recipient and set fields
                Users recipient = new Users();
                recipient.setUserId(rs.getInt("userId"));
                recipient.setUsername(rs.getString("username"));
                recipient.setEmail(rs.getString("email"));
                // add other user fields if you want

                // Create Requests object and set fields + nested objects
                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
                r.setDonation(donation);
                r.setRecipient(recipient);

                requests.add(r);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred in the getRequestsByRecipientId() method: " + e.getMessage());
        } finally {
            freeConnection("An error occurred when shutting down the getRequestsByRecipientId() method:");
        }
        return requests;
    }


//    public List<Requests> getRequestsForDonor(int donorId) {
//        List<Requests> requests = new ArrayList();
//        try
//        {
//            con = this.getConnection();
////            "SELECT * FROM requests r " +
////                    "JOIN donations d ON r.donationId = d.donationId " +
////                    "WHERE d.userId = ?";
//            String query = "SELECT r.requestId, r.donationId, r.recipientId, r.status, r.requestedDate " +
//                    "FROM requests r " +
//                    "JOIN donations d ON r.donationId = d.donationId " +
//                    "WHERE r.status = 1 AND d.userId = ?";
//            ps = con.prepareStatement(query);
//            ps.setInt(1, donorId);
//            rs = ps.executeQuery();
//
//            while (rs.next())
//            {
//                int requestId = rs.getInt("requestId");
//                int donationId = rs.getInt("donationId");
//                int recipientId = rs.getInt("recipientId");
//                int status = rs.getInt("status");
//                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();
//
//                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
//                requests.add(r);
//            }
//        }
//        catch (SQLException e)
//        {
//            System.out.println("An error occurred in the getRequestsByDonationId() method: " + e.getMessage());
//        }
//        finally
//        {
//            freeConnection("An error occurred when shutting down the getRequestsByDonationId() method:");
//        }
//        return requests;
//    }

}
//    @Override
//    public List<Requests> getRequestsWithItemAndRecipientByDonorId(int donorId) {
//        List<Requests> requests = new ArrayList();
//        try
//        {
//            con = this.getConnection();
//
//            String query = "SELECT r.*, d.itemName, u.username AS recipientName "
//                    + "FROM requests r "
//                    + "JOIN donations d ON r.donationId = d.donationId "
//                    + "JOIN users u ON r.recipientId = u.userId "
//                    + "WHERE d.userId = ? "
//                    + "ORDER BY r.requestId DESC";
//
//            ps = con.prepareStatement(query);
//            ps.setInt(1, donorId);
//            rs = ps.executeQuery();
//
//            while (rs.next())
//            {
//                int requestId = rs.getInt("requestId");
//                int donationId = rs.getInt("donationId");
//                int recipientId = rs.getInt("recipientId");
//                int status = rs.getInt("status");
//                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();
//
//                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
//                requests.add(r);
////                Requests request = new Requests();
////                request.setRequestId(rs.getInt("requestId"));
////                request.setStatus(rs.getInt("requestStatus"));
////
////                Donations donation = new Donations();
////                donation.setDonationId(rs.getInt("donationId"));
////                donation.setItemName(rs.getString("itemName"));
////                donation.setDescription(rs.getString("description"));
////                donation.setQuantity(rs.getInt("quantity"));
////                donation.setImage(rs.getString("image"));
////                donation.setStatus(rs.getInt("donationStatus"));
////
////                Users recipient = new Users();
////                recipient.setUserId(rs.getInt("recipientId"));
////                recipient.setUsername(rs.getString("recipientName"));
////
////                request.setDonation(donation);
////                request.setRecipient(recipient);
////
////                requests.add(request);
//            }
//        }
//        catch (SQLException e)
//        {
//            System.out.println("An error occurred in the getRequestsByDonationId() method: " + e.getMessage());
//        }
//        finally
//        {
//            freeConnection("An error occurred when shutting down the getRequestsByDonationId() method:");
//        }
//        return requests;
//    }
//    public List<Requests> getRequestsWithItemAndRecipientByDonorId(int donorId) {
//        List<Requests> requests = new ArrayList<>();
//        try {
//            con = this.getConnection();
//
//            String query = "SELECT r.requestId, r.donationId, r.recipientId, r.status, r.requestedDate " +
//                    "FROM requests r " +
//                    "JOIN donations d ON r.donationId = d.donationId " +
//                    "WHERE d.userId = ?";
//            ps = con.prepareStatement(query);
//            ps.setInt(1, donorId);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int requestId = rs.getInt("requestId");
//                int donationId = rs.getInt("donationId");
//                int recipientId = rs.getInt("recipientId");
//                int status = rs.getInt("status");
//                LocalDate requestedDate = rs.getDate("requestedDate").toLocalDate();
//
//                Requests r = new Requests(requestId, donationId, recipientId, status, requestedDate);
//                requests.add(r);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error in getRequestsWithItemAndRecipientByDonorId: " + e.getMessage());
//        } finally {
//            freeConnection("Error closing connection in getRequestsWithItemAndRecipientByDonorId()");
//        }
//        return requests;
//    }

