import java.util.*;
import java.io.*;

/**
 * Team Project -- Run Local Test for Social Media App
 * 
 * Handles all user objects - stores them, manipulates blockedUser and
 * friendUser arraylist, etc.
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */
public class UserDatabase implements UserInterface {
    public static ArrayList<User> users = new ArrayList<>();
    public Object gatekeeper = new Object();

    public ArrayList<User> getUsers() {
        synchronized (gatekeeper) {
            return users;
        }
    }

    public boolean createUser(String username, String password, String pfp, boolean restrictMessage) {
      // checks if a user object with this username already exists
      synchronized (gatekeeper) {
            if (returnUser(username) != null) {
                return false;
            } else {
                // create user
                User u = new User(username, password, pfp, restrictMessage, new ArrayList<User>(), new
                                ArrayList<User>());
                // add new user to users arraylist
                users.add(u);
                return true;
            }
        }
    }

    public boolean login(String username, String password) {
      // check to see if username exists\
        synchronized (gatekeeper) {
            if (returnUser(username) != null) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

   // username, pfp, pw, restrictMessages, friends, blocked
    public boolean blockUser(User user, User blockUser) {
        synchronized (gatekeeper) {
        // go through each user
            for (int i = 0; i < users.size(); i++) {
            // check to see if we have the right user
                if (users.get(i).equals(user)) {
                    // get the user from the user array
                    User updatedUser = users.get(i);
                    // update the blocked users
                    ArrayList<User> updatedBlocked = users.get(i).getBlocked();
                    updatedBlocked.add(blockUser);
                    updatedUser.setBlocked(updatedBlocked);
                    // set the user in users array to be the updated user
                    users.set(i, updatedUser);
                    return true;
                }
            }
            return false;
        }
    }

    public boolean friendUser(User user, User friendUser) {
        synchronized (gatekeeper) {
        // go through each user
            for (int i = 0; i < users.size(); i++) {
            // check to see if we have the right user
                if (users.get(i).equals(user)) {
                    // get the user from the user array
                    User updatedUser = users.get(i);
                    // update the friend users
                    ArrayList<User> updatedFriends = users.get(i).getFriends();
                    updatedFriends.add(friendUser);
                    updatedUser.setFriends(updatedFriends);
                    // set the user in users array to be the updated user
                    users.set(i, updatedUser);
                    return true;
                }
            }
            return false;
        }
    }

   // return a use object with the giver username
    public User returnUser(String username) {
        synchronized (gatekeeper) {
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    return u;
                }
            }
            return null;
        }
    }
}
