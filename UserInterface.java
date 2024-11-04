import java.util.ArrayList;

/**
 * Interface defining methods for handling user accounts in the UserDatabase.
 * 
 * @version Nov. 3, 2024
 */
public interface UserInterface {
    boolean createUser(String username, String password, String profilePicture, boolean restrictMessage);
    boolean login(String username, String password);
    boolean blockUser(User user, User blockUser);
    boolean friendUser(User user, User friendUser);
}
