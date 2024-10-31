import java.util.*;
import java.io.*;

public class UserDatabase implements UserInterface {

   public void createUser(String username, String password, String pfp, boolean restrictMessage) {
      User u = new User(username, password, pfp, restrictMessage, new ArrayList<String>(), new ArrayList<String>());
      try (PrintWriter pw = new PrintWriter(new FileWriter("userFile.txt", true))) {
         pw.println(u.getUsername() + ";" + u.getPassword() + ";" + "" + ";" + "" + ";");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public boolean login(String username, String password) {
      if (searchUser(username)) {
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


public boolean blockUser(User user, User blockUser) {
    if (searchUser(user.getUsername())) { 
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("userFile.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line); 
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts[0].equals(user.getUsername())) {
                    String blocked = parts[3];
                    blocked += blockUser.getUsername() + ",";
                    ArrayList<String> block = user.getBlocked();
                    block.add(blockUser.getUsername());
                    user.setBlocked(block);
                    lines.set(i, parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + blocked + ";" + parts[4]);
                    break;
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter("userFile.txt"))) {
                for (String updatedLine : lines) {
                    pw.println(updatedLine); 
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
    }
    return false; 
}



   public boolean friendUser(User user, User friendUser) {
    if (searchUser(user.getUsername())) { 
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("userFile.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line); 
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts[0].equals(user.getUsername())) {
                    String friend = parts[2];
                    friend += friendUser.getUsername() + ",";
                    ArrayList<String> f = user.getFriends();
                    f.add(friendUser.getUsername());
                    user.setFriends(f);
                    lines.set(i, parts[0] + ";" + parts[1] + ";" + friend + ";" + parts[3] + ";" + parts[4]);
                    break;
                }
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter("userFile.txt"))) {
                for (String updatedLine : lines) {
                    pw.println(updatedLine); 
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
    }
    return false; 
   }


   public boolean searchUser(String username) {
      try (BufferedReader br = new BufferedReader(new FileReader("userFile.txt"))) {
         String line = br.readLine();
         while (line != null) {
            String[] parts = line.split(";");
            if (parts[0].equals(username)) {
               return true; 
            }
            line = br.readLine(); 
         }
      } catch (IOException e) {
         return false;
      }
      return false; 
   }
}
