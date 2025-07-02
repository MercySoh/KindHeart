package business;

import java.time.LocalDate;
import java.util.Objects;

public class Donations {
//  `donationId` int(11) NOT NULL,
//  `userId` int(11) NOT NULL,
//  `itemName` varchar(100) NOT NULL,
//  `description` text DEFAULT NULL,
//  `quantity` int(11) NOT NULL,
//  `category` varchar(50) NOT NULL,
//  `image` varchar(255) DEFAULT 'default.png',
//  `status` int(1) DEFAULT 1 COMMENT '1-available,2-requested,3-collected',
//  `datePosted` date DEFAULT current_timestamp()

private int donationId;
private int userId;
private String itemName;
private String description;
private int quantity;
private String category;
private String image;
private int status;
private LocalDate datePosted;

    public Donations() {
    }

    public Donations(int donationId, int userId, String itemName, String description, int quantity, String category, String image, int status, LocalDate datePosted) {
        this.donationId = donationId;
        this.userId = userId;
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.status = status;
        this.datePosted = datePosted;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donations donations = (Donations) o;
        return donationId == donations.donationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationId);
    }

    @Override
    public String toString() {
        return "Donations{" +
                "donationId=" + donationId +
                ", userId=" + userId +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", datePosted=" + datePosted +
                '}';
    }
}
