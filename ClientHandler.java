import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Database.UserDatabase;
import Database.MessageDatabase;
import Database.User;

/**
 * Team Project -- Client Handler for the program that processes all the information from the Client
 * 
 * Processes all of the information from the 
 * Client with the run method that will be ran on a 
 * Thread made in Server class.
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 17, 2024
 */

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
                    username = loginInfo[0];
                    password = loginInfo[1];

                    System.out.println("Received new user information from client: " + username + ", " + password);

                    // Check if the user exists in the UserDatabase
                    if (userDatabase.returnUser(username) != null) {
                        writer.println("User Already Exists");
                        writer.flush();
                    } else {
                        writer.println("New User Created");
                        System.out.println("New user created: " + username);
                        userDatabase.createUser(username, password, "123", false); //INCLUDE PROFILEPIC
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
                    System.out.println("Sent message from " + 
                                       currentUser.getUsername() + " to " + 
                                       receiver.getUsername());
                } else if (message.equals("deleteMessage")) {

                    User receiver = userDatabase.returnUser(reader.readLine());
                    System.out.println("Received the receiving user " + receiver + " from client");
                    User currentUser = userDatabase.returnUser(reader.readLine());
                    System.out.println("Received current user " + currentUser + " from client");
                    String sendWhat = reader.readLine();
                    System.out.println("Received and deleting message  '" + sendWhat + "'");

                
                    
                    messageDatabase.deleteMessage(currentUser, receiver, sendWhat, "testFile.txt");


                    
                } else if (message.equals("searchUser")) {
                    String searcher = reader.readLine();
                    System.out.println("User client intends to search: " + searcher);
                
                    if (userDatabase.returnUser(searcher) == null) {
                        writer.println("User not found");
                        writer.flush();
                    } else {
                        writer.println("User found");
                        writer.flush();
                    }
                    System.out.println("done with search user");
                } else if (message.equals("receive message")) {
                    System.out.println("attempting to retrieve a message");
                    String receiver = reader.readLine();
                    System.out.println("Receiver is: " + receiver);
                    ArrayList<String> messageLogs = 
                        messageDatabase.retrieveMessages(username, 
                                                         receiver, 
                                                         "testFile.txt");

                    try {
                        for (String m : messageLogs) {
                            writer.println(m); // send each message as sender;receiver;message
                            writer.flush();
                        }
                        writer.println("END"); // end of message list
                        writer.flush();
                    } catch (Exception e) {
                        System.out.println("Error sending messages to client");
                        e.printStackTrace();
                    }

                } else if (message.equals("blockUser")) {


                    // sends current user
                    User un = userDatabase.returnUser(reader.readLine());
                    User blocked = userDatabase.returnUser(reader.readLine());
                    // prevents user from blocking same user twice
                    if (!un.getBlocked().contains(blocked)) {
                        if (un.getFriends().contains(blocked)) {
                            ArrayList<User> test = un.getFriends();
                            test.remove(blocked);
                            un.setFriends(test);
                        }
                        userDatabase.blockUser(un, blocked);

                        // sends usersBlocked to check if added to array
                        ArrayList<User> usersBlocked = un.getBlocked();
                        String users = "";
                        for (User user : usersBlocked) {
                            users  += user.getUsername() + " ";
                        }
                        writer.println(users);
                        writer.flush();
                    }
                } else if (message.equals("friendUser")) {
                    
                    // sends current user
                    User un = userDatabase.returnUser(reader.readLine());
                    User friend = userDatabase.returnUser(reader.readLine());
                    // prevents user from adding the same friend twice
                    if (!un.getFriends().contains(friend)) {
                        if (un.getBlocked().contains(friend)) {
                            ArrayList<User> test = un.getBlocked();
                            test.remove(friend);
                            un.setBlocked(test);
                        }
                        userDatabase.friendUser(un, friend);

                        // sends userFriend to check if added to array
                        ArrayList<User> userFriend = un.getFriends();
                        String users = "";
                        for (User user : userFriend) {
                            users += user.getUsername() + " ";
                        }
                        writer.println(users);
                        writer.flush();
                    }
                } else if (message.equals("restrict messages")) {
                    String restricted = reader.readLine();
                    if (restricted.equals("yes")) {
                        userDatabase.restrictUser(username, true);
                    } else {
                        userDatabase.restrictUser(username, false);
                    }
                
                    

                } else if (message.equals("Check restrict messages")) {
                    User user = userDatabase.returnUser(reader.readLine());
                    User otherUser = userDatabase.returnUser(reader.readLine()); //User that user is trying to send message to.
                    //check to see if user is able to send messages
                    //if getRestrictMessages is true, anyone can send user messages
                    if (user.getBlocked().contains(otherUser) || otherUser.getBlocked().contains(user)) {
                        writer.println("false");
                    } else if (user.getRestrictMessages() && otherUser.getRestrictMessages() || 
                        !user.getRestrictMessages() && user.getFriends().contains(otherUser) ||
                        !otherUser.getRestrictMessages() && otherUser.getFriends().contains(user)) {
                            writer.println("true");
                    } else {
                        writer.println("false");
                    }
                } else if (message.equals("exit")) {
                    // Handle client disconnection
                    System.out.println("Client requested disconnection.");
                    break;
                } else if (message.equals("Get friends and blocked")) {
                    User user = userDatabase.returnUser(reader.readLine());
                    ArrayList<User> friends = user.getFriends();
                    ArrayList<User> blocked = user.getBlocked();
                    String friendString = "";
                    String blockedString = "";
                    for(User u : friends) {
                        friendString += u.getUsername() + ", ";
                    }
                    for(User u : blocked) {
                        blockedString += u.getUsername() + ", ";
                    }

                    writer.println(friendString);
                    writer.println(blockedString);
                    if (user.getRestrictMessages()) {
                        writer.println("You are accepting messages from all users");
                    } else {
                        writer.println("You are accepting messages from friends only");
                    }
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
