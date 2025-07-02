package daos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationsDaoTest {

    @Test
    void createNotification_success() {
        NotificationsDao notificationsDao = new NotificationsDao("kindhearttest");
        System.out.println("createNotification_success");

        int testUserId = 1;
        String message = "Test notification message";
        int isRead = 0;

        int notificationId = notificationsDao.createNotification(testUserId, message, isRead);
        notificationsDao.deleteNotification(5);
        notificationsDao.updateIncrement("notifications", 5);
        assertTrue(notificationId > 0);
    }

    @Test
    void getNotificationsByUserId() {
    }

    @Test
    void markNotificationAsRead() {
    }

    @Test
    void deleteNotification() {
    }
}