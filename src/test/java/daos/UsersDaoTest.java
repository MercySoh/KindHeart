package daos;

import business.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoTest {

    @Test
    void register_success() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("register_success");
        String userName = "donortest@gmail.com";
        String email = "donortest@gmail.com";
        String password = "1PassWord@25";
        int role = 1;

        int result = usersDao.register(userName,email,password,role);
        usersDao.deleteUserById(7);
        usersDao.updateIncrement("users", 7);

        assertTrue((result > 0));
    }

    @Test
    void register_failWithDuplicateEmail() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("register_failWithDuplicateEmail");
        String userName = "Gary";
        String email = "donor@example.com";
        String password = "1PassWord@25";
        int role = 1;

        int result = usersDao.register(userName,email,password,role);
        usersDao.updateIncrement("users", 6);
        assertTrue((result == -1));
    }

    @Test
    void register_failWithDuplicateUsername() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("register_failWithDuplicateUsername");
        String userName = "recipient";
        String email = "recipienttest@gmail.com";
        String password = "1PassWord@25";
        int role = 2;

        int result = usersDao.register(userName,email,password,role);
        usersDao.updateIncrement("users", 6);
        assertTrue((result == -1));
    }

    @Test
    void login_success() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("login_success");
        String email = "donor@example.com";
        String password = "1PassWord@25";

        Users expResult = new Users(1,  "donor","donor@example.com",  "$2a$10$LdZyn55grv70TVFYOYUOCulDHnNfgRoRUAvZC.c359yO9nAJXnCU2", "default.png",1);
        Users result = usersDao.login(email,password);
        assertEquals(expResult,result);
    }

    @Test
    void login_UserCantFound() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("login_UserCantFound");
        String email = "Sherry@gmail.com";
        String password = "1PassWord@25";

        Users expResult = null;
        Users result = usersDao.login(email,password);
        assertEquals(expResult,result);
    }

    @Test
    void login_WhenJustEmailMatch() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("login_WhenJustEmailMatch");
        String email = "donor@example.com";
        String password = "password";

        Users result = usersDao.login(email,password);
        assertNull(result);
    }

    @Test
    void login_WhenJustPasswordMatch() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("login_WhenJustPasswordMatch");
        String email = "paul@gmail.com";
        String password = "1PassWord@25";

        Users result = usersDao.login(email,password);
        assertNull(result);
    }

    @Test
    void checkEmail_isPresent() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("checkEmail_isPresent");
        String email = "donor@example.com";
        boolean expResult = true;
        boolean result = usersDao.checkEmail(email);

        assertEquals(expResult, result);
    }

    @Test
    void checkEmail_notPresent() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("checkEmail_notPresent");
        String email = "Henry123@gmail.com";
        boolean expResult = false;
        boolean result = usersDao.checkEmail(email);

        assertEquals(expResult, result);
    }

    @Test
    void checkUsername_isPresent() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("checkUsername_isPresent");
        String username = "donor";
        boolean expResult = true;
        boolean result = usersDao.checkUsername(username);

        assertEquals(expResult, result);
    }

    @Test
    void checkUsername_notPresent() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("checkUsername_notPresent");
        String username = "Henry";
        boolean expResult = false;
        boolean result = usersDao.checkUsername(username);

        assertEquals(expResult, result);
    }

    @Test
    void getUserById_Found() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("getUserById_Found");
        int userID = 1;

        Users expResult = new Users(1,  "donor","donor@example.com",  "$2a$10$LdZyn55grv70TVFYOYUOCulDHnNfgRoRUAvZC.c359yO9nAJXnCU2", "default.png",1);
        Users result = usersDao.getUserById(userID);
        assertEquals(expResult, result);
    }

    @Test
    void getUserById_NotFound() {
        UsersDao usersDao = new UsersDao("kindhearttest");
        System.out.println("getUserById_NotFound");

        int userID = 15;
        Users expResult = null;
        Users result = usersDao.getUserById(userID);
        assertEquals(expResult, result);
    }
}