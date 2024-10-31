import java.io.*;
public class MessageDatabase implements MessageInterface {

    public void sendMessage(User sender, User receiver, String content, String messageFile) {
        Message message = new Message(sender, receiver, content);


    }


    public void deleteMessage(User sender, User receiver, String content) {

    }

    public Message[] retreiveMessages(User sender, User receiver) {

    }
    
}