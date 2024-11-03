import java.util.ArrayList;

public interface MessageInterface {
    public void sendMessage(User sender, User receiver, String content, String messageFile);
    public void deleteMessage(User sender, User receiver, String content, String messageFile);
    public ArrayList<String> retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile);
}
