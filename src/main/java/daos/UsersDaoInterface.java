package daos;

import business.Users;

public interface UsersDaoInterface {

        //public boolean register(Users user);
        public int register(String username, String email, String password, int role);
        public Users login(String uemail, String pword);
        public boolean checkEmail(String uemail);
        public boolean checkUsername(String uname);
        public int updateUserProfile(Users user);
        public Users getUserById(int id);
        public int deleteUserById(int userId);

}
