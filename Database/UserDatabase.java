package Database;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

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
    public static Object userGatekeeper = new Object();

    public ArrayList<User> getUsers() {
        synchronized (userGatekeeper) {
            return users;
        }
    }

    // new change
    public UserDatabase() {

        synchronized (userGatekeeper) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("userFile.txt"))) {
                // Deserialize the list of users from the file
                users = (ArrayList<User>) in.readObject(); // Read the list of User objects
            } catch (IOException e) {
                // Handle the case where the file does not exist or cannot be read
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // Handle the case where the class type cannot be found (should not happen if the User class is available)
                e.printStackTrace();
            }
        }
    }

    public boolean createUser(String username, String password, String pfp, boolean restrictMessage) {
      // checks if a user object with this username already exists
        synchronized (userGatekeeper) {
            if (returnUser(username) != null) {
                return false;
            } else {
                // create user
                User u = new User(username, password, pfp, restrictMessage, new ArrayList<User>(), new
                                ArrayList<User>());
                // add new user to users arraylist
                users.add(u);
                saveUsers();
                return true;
            }
        }
    }

    public boolean login(String username, String password) {
      // check to see if username exists\
        synchronized (userGatekeeper) {
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

    public void restrictUser(String username, boolean restrict) {
        synchronized (userGatekeeper) {
            // go through each user
                for (int i = 0; i < users.size(); i++) {
                // check to see if we have the right user
                    if (users.get(i).getUsername().equals(username)) {
                        // get the user from the user array
                        User updatedUser = users.get(i);
                        // update the restrict status
                        updatedUser.setRestrictMessages(restrict);
                        // set the user in users array to be the updated user
                        users.set(i, updatedUser);
                        saveUsers();
                    }
                }
            }
    }

   // username, pfp, pw, restrictMessages, friends, blocked
    public boolean blockUser(User user, User blockUser) {
        synchronized (userGatekeeper) {
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
                    saveUsers();
                    return true;
                }
            }
            return false;
        }
    }

    public boolean friendUser(User user, User friendUser) {
        synchronized (userGatekeeper) {
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
                    saveUsers();
                    return true;
                }
            }
            return false;
        }
    }

   // return a user object with the given username
    public User returnUser(String username) {
        synchronized (userGatekeeper) {
            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    return u;
                }
            }
            return null;
        }
    }

    public void saveUsers() {
        synchronized (userGatekeeper) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("userFile.txt"))) {
                out.writeObject(users); // Serialize the updated list of users
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

