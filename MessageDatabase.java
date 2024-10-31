import java.io.*;
import java.util.ArrayList;
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

    public ArrayList<Message> retreiveMessages(User user1, User user2, String messageFile) {
        ArrayList<Message> messages = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(messageFile))) {

            while (bfr.readLine() != null) {
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        return messages;

    }
    
}