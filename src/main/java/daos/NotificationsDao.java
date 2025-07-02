package daos;

import business.Notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDao extends Dao implements NotificationsDaoInterface{

    public NotificationsDao(String dbName) {
        super(dbName);
    }
    public NotificationsDao(Connection con) {
        super(con);
    }

    @Override
    public int createNotification(int userId, String mess, int isRead) {
        int generatedId = -1;

        try {
            con = getConnection();

            String query = "INSERT INTO notifications (userId, message, isRead) VALUES (?, ?, ?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, mess);
            ps.setInt(3, isRead);

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println("Exception occurred in the createNotification() method: " + e.getMessage());
        }
        finally {
            freeConnection("Exception occurred in the createNotification() final method:");
        }
        return generatedId;
    }

    @Override
    public List<Notifications> getNotificationsByUserId(int uId) {
        List<Notifications> notifications = new ArrayList();
        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM notifications WHERE userId = ? ORDER BY createdAt DESC";
            ps = con.prepareStatement(query);
            ps.setInt(1, uId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                int notificationId = rs.getInt("notificationId");
                int userId = rs.getInt("userId");
                String message = rs.getString("message");
                int isRead = rs.getInt("isRead");
                LocalDate createdAt = rs.getDate("createdAt").toLocalDate();

                Notifications n = new Notifications(notificationId, userId, message, isRead, createdAt);
                notifications.add(n);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getNotificationsByUserId() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getNotificationsByUserId() method:");
        }
        return notifications;
    }

    @Override
    public int markNotificationAsRead(int notificationId) {
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "UPDATE notifications SET isRead = 1 WHERE notificationId = ?";
            ps = con.prepareStatement(command);
            ps.setInt(1, notificationId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the markNotificationAsRead() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the markNotificationAsRead() method: ");
        }
        return rowsAffected;
    }

    @Override
    public int deleteNotification(int notificationId) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();
            String command = "DELETE FROM notifications WHERE notificationId = ?";
            ps = con.prepareStatement(command);
            ps.setInt(1, notificationId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the deleteNotification() method: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) freeConnection("");
            } catch (SQLException e) {
                System.out.println("An error occurred when shutting down the deleteNotification() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }
}
