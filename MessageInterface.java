public interface MessageInterface {
    public void sendMessage(User sender, User receiver, String content);
    public void deleteMessage(User sender, User receiver, String content);
    public Message[] retreiveMessages(User sender, User receiver);
}