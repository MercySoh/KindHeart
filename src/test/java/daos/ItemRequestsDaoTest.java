package daos;

import business.ItemRequests;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestsDaoTest {

    @Test
    void addItemRequest_success() {
        ItemRequestsDao itemRequestsDao = new ItemRequestsDao("kindhearttest");
        System.out.println("addItemRequest_success");

        int recipientId = 4;
        String itemName = "Canned Peas";
        String description = "A few canned peas require";
        int quantity = 5;
        String category = "Food";

        int result = itemRequestsDao.addItemRequest(recipientId, itemName, description, quantity, category);
        itemRequestsDao.deleteItemRequestById(5);
        itemRequestsDao.updateIncrement("itemrequests", 5);
        assertTrue(result > 0);
    }

    @Test
    void getAllItemRequests_success() {
        ItemRequestsDao itemRequestsDao = new ItemRequestsDao("kindhearttest");
        System.out.println("getAllItemRequests_success");

        List<ItemRequests> result = itemRequestsDao.getAllItemRequests();

        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }
}