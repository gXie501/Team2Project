import java.io.*;
import java.net.*;

import Database.MessageDatabase;
import Database.UserDatabase;

/**
 * Team Project -- Server side of the program that creates a thread to run the processing.
 * 
 * Opens a serverSocket, and creates a new Thread for every user connecting to the server and each Thread
 * will run the run method of the ClientHandler which processes Client input and interaction between Sockets.
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 17, 2024
 */

public class Server implements ServerInterface {
    public static void main(String[] args) {
        try {
            // Initialize UserDatabase instance
            UserDatabase userDatabase = new UserDatabase(); // Ensure it's not null
            MessageDatabase messageDatabase = new MessageDatabase();

            // Create the server socket and start listening for connections
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is running...");

            // Accept client connections in an infinite loop
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Pass the UserDatabase to the ClientHandler
                ClientHandler clientHandler = new ClientHandler(clientSocket, userDatabase, messageDatabase);

                // Handle each client connection in a new thread
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
