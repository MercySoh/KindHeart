package business;

import java.time.LocalDate;
import java.util.Objects;

public class Notifications {

//  `notificationId` int(11) NOT NULL,
//  `userId` int(11) NOT NULL,
//  `message` text NOT NULL,
//  `isRead` tinyint(1) DEFAULT 0,
//  `createdAt` date DEFAULT current_timestamp()

  private int notificationId;
  private int userId;
  private String message;
  private int isRead;
  private LocalDate createdAt;

    public Notifications() {
    }

    public Notifications(int notificationId, int userId, String message, int isRead, LocalDate createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifications that = (Notifications) o;
        return notificationId == that.notificationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId);
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                '}';
    }
}
