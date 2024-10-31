public interface MessageObjectInterface {
    public User getSender();
    public User getReceiver();
    public String getMessageContents();
    public void setSender(User sender);
    public void setReceiver(User receiver);  
} 
