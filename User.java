import java.util.*;
public class User {
    private String username;
    private String pfp;
    private String password;
    private Boolean restrictMessages; //indicates whether or not the user will allow messages from users who aren't their friend
    ArrayList <String> friends = new ArrayList<>();
    ArrayList <String> blocked = new ArrayList<>();
  

    public User (String username, String password, String pfp, Boolean restrictMessages, ArrayList <String> friends, ArrayList <String> blocked){
        this.username = username;
        this.password = password;
        this.pfp = pfp;
        this.restrictMessages=restrictMessages;
        this.friends=friends;
        this.blocked=blocked;
    }
    
        
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRestrictMessages(Boolean restrictMessages) {
        this.restrictMessages = restrictMessages;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public void setBlocked(ArrayList<String> blocked) {
        this.blocked = blocked;
    }
    public void setPfp(String pfp) {
        this.pfp = pfp;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public boolean getRestrictMessages() {
        return this.restrictMessages;
    }
    public ArrayList<String> getFriends() {
        return this.friends;
    }
    public ArrayList<String> getBlocked() {
        return this.blocked;
    }
    public String getPfp() {
        return this.pfp;
    }
}
