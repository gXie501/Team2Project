import java.util.ArrayList;

public interface MessageInterface {
    public void sendMessage(User sender, User receiver, String content, String messageFile);
<<<<<<< HEAD
    public void deleteMessage(User sender, User receiver, String content);
=======
    public void deleteMessage(User sender, User receiver, String content, String messageFile);
>>>>>>> refs/remotes/origin/nehasbranch
    public ArrayList<String> retreiveMessages(User sender, User receiver, String messageFile);
}