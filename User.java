import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Represents a user in the social media application.
 * 
 * @version Nov. 3, 2024
 */
public class User implements UserObjectInterface {
    private String username;
    private String profilePicture;
    private String password;
    private Boolean restrictMessages;
    private ArrayList<User> friends;
    private ArrayList<User> blocked;

    /**
     * Constructs a User with the specified attributes.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param profilePicture the profile picture path of the user
     * @param restrictMessages whether the user restricts messages from non-friends
     * @param friends a list of the user's friends
     * @param blocked a list of users blocked by the user
     */
    public User(String username, String password, String profilePicture, Boolean restrictMessages,
                ArrayList<User> friends, ArrayList<User> blocked) {
        this.username = username;
        this.password = password;
        setProfilePicture(profilePicture);
        this.restrictMessages = restrictMessages;
        this.friends = friends;
        this.blocked = blocked;
    }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRestrictMessages(Boolean restrictMessages) { this.restrictMessages = restrictMessages; }
    public void setFriends(ArrayList<User> friends) { this.friends = friends; }
    public void setBlocked(ArrayList<User> blocked) { this.blocked = blocked; }

    /**
     * Sets the profile picture path and saves the image to the specified directory.
     * 
     * @param profilePicture the path to the profile picture file
     */
    public void setProfilePicture(String profilePicture) {
        try {
            BufferedImage image = ImageIO.read(new File(profilePicture));
            if (image == null) throw new IOException("Image file could not be found!");
            String profilePicturePath = "profile_pictures/" + username + ".png";
            ImageIO.write(image, "png", new File(profilePicturePath));
            this.profilePicture = profilePicturePath;
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public Boolean getRestrictMessages() { return this.restrictMessages; }
    public ArrayList<User> getFriends() { return this.friends; }
    public ArrayList<User> getBlocked() { return this.blocked; }
    public String getProfilePicture() { return this.profilePicture; }

    @Override
    public boolean equals(User user) {
        return this.username.equals(user.getUsername()) && this.password.equals(user.getPassword());
    }
}
