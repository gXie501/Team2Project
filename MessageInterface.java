import java.util.ArrayList;

/**
 * Interface defining methods for handling messages in the MessageDatabase.
 * 
 * @version Nov. 3, 2024
 */
public interface MessageInterface {
    void sendMessage(User sender, User receiver, String content, String messageFile);
    void deleteMessage(User sender, User receiver, String content, String messageFile);
    ArrayList<String> retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile);
}
