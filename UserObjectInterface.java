import java.util.ArrayList;
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
}
