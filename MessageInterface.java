import java.util.ArrayList;

/**
 * Team Project -- Message Interface for Social Media App
 * 
 * File that outlines the methods in the MessageDatabase class
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */

public interface MessageInterface {
    public void sendMessage(User sender, User receiver, String content, String messageFile);

    public void deleteMessage(User sender, User receiver, String content, String messageFile);

    public ArrayList<String> retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile);
}
