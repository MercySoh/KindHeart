package daos;

import business.Donations;

import java.util.List;

public interface DonationsDaoInterface {
        public int addDonation(int uId, String iName, String desc, int qty, String ctgy, String img);
        //int uId, String iName,String desc,int qty, String ctgy,String img, int status
        public Donations getDonationById(int dntId);
        public List<Donations> getAllDonations();
        public List<Donations> getDonationsByStatus(int dntStatus);
        public List<Donations> getDonationsByDonorId(int donorId);
        public int updateDonationStatus(int dntId, int newStatus);
        public int deleteDonationById(int dntId);
        public boolean updateDonationStatusByRequestId(int requestId, int status);
}

