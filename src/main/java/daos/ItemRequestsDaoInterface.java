package daos;

import business.ItemRequests;

import java.util.List;

public interface ItemRequestsDaoInterface {

    int addItemRequest(int recId, String iName, String desc, int qty, String ctgy);
    List<ItemRequests> getItemRequestsByUserId(int userId);
    int updateItemRequestStatus(int itemReId, int newFulfilled);
    ItemRequests getItemRequestById(int itemReId);
    int deleteItemRequestById(int itemReId);
    List<ItemRequests> getAllItemRequests();
}
