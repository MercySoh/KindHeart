package business;

import java.time.LocalDate;
import java.util.Objects;

public class Requests {
//  `requestId` int(11) NOT NULL,
//  `donationId` int(11) NOT NULL,
//  `recipientId` int(11) NOT NULL,
//  `status` int(1) DEFAULT 1 COMMENT '1-pending,2-approved,3-rejected,4-collected',
//  `requestedDate` date DEFAULT current_timestamp()

    private int requestId;
    private int donationId;
    private int recipientId;
    private int status;
    private LocalDate requestedDate;
    private Donations donation;
    private Users recipient;

    public Requests() {
    }

    public Requests(int requestId, int donationId, int recipientId, int status, LocalDate requestedDate) {
        this.requestId = requestId;
        this.donationId = donationId;
        this.recipientId = recipientId;
        this.status = status;
        this.requestedDate = requestedDate;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDate requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Donations getDonation() {
        return donation;
    }

    public void setDonation(Donations donation) {
        this.donation = donation;
    }

    public Users getRecipient() {
        return recipient;
    }

    public void setRecipient(Users recipient) {
        this.recipient = recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requests requests = (Requests) o;
        return requestId == requests.requestId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }


}
