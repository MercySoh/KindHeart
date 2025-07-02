package daos;

import business.Donations;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DonationsDao extends Dao implements DonationsDaoInterface{

    public DonationsDao(String dbName) {
        super(dbName);
    }
    public DonationsDao(Connection con) {
        super(con);
    }

    @Override
    public int addDonation(int uId, String iName, String desc, int qty, String ctgy, String img) {
        int rowsAffected = -1;

        try {
            con = getConnection();
            String query = "INSERT INTO donations (userId, itemName, description, quantity, category, image, status, datePosted) VALUES (?, ?, ?, ?, ?, ?, 1, NOW())";
            ps = con.prepareStatement(query);

            ps.setInt(1, uId);
            ps.setString(2, iName);
            ps.setString(3, desc);
            ps.setInt(4, qty);
            ps.setString(5, ctgy);
            ps.setString(6, img);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception occurred in the addDonation() method: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to add donation", e);  // Throw a runtime exception to notify the controller
        } finally {
            freeConnection("Exception occurred in the addDonation() final method:");
        }

        return rowsAffected;
    }

    @Override
    public Donations getDonationById(int dntId) {
        Donations d = null;
        try{
            con = getConnection();

            String query = "SELECT * FROM donations WHERE donationId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, dntId);
            rs = ps.executeQuery();

            if (rs.next())
            {
                int donationId = rs.getInt("donationId");
                int userId = rs.getInt("userId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String image = rs.getString("image");
                int status = rs.getInt("status");
                LocalDate datePosted = rs.getDate("datePosted").toLocalDate();

                d = new Donations(donationId, userId, itemName, description, quantity, category,image,status,datePosted);
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getDonationById() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getDonationById() final method:");
        }
        return d;
    }

    @Override
    public List<Donations> getAllDonations() {
        List<Donations> donations = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM donations ORDER BY datePosted DESC";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int donationId = rs.getInt("donationId");
                int userId = rs.getInt("userId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String image = rs.getString("image");
                int status = rs.getInt("status");
                LocalDate datePosted = rs.getDate("datePosted").toLocalDate();

                Donations d = new Donations(donationId, userId, itemName, description, quantity, category,image,status,datePosted);
                donations.add(d);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getAllDonations() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getAllDonations() method:");
        }
        return donations;
    }

    @Override
    public List<Donations> getDonationsByStatus(int dntStatus) {
        List<Donations> donations = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM donations WHERE status = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, dntStatus);
            rs = ps.executeQuery();

            while (rs.next())
            {
                 int donationId = rs.getInt("donationId");
                int userId = rs.getInt("userId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String image = rs.getString("image");
                int status = rs.getInt("status");
                LocalDate datePosted = rs.getDate("datePosted").toLocalDate();

                Donations d = new Donations(donationId, userId, itemName, description, quantity, category,image,status,datePosted);
                donations.add(d);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getDonationsByStatus() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getDonationsByStatus() method:");
        }
        return donations;
    }

    @Override
    public List<Donations> getDonationsByDonorId(int donorId) {
        List<Donations> donations = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM donations WHERE userId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, donorId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int donationId = rs.getInt("donationId");
                int userId = rs.getInt("userId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                String image = rs.getString("image");
                int status = rs.getInt("status");
                LocalDate datePosted = rs.getDate("datePosted").toLocalDate();

                Donations d = new Donations(donationId, userId, itemName, description, quantity, category,image,status,datePosted);
                donations.add(d);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getDonationsByDonorId() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getDonationsByDonorId() method:");
        }
        return donations;
    }

    @Override
    public int updateDonationStatus(int dntId, int newStatus) {
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "UPDATE donations SET status = ? WHERE donationId = ?";
            ps = con.prepareStatement(command);

            ps.setInt(1, newStatus);
            ps.setInt(2, dntId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateDonationStatus() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateDonationStatus() method: ");
        }
        return rowsAffected;
    }

    @Override
    public int deleteDonationById(int dntId) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();
            String command = "DELETE FROM donations WHERE donationId = ?";
            ps = con.prepareStatement(command);
            ps.setInt(1, dntId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the deleteDonationById() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) freeConnection("");
            } catch (SQLException e) {
                System.out.println("An error occurred when shutting down the deleteDonationById() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

    public boolean updateDonationStatusByRequestId(int requestId, int status) {
        boolean updated = false;
        try {
            con = this.getConnection();
            String sql = "UPDATE donations d " +
                    "JOIN requests r ON d.donationId = r.donationId " +
                    "SET d.status = ? " +
                    "WHERE r.requestId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, requestId);
            int rows = ps.executeUpdate();
            updated = rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating donation status: " + e.getMessage());
        } finally {
            freeConnection("Finished updateDonationStatusByRequestId()");
        }
        return updated;
    }

}
