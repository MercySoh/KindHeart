package daos;

import business.ItemRequests;
import business.Requests;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemRequestsDao extends Dao implements ItemRequestsDaoInterface{

    public ItemRequestsDao(String dbName) {
        super(dbName);
    }
    public ItemRequestsDao(Connection con) {
        super(con);
    }

    @Override
    public int addItemRequest(int recId, String iName, String desc, int qty, String ctgy) {
        int rowsAffected = -1;

        try{
            con = getConnection();

            String query = "INSERT INTO itemrequests (recipientId, itemName, description, quantity, category,itemRequestDate, fulfilled) VALUES (?, ?, ?, ?, ?,?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, recId);
            ps.setString(2,iName);
            ps.setString(3,desc);
            ps.setInt(4, qty);
            ps.setString(5, ctgy);
            ps.setDate(6, Date.valueOf(LocalDate.now()));
            ps.setInt(7,0);

            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the addItemRequest() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the addItemRequest() final method:");
        }
        return rowsAffected;
    }

    @Override
    public List<ItemRequests> getItemRequestsByUserId(int userId) {
        List<ItemRequests> itemRequests = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM itemrequests WHERE recipientId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int itemRequestId = rs.getInt("itemRequestId");
                int recipientId = rs.getInt("recipientId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                LocalDate itemRequestDate = rs.getDate("itemRequestDate").toLocalDate();
                int fulfilled = rs.getInt("fulfilled");

                ItemRequests i = new ItemRequests(itemRequestId, recipientId, itemName, description, quantity, category, itemRequestDate, fulfilled);
                itemRequests.add(i);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getItemRequestsByUserId() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getItemRequestsByUserId() method:");
        }
        return itemRequests;
    }

    @Override
    public int updateItemRequestStatus(int itemReId, int newFulfilled) {
        int rowsAffected = 0;

        try {
            con = getConnection();

            String command = "UPDATE itemrequests SET fulfilled = ? WHERE itemRequestId = ?";
            ps = con.prepareStatement(command);

            ps.setInt(1, newFulfilled);
            ps.setInt(2, itemReId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateItemRequestStatus() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateItemRequestStatus() method: ");
        }
        return rowsAffected;
    }

    @Override
    public ItemRequests getItemRequestById(int itemReId) {
        ItemRequests i = null;
        try{
            con = getConnection();

            String query = "SELECT * FROM itemrequests WHERE itemRequestId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, itemReId);
            rs = ps.executeQuery();

            if (rs.next())
            {
                int itemRequestId = rs.getInt("itemRequestId");
                int recipientId = rs.getInt("recipientId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                LocalDate itemRequestDate = rs.getDate("itemRequestDate").toLocalDate();
                int fulfilled = rs.getInt("fulfilled");

                i = new ItemRequests(itemRequestId, recipientId, itemName, description, quantity, category, itemRequestDate, fulfilled);
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred in  the getRequestById() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in  the getRequestById() final method:");
        }
        return i;
    }

    @Override
    public int deleteItemRequestById(int itemReId) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();
            String command = "DELETE FROM itemrequests WHERE itemRequestId = ?";
            ps = con.prepareStatement(command);
            ps.setInt(1, itemReId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the deleteItemRequestById() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) freeConnection("");
            } catch (SQLException e) {
                System.out.println("An error occurred when shutting down the deleteItemRequestById() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

    @Override
    public List<ItemRequests> getAllItemRequests() {
        List<ItemRequests> itemRequests = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM itemrequests";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int itemRequestId = rs.getInt("itemRequestId");
                int recipientId = rs.getInt("recipientId");
                String itemName = rs.getString("itemName");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                LocalDate itemRequestDate = rs.getDate("itemRequestDate").toLocalDate();
                int fulfilled = rs.getInt("fulfilled");

                ItemRequests i = new ItemRequests(itemRequestId, recipientId, itemName, description, quantity, category, itemRequestDate, fulfilled);
                itemRequests.add(i);
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
        return itemRequests;
    }
}
