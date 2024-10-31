import java.util.ArrayList;

public interface MessageInterface {
    public void sendMessage(User sender, User receiver, String content, String messageFile);
    public void deleteMessage(User sender, User receiver, String content, String messageFile);
    public ArrayList<String> retreiveMessages(User sender, User receiver, String messageFile);
}