package daos;

import business.Requests;

import java.util.List;
import java.util.Map;

public interface RequestsDaoInterface {
    int addRequest(int dntId, int recId, int status);
    List<Requests> getRequestsByUserId(int userId);
    List<Requests> getRequestsByDonationId(int dntId);
    int updateRequestStatus(int reqId, int newStatus);
    Requests getRequestById(int reqId);
    int deleteRequestById(int reqId);
    List<Requests> getAllRequests();
    public List<Requests> getRequestsForDonor(int donorId);
//    public List<Requests> getRequestsWithItemAndRecipientByDonorId(int donorId);

}
