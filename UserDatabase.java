import java.util.*;
import java.io.*;
/**
 * Team Project -- Run Local Test for Social Media App
 * 
 * Handles all user objects - stores them, manipulates blockedUser and friendUser arraylist, etc.
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */
public class UserDatabase implements UserInterface {
    ArrayList <User> users = new ArrayList<>();

   public void createUser(String username, String password, String pfp, boolean restrictMessage) {
      //create user
      User u = new User(username, password, pfp, restrictMessage, new ArrayList<User>(), new ArrayList<User>());
      //add new user to users arraylist
      users.add(u);
      //print user to users file
      try (PrintWriter pw = new PrintWriter(new FileWriter("userFile.txt", true))) {
         pw.println(u.getUsername() + ";" + u.getPassword() + ";" + "" + ";" + "" + ";");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public boolean login(String username, String password) {
      if (returnUser(username) != null) {
         try (BufferedReader br = new BufferedReader(new FileReader("userFile.txt"))) {
            String line = br.readLine();
            while (line != null) {
               String[] parts = line.split(";");
               if (parts[0].equals(username)) {
                  if (parts[1].equals(password)) {
                     return true; 
                  }
               }
               line = br.readLine(); 
            }
         } catch (IOException e) {
            return false;
         }
      }
      return false; 
   }

//username, pfp, pw, restrictMessages, friends, blocked
   public boolean blockUser(User user, User blockUser) {
        //go through each user
        for (int i = 0; i < users.size(); i++) {
            //check to see if we have the right user
            if (users.get(i).equals(user)) {
                // get the user from the user array
                User updatedUser = users.get(i);
                //update the blocked users
                ArrayList <User> updatedBlocked  = users.get(i).getBlocked();
                updatedBlocked.add(blockUser);          
                updatedUser.setBlocked(updatedBlocked);
                //set the user in users array to be the updated user
                users.set(i, updatedUser);
                return true;           
            }
        }
        return false;
    }

   public boolean friendUser(User user, User friendUser) {
    //go through each user
    for (int i = 0; i < users.size(); i++) {
        //check to see if we have the right user
        if (users.get(i).equals(user)) {
            // get the user from the user array
            User updatedUser = users.get(i);
            //update the friend users
            ArrayList <User> updatedFriends  = users.get(i).getFriends();
            updatedFriends.add(friendUser);          
            updatedUser.setBlocked(updatedFriends);
            //set the user in users array to be the updated user
            users.set(i, updatedUser);
            return true;           
        }
    }
    return false;   
    }

   //return a use object with the giver username
   public User returnUser(String username) {
    for(User u : users) {
        if(u.getUsername().equals(username)) {
            return u;
        }
    }
    return null;
   }
}
