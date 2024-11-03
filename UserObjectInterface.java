import java.util.ArrayList;
/**
 * Team Project -- Run Local Test for Social Media App
 * 
 * Contains User Object methods
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */
public interface UserObjectInterface {
    String getUsername();
    String getPassword();
    String getPfp();
    ArrayList<User> getBlocked();
    ArrayList<User> getFriends();
    Boolean getRestrictMessages();
    
    void setUsername(String username);
    void setPassword(String password);
    void setPfp(String pfp);
    void setBlocked(ArrayList<User> blocked);
    void setFriends(ArrayList<User> friends);
    void setRestrictMessages(Boolean restrictMessages);
    boolean equals(User user);
   
}
