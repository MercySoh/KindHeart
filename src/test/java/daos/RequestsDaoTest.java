package daos;

import business.Donations;
import business.Requests;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestsDaoTest {

    @Test
    void addRequest_success() {
        RequestsDao requestsDao = new RequestsDao("kindhearttest");
        System.out.println("addRequest_success");

        int donationId = 5;
        int recipientId = 4;
        int status = 1;

        int result = requestsDao.addRequest(donationId, recipientId, status);
        requestsDao.deleteRequestById(5);
        requestsDao.updateIncrement("requests", 5);
        assertTrue(result > 0);
    }

    @Test
    void updateRequestStatus_success() {
        RequestsDao requestsDao = new RequestsDao("kindhearttest");
        System.out.println("updateRequestStatus_success");

        int requestId = 3;
        int newStatus = 4;

        int result = requestsDao.updateRequestStatus(requestId, newStatus);
        assertTrue(result > 0);
    }

    @Test
    void getAllRequests() {
        RequestsDao requestsDao = new RequestsDao("kindhearttest");
        System.out.println("getAllRequests");
        ArrayList<Requests> result = (ArrayList<Requests>) requestsDao.getAllRequests();
        assertEquals(4, result.size());
    }

    @Test
    void getRequestsForDonor_success() {
        RequestsDao requestsDao = new RequestsDao("kindhearttest");
        System.out.println("getRequestsForDonor_success");

        int donorId = 3;
        List<Requests> result = requestsDao.getRequestsForDonor(donorId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}