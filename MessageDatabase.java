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

    public ArrayList<String> retreiveMessages(User user1, User user2, String messageFile) {
        ArrayList<String> messages = new ArrayList<>();
        String userOneUsername = user1.getUsername();
        String userTwoUsername = user2.getUsername();

        try (BufferedReader bfr = new BufferedReader(new FileReader(messageFile))) {
            
            while (bfr.readLine() != null) {
                String currentMessage = bfr.readLine();
                String firstUser = currentMessage.substring(0, currentMessage.indexOf(";"));
                String newMessage = currentMessage.substring(currentMessage.indexOf(";") + 1);
                String secondUser = newMessage.substring(0, newMessage.indexOf(";"));

                if ((firstUser.equals(userOneUsername) || firstUser.equals(userTwoUsername)) && (secondUser.equals(userOneUsername) || secondUser.equals(userTwoUsername))) {
                    messages.add(currentMessage);
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;

    }
    
}