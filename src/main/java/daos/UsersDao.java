package daos;

import business.Requests;
import business.Users;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class UsersDao extends Dao implements UsersDaoInterface {

    public UsersDao(String dbName) {
        super(dbName);
    }
    public UsersDao(Connection con) {
        super(con);
    }

    // Register method: Hashing happens here only
    @Override
    public int register(String username, String email, String password, int role) {
        int newId = -1;
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash here

        try {
            con = this.getConnection();
            String insertQuery = "INSERT INTO users(userName, email, password, role) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashPassword);
            ps.setInt(4, role);

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error during register: " + e.getMessage());
        } finally {
            freeConnection("Closing register resources");
        }

        return newId;
    }

    // Login method: Verifies bcrypt hash
    @Override
    public Users login(String uemail, String pword) {
        Users user = null;
        try {
            con = getConnection();
            String query = "SELECT * FROM users WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, uemail);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(pword, hashedPassword)) {
                    int userId = rs.getInt("userId");
                    String username = rs.getString("username"); // Match your DB column name!
                    String email = rs.getString("email");
                    String profilePic = rs.getString("profilePic");
                    int role = rs.getInt("role");

                    user = new Users(userId, username, email, hashedPassword, profilePic, role);
                }
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        } finally {
            freeConnection("Closing login resources");
        }

        return user;
    }


//    @Override
//    public int register(String username, String email, String password, int role) {
//        int newId = -1;
//        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//        try {
//            con = this.getConnection();
//
//            // If email is unique, proceed with the insert
//            String insertQuery ="INSERT INTO users(userName, email, password, role) VALUES (?, ?, ?, ?)";
//            ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, username);
//            ps.setString(2, email);
//            ps.setString(3, hashPassword);
//            ps.setInt(4, role);
//
//            ps.executeUpdate();
//
//            rs = ps.getGeneratedKeys();
//
//            if (rs.next()) {
//                newId = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            System.err.println("\tA problem occurred during the register method:");
//            System.err.println("\t" + e.getMessage());
//        } finally {
//            freeConnection("A problem occurred when closing down the register method: ");
//        }
//        return newId;
//    }
//
//    public Users login(String uemail, String pword) {
//        Users user = null;
//
//        try {
//            con = getConnection();
//            String query = "SELECT * FROM users WHERE email = ?";
//            ps = con.prepareStatement(query);
//            ps.setString(1, uemail);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String hashedPassword = rs.getString("password");
//
//                if (BCrypt.checkpw(pword, hashedPassword)) {
//                    int userId = rs.getInt("userId");
//                    String username = rs.getString("username"); // check your DB column spelling!
//                    String email = rs.getString("email");
//                    String profilePic = rs.getString("profilePic");
//                    int role = rs.getInt("role");
//
//                    user = new Users(userId, username, email, hashedPassword, profilePic, role);
//                } else {
//                    System.out.println("Password check failed for user: " + uemail);
//                }
//            } else {
//                System.out.println("No user found with email: " + uemail);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Exception in login(): " + e.getMessage());
//        } finally {
//            freeConnection("Exception closing resources in login()");
//        }
//
//        return user;
//    }


//    public Users login(String uemail, String pword) {
//        Users user = null;
//
//        try {
//            try {
//                con = getConnection();
//                String query = "SELECT * FROM users WHERE email = ?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, uemail);
//                rs = ps.executeQuery();
//
//                if (rs.next()) {
//                    String password = rs.getString("password");
//                    if (BCrypt.checkpw(pword, password)) {
//                        int userId = rs.getInt("userId");
//                        String username = rs.getString("userName");
//                        String email = rs.getString("email");
//                        String profilePic = rs.getString("profilePic");
//                        int role = rs.getInt("role");
//
//                        user = new Users(userId, username, email, password, profilePic, role);
//                    }
//                }
//            } catch (SQLException e) {
//                System.out.println("Exception occurred in the Login() method: " + e.getMessage());
//            } finally {
//                freeConnection("Exception occurred in the final section of the Login() method: ");
//            }
//        return user;
//    }

//    public Users login(String uemail, String pword) {
//        Users user = null;
//        try {
//            con = getConnection();
//            String query = "SELECT * FROM users WHERE email = ?";
//            ps = con.prepareStatement(query);
//            ps.setString(1, uemail);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String passwordFromDB = rs.getString("password");
//                System.out.println("Input email: " + uemail);
//                System.out.println("Entered password: " + pword);
//                System.out.println("Hashed in DB: " + passwordFromDB);
//                System.out.println("Password match? " + BCrypt.checkpw(pword, passwordFromDB));
//
//                if (BCrypt.checkpw(pword, passwordFromDB)) {
//                    int userId = rs.getInt("userId");
//                    String username = rs.getString("userName");
//                    String email = rs.getString("email");
//                    String profilePic = rs.getString("profilePic");
//                    int role = rs.getInt("role");
//
//                    user = new Users(userId, username, email, passwordFromDB, profilePic, role);
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception in login(): " + e.getMessage());
//        } finally {
//            freeConnection("Error closing resources in login()");
//        }
//        return user;
//    }


//    @Override
//    public Users login(String uemail, String pword) {
//        Users user=null;
//        try {
//            con = getConnection();
//
//            String query = "Select * from users where email=?";
//            ps = con.prepareStatement(query);
//            ps.setString(1,uemail);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String password = rs.getString("password");
//                if(BCrypt.checkpw(pword, password)){
//                    int userId = rs.getInt("userId");
//                    String username = rs.getString("userName");
//                    String email = rs.getString("email");
//                    String profilePic = rs.getString("profilePic");
//                    int role = rs.getInt("role");
//
//                    user = new Users(userId, username, email, password, profilePic,  role);
//                }
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Exception occurred in the Login() method: " + e.getMessage());
//        } finally {
//            freeConnection("Exception occurred in the final section of the login method: ");
//        }
//        return user;
//    }

    @Override
    public boolean checkEmail(String uemail) {
        boolean isPresent = false;
        try {
            con = this.getConnection();
            String query = "SELECT * from users where email=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, uemail);

            rs = ps.executeQuery();

            if (rs.next()) {
                isPresent = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" A problem occurred during the checkEmail() method:");
        } finally {
            freeConnection("An error occurred when shutting down the checkEmail() method: ");
        }
        return isPresent;
    }

    @Override
    public boolean checkUsername(String uname) {
        boolean isPresent = false;
        try {
            con = this.getConnection();
            String query = "SELECT * from users where username=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, uname);

            rs = ps.executeQuery();

            if (rs.next()) {
                isPresent = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" A problem occurred during the checkUsername() method:");
        } finally {
            freeConnection("An error occurred when shutting down the checkUsername() method: ");
        }
        return isPresent;
    }

    @Override
    public int updateUserProfile(Users u) {
        int rowsAffected = 0;
        try {
            con = getConnection();

            String hashPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            String command = "UPDATE users SET email=? ,userName=? , profilePic=? , password=? , role=? WHERE userID=?";
            ps = con.prepareStatement(command);
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getProfilePic());
            ps.setString(4, hashPassword);
            ps.setInt(5, u.getRole());
            ps.setInt(6, u.getUserId());

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred in the updateUserProfile() method: " + e.getMessage());
        } finally {
            freeConnectionUpdate("An error occurred when shutting down the updateUserProfile() method: ");
        }
        return rowsAffected;
    }

    public Users getUserById(int id) {
        Users u = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM USERS WHERE userID = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");
                String username = rs.getString("userName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String profilePic = rs.getString("profilePic");
                int role = rs.getInt("role");

                u = new Users(userId, username, email, password, profilePic, role);
            }
        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the getUserById() method: " + e.getMessage());
        }
        finally
        {
            freeConnection("An error occurred when shutting down the getUserById() method: ");
        }
        return u;
    }

    @Override
    public int deleteUserById(int userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rowsAffected = 0;
        try {
            con = getConnection();

            String command = "DELETE FROM Users WHERE userID=?";
            ps = con.prepareStatement(command);
            ps.setInt(1, userId);

            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println("An error occurred in the deleteUserById() method: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection("");
                }
            } catch (SQLException e)
            {
                System.out.println("An error occurred when shutting down the deleteUserById() method: " + e.getMessage());
            }
        }
        return rowsAffected;
    }

}
