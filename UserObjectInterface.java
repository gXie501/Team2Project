import java.util.ArrayList;

/**
 * Interface defining basic methods for a User object.
 * 
 * @version Nov. 3, 2024
 */
public interface UserObjectInterface {
    String getUsername();
    String getPassword();
    String getProfilePicture();
    ArrayList<User> getBlocked();
    ArrayList<User> getFriends();
    Boolean getRestrictMessages();
    void setUsername(String username);
    void setPassword(String password);
    void setProfilePicture(String profilePicture);
    void setBlocked(ArrayList<User> blocked);
    void setFriends(ArrayList<User> friends);
    void setRestrictMessages(Boolean restrictMessages);
    boolean equals(User user);
}
