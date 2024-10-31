import java.util.*;
public class User {
    private String username;
    private String pfp;
    private String password;
    private Boolean restrictMessages; //indicates whether or not the user will allow messages from users who aren't their friend
    ArrayList <String> friends = new ArrayList();
    ArrayList <String> blocked = new ArrayList();
  

    public (String user, String password, String pfp, Boolean restrictMessages, ArrayList <String> friends, ArrayList <String> blocked)
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
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getRestrictMessages() {
        return restrictMessages;
    }
    public ArrayList<String> getFriends() {
        return friends;
    }
    public ArrayList<String> getBlocked() {
        return blocked;
    }
    public String getPfp() {
        return pfp;
    }



}



}
