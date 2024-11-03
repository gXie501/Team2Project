// import java.io.*;
// import java.util.Scanner;
// import java.util.*;

// public class Main {
//     public static void main(String[] args) {
//         UserDatabase userDatabase = new UserDatabase();
//         MessageDatabase messageDatabase = new MessageDatabase();
//         Scanner scanner = new Scanner(System.in);
        
//         // Sample user creation
//         userDatabase.createUser("John", "1234", "john.jpg", false);
//         userDatabase.createUser("Spongebob", "patrick", "spongebob.jpg", true);
        

//         while (true) {
//             System.out.println("Welcome to the Social Media App!");
//             System.out.println("1. Login");
//             System.out.println("2. Exit");
//             System.out.print("Select an option: ");
//             int choice = scanner.nextInt();
//             scanner.nextLine(); 
            
//             if (choice == 1) {
//                 // Login process
//                 System.out.print("Enter username: ");
//                 String username = scanner.nextLine();
//                 System.out.print("Enter password: ");
//                 String password = scanner.nextLine();
                
//                 if (userDatabase.login(username, password)) {
//                     System.out.println("Login successful!");
//                     boolean loggedIn = true;
//                     User currentUser = new User(username, password, "", false, new ArrayList<String>(), new ArrayList<String>());

//                     while (loggedIn) {
//                         System.out.println("1. Send Message");
//                         System.out.println("2. Add Friend");
//                         System.out.println("3. Block User");
//                         System.out.println("4. Retrieve Messages");
//                         System.out.println("5. Logout");
//                         System.out.print("Select an option: ");
//                         int actionChoice = scanner.nextInt();
//                         scanner.nextLine(); // Consume the newline

//                         switch (actionChoice) {
//                             case 1:
//                                 // Send message
//                                 System.out.print("Enter receiver's username: ");
//                                 String receiver = scanner.nextLine();
//                                 System.out.print("Enter message content: ");
//                                 String messageContent = scanner.nextLine();
//                                 messageDatabase.sendMessage(currentUser, new User(receiver, "", "", false, new ArrayList<>(), new ArrayList<>()), messageContent, "messages.txt");
//                                 System.out.println("Message sent!");
//                                 break;
//                             case 2:
//                                 // Add friend
//                                 System.out.print("Enter friend's username: ");
//                                 String friendUsername = scanner.nextLine();
//                                 userDatabase.friendUser(currentUser, new User(friendUsername, "", "", false, new ArrayList<>(), new ArrayList<>()));
//                                 System.out.println("Friend added!");
//                                 break;
//                             case 3:
//                                 // Block user
//                                 System.out.print("Enter username to block: ");
//                                 String blockUsername = scanner.nextLine();
//                                 userDatabase.blockUser(currentUser, new User(blockUsername, "", "", false, new ArrayList<>(), new ArrayList<>()));
//                                 System.out.println("User blocked!");
//                                 break;
//                             /*case 4:
//                                 // Retrieve messages
//                                 System.out.print("Enter other user's username to retrieve messages: ");
//                                 String otherUser = scanner.nextLine();
                                
//                                 ArrayList<String> messages = messageDatabase.retrieveMessages(currentUser, new User(otherUser, "", "", false, new ArrayList<>(), new ArrayList<>()), "messages.txt");
//                                 System.out.println("Messages:");
//                                 for (String msg : messages) {
//                                     System.out.println(msg);
//                                 }
//                                 break;*/
//                             case 4:
//                                 loggedIn = false;
//                                 System.out.println("Logged out!");
//                                 break;
//                             default:
//                                 System.out.println("Invalid option. Try again.");
//                                 break;
//                         }
//                     }
//                 } else {
//                     System.out.println("Login failed. Please check your credentials.");
//                 }
//             } else if (choice == 2) {
//                 System.out.println("Exiting the application. Goodbye!");
//                 break;
//             } else {
//                 System.out.println("Invalid option. Try again.");
//             }
//         }

//         scanner.close();
//     }
// }
