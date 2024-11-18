import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Database.UserDatabase;
import Database.MessageDatabase;
import Database.User;

public class ClientHandler implements Runnable, ClientHandlerInterface {
    private Socket clientSocket;
    private UserDatabase userDatabase;  // This will store the reference to the UserDatabase instance
    private MessageDatabase messageDatabase;
    String username;
    String password;

    // Constructor that accepts the socket and the UserDatabase instance
    public ClientHandler(Socket socket, UserDatabase userDatabase, MessageDatabase messageDatabase) {
        this.clientSocket = socket;
        this.userDatabase = userDatabase;  // Store the reference to the UserDatabase
        this.messageDatabase = messageDatabase;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);

                if (message.equals("login")) {
                    String login = reader.readLine();
                    String[] loginInfo = login.split(",");
                    username = loginInfo[0];
                    password = loginInfo[1];

                    System.out.println("Received login info from client: " + username + ", " + password);

                    // Check if the user exists in the UserDatabase
                    if (userDatabase.returnUser(username) != null) {
                        // If the user exists, validate the password
                        if (userDatabase.login(username, password)) {
                            writer.println("Login successful");
                            writer.flush();
                            System.out.println("Login successful for: " + username);
                        } else {
                            writer.println("Invalid password");
                            writer.flush();
                            System.out.println("Invalid password for: " + username);
                        }
                    } else {
                        writer.println("User does not exist");
                        writer.flush();
                        System.out.println("User does not exist: " + username);
                    }
                } else if (message.equals("createUser")) {
                
                    String login = reader.readLine();
                    String[] loginInfo = login.split(",");
                    String username = loginInfo[0];
                    String password = loginInfo[1];

                    System.out.println("Received new user information from client: " + username + ", " + password);

                    // Check if the user exists in the UserDatabase
                    if (userDatabase.returnUser(username) != null) {
                        writer.println("User Already Exists");
                        writer.flush();
                    } else {
                        writer.println("New User Created");
                        System.out.println("New user created: " + username);
                        userDatabase.createUser(username, password, "123", false); //INCLUDE PROFILEPIC

                        // TESTING:
                        // userDatabase.createUser("user2", "randomPass", "123", false);
                    }
                } else if (message.equals("sendMessage")) {
                    // sends receiver
                    // sends user
                    // sends message
                    User receiver = userDatabase.returnUser(reader.readLine());
                    System.out.println("Received the receiving user " + receiver + " from client");
                    User currentUser = userDatabase.returnUser(reader.readLine());
                    System.out.println("Received current user " + currentUser + " from client");
                    String sendWhat = reader.readLine();
                    System.out.println("Received and sending message  '" + sendWhat + "'");

                
                    
                    messageDatabase.sendMessage(currentUser, receiver, sendWhat, "testFile.txt");
                    System.out.println("Sent message from " + currentUser.getUsername() + " to " + receiver.getUsername());
                    
                } else if (message.equals("searchUser")) {
                    String searcher = reader.readLine();
                    System.out.println("Received user client intends to search: " + searcher);
                
                    if (userDatabase.returnUser(searcher) == null) {
                        writer.println("User not found");
                        writer.flush();
                    } else {
                        writer.println("User found");
                        writer.flush();
                        
                        
                    }
                } else if (message.equals("blockUser")) {
                    // sends current user
                    User username = userDatabase.returnUser(reader.readLine());
                    User blocked = userDatabase.returnUser(reader.readLine());
                    userDatabase.blockUser(username, blocked);
                    
                    // sends usersBlocked to check if added to array
                    ArrayList<User> usersBlocked = username.getBlocked();
                    String users = "";
                    for (User user : usersBlocked) {
                        users  += user.getUsername() + " ";
                    }
                    writer.println(users);
                    writer.flush();
                } else if(message.equals("friendUser"))
                {
                    // sends current user
                    User username = userDatabase.returnUser(reader.readLine());
                    User friend = userDatabase.returnUser(reader.readLine());
                    userDatabase.friendUser(username, friend);
                    
                    // sends usersBlocked to check if added to array
                    ArrayList<User> userFriend = username.getFriends();
                    String users = "";
                    for (User user : userFriend) {
                        users  += user.getUsername() + " ";
                    }
                    writer.println(users);
                    writer.flush();
                } else if (message.equals("restrict messages")) {
                    String restricted = reader.readLine();
                    if (restricted.equals("yes")) {
                        userDatabase.restrictUser(username, true);
                    } else {
                        userDatabase.restrictUser(username, false);
                    }
                
                    

                } else if (message.equals("exit")) {
                    // Handle client disconnection
                    System.out.println("Client requested disconnection.");
                    break;
                } else {
                    // Echo or process other types of messages
                    writer.println("Server received: " + message);
                }
            }

            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
