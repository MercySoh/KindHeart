package daos;

import business.Donations;
import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonationsDaoTest {

    @Test
    void addDonation_success() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("addDonation_success");

        int userId = 1;
        String itemName = "Rice Pack";
        String description = "5kg bag of rice";
        int quantity = 2;
        String category = "Food";
        String image = "default.png";

        int result = donationsDao.addDonation(userId, itemName, description, quantity, category, image);

        donationsDao.deleteDonationById(10);
        donationsDao.updateIncrement("donations", 10);

        assertTrue((result > 0));
    }

    @Test
    void addDonation_noImage_usesDefault() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("addDonation_noImage_usesDefault");

        int userId = 1;
        String itemName = "Canned bean";
        String description = "150g canned beab";
        int quantity = 5;
        String category = "Food";
        String image = "";

        int result = donationsDao.addDonation(userId, itemName, description, quantity, category, image);
        donationsDao.deleteDonationById(10);
        donationsDao.updateIncrement("donations", 10);

        assertTrue((result > 0));
    }

    @Test
    void getDonationById_success() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("getDonationById_success");

        int donationId = 1;
        LocalDate datePosted = LocalDate.of(2025,2,10);

        Donations expResult = new Donations(1, 1,"Canned Beans","12 cans of beans - Best before:20 Dec 2025",  12, "Food","default.png",1,datePosted);
        Donations result = donationsDao.getDonationById(donationId);
        assertEquals(expResult, result);
    }

    @Test
    void getAllDonations() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("getAllDonations");
        ArrayList<Donations> result = (ArrayList<Donations>) donationsDao.getAllDonations();
        assertEquals(5, result.size());
    }

    @Test
    void getDonationsByStatus_success() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("getDonationsByStatus_success");

        int status = 2;
        LocalDate datePosted = LocalDate.of(2025,2,19);

        Donations expResult = new Donations(3, 3,"Instant noodles","Unopened Koka Chicken Noodle - 10 Pack X 850G - Best before: 18 September 2025",  3, "Food","default.png",2,datePosted);
        ArrayList<Donations> result = (ArrayList<Donations>) donationsDao.getDonationsByStatus(status);

        Donations actual = result.stream()
                .filter(d -> d.getDonationId() == 3)
                .findFirst()
                .orElse(null);

        assertNotNull(actual);
        assertEquals(expResult, actual);
    }

    @Test
    void getDonationsByDonorId_success() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("getDonationsByDonorId_success");

        int donorId = 5;
        List<Donations> result = donationsDao.getDonationsByDonorId(donorId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void updateDonationStatus_success() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("updateDonationStatus_success");

        int donationId = 5;
        int newStatus = 2;
        int result = donationsDao.updateDonationStatus(donationId, newStatus);

        assertTrue(result > 0);
    }

    @Test
    void deleteDonationById() {
        DonationsDao donationsDao = new DonationsDao("kindhearttest");
        System.out.println("deleteDonationById");

        donationsDao.addDonation(1,"Rice Pack","5kg bag of rice",2,"Food","default.png");
        LocalDate datePosted = LocalDate.of(2025,5,12);

        Donations d = new Donations(10,1,"Rice Pack","5kg bag of rice",2,"Food","default.png",1,datePosted);
        int id = d.getDonationId();
        int expResult = 1;

        int result = donationsDao.deleteDonationById(id);
        donationsDao.updateIncrement("donations", 10);
        assertEquals(expResult, result);
    }

}