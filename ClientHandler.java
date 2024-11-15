import java.io.*;
import java.net.*;

import Database.UserDatabase;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private UserDatabase userDatabase;  // This will store the reference to the UserDatabase instance

    // Constructor that accepts the socket and the UserDatabase instance
    public ClientHandler(Socket socket, UserDatabase userDatabase) {
        this.clientSocket = socket;
        this.userDatabase = userDatabase;  // Store the reference to the UserDatabase
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
                    String username = loginInfo[0];
                    String password = loginInfo[1];

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
