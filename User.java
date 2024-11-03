import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
/**
 * Team Project -- Run Local Test for Social Media App
 * 
 * Creates User object
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */
public class User implements UserObjectInterface{
    private String username; //username of the users account
    private String pfp; //profile picture of the users account
    private String password; //password of the users account
    private Boolean restrictMessages; //indicates whether or not the user will allow messages from users who aren't their friend
    ArrayList <User> friends = new ArrayList<>(); //a list of mutual acoounts to this users account
    ArrayList <User> blocked = new ArrayList<>(); //a list of blocked acoounts to this users account
  
//username, pfp, pw, restrictMessages, friends, blocked
    public User (String username, String password, String pfp, Boolean restrictMessages, ArrayList <User> friends, ArrayList <User> blocked){
        this.username = username;
        this.password = password;
        setPfp(pfp);
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
    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }
    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }
    public void setPfp(String pfp) {
        try {
            BufferedImage image = ImageIO.read(new File(pfp)); // Read the image from the specified path
            String profilePicturePath = "profile_pictures" + "/" + username + "_pfp.png"; // Save as PNG
            ImageIO.write(image, "png", new File(profilePicturePath)); // Write the image to the file

            this.pfp = profilePicturePath; 
        } catch (IOException e) {
            e.printStackTrace(); 

        }
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public Boolean getRestrictMessages() {
        return this.restrictMessages;
    }
    public ArrayList<User> getFriends() {
        return this.friends;
    }
    public ArrayList<User> getBlocked() {
        return this.blocked;
    }
    public String getPfp() {
        return this.pfp;
    }
    public boolean equals(User user) {
        return this.username.equals(user.getUsername()) &&
        this.password.equals(user.getPassword());

    }
}
