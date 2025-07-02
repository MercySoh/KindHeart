package business;

import java.time.LocalDate;
import java.util.Objects;

public class ItemRequests {
//  `itemRequestId` int(11) NOT NULL,
//  `recipientId` int(11) NOT NULL,
//  `itemName` varchar(100) NOT NULL,
//  `description` text DEFAULT NULL,
//  `quantity` int(11) DEFAULT NULL,
//  `category` varchar(100) DEFAULT NULL,
//  `itemRequestDate` date DEFAULT current_timestamp(),
//  `fulfilled` tinyint(1) DEFAULT 0

    private int itemRequestId;
    private int recipientId;
    private String itemName;
    private String description;
    private int quantity;
    private String category;
    private LocalDate itemRequestDate;
    private int fulfilled;

    public ItemRequests() {
    }

    public ItemRequests(int itemRequestId, int recipientId, String itemName, String description, int quantity, String category, LocalDate itemRequestDate, int fulfilled) {
        this.itemRequestId = itemRequestId;
        this.recipientId = recipientId;
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.itemRequestDate = itemRequestDate;
        this.fulfilled = fulfilled;
    }

    public int getItemRequestId() {
        return itemRequestId;
    }

    public void setItemRequestId(int itemRequestId) {
        this.itemRequestId = itemRequestId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
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

    public LocalDate getItemRequestDate() {
        return itemRequestDate;
    }

    public void setItemRequestDate(LocalDate itemRequestDate) {
        this.itemRequestDate = itemRequestDate;
    }

    public int getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(int fulfilled) {
        this.fulfilled = fulfilled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRequests that = (ItemRequests) o;
        return itemRequestId == that.itemRequestId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemRequestId);
    }

    @Override
    public String toString() {
        return "ItemRequests{" +
                "itemRequestId=" + itemRequestId +
                ", recipientId=" + recipientId +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", itemRequestDate=" + itemRequestDate +
                ", fulfilled=" + fulfilled +
                '}';
    }
}
