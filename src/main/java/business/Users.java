package business;

import java.util.Objects;

public class Users {

// `userId` int(11) NOT NULL,
//  `username` varchar(50) NOT NULL,
//  `email` varchar(100) NOT NULL,
//  `password` varchar(255) NOT NULL,
//  `profilePic` varchar(255) NOT NULL DEFAULT 'default.png',
//  `role` int(1) NOT NULL COMMENT '1-Donor,2-Recipient'

 private int userId;
 private String username;
 private String email;
 private String password;
 private String profilePic;
 private int role;

    public Users() {
    }

    //All
    public Users(int userId, String username, String email, String password, String profilePic, int role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.role = role;
    }

    //Without userID
    public Users(String username, String email, String password, String profilePic, int role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.role = role;
    }

    //Without userID and profilePic
    public Users(String username, String email, String password, int role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId == users.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", role=" + role +
                '}';
    }
}
