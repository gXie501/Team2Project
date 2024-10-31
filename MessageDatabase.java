import java.io.*;
public class MessageDatabase implements MessageInterface {

    public void sendMessage(User sender, User receiver, String content, String messageFile) {
        Message message = new Message(sender, receiver, content);
        
        try (PrintWriter pw = new PrintWriter(messageFile)) {
            pw.println(sender.getUsername() + ";" + receiver.getUsername() + ";" + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteMessage(User sender, User receiver, String content) {

    }

    public Message[] retreiveMessages(User user1, User user2) {
        
        

    }
    
}