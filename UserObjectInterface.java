import java.util.ArrayList;
public interface UserObjectInterface {
    String getUsername();
    String getPassword();
    String getPfp();
    ArrayList<String> getBlocked();
    ArrayList<String> getFriends();
    Boolean getRestrictMessages();
    
    void setUsername(String username);
    void setPassword(String password);
    void setPfp(String pfp);
    void setBlocked(ArrayList<String> blocked);
    void setFriends(ArrayList<String> friends);
    void setRestrictMessages(Boolean restrictMessages);
    boolean equals(User user);
    String toString(User user);
}
