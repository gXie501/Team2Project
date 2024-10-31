public class Message implements MessageObjectInterface{
    // message object
    private User sender; //user who sent message
    private User receiver; //user who received message
    private String messageContents;

    public Message(User sender, User receiver, String messageContents){
        this.sender = sender;
        this.receiver = receiver;
        this.messageContents = messageContents;
    }
    public User getSender() {
        return this.sender;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public String getMessageContents() {
        return this.messageContents;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}