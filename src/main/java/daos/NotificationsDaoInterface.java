package daos;

import business.Notifications;

import java.util.List;

public interface NotificationsDaoInterface {
    int createNotification(int userId, String mess, int isRead);

    List<Notifications> getNotificationsByUserId(int uId);

    int markNotificationAsRead(int notificationId);

    int deleteNotification(int notificationId);
}
