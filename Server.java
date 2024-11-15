import java.io.*;
import java.net.*;

import Database.UserDatabase;

public class Server implements ServerInterface {
    public static void main(String[] args) {
        try {
            // Initialize UserDatabase instance
            UserDatabase userDatabase = new UserDatabase(); // Ensure it's not null

            // Create the server socket and start listening for connections
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is running...");

            // Accept client connections in an infinite loop
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Pass the UserDatabase to the ClientHandler
                ClientHandler clientHandler = new ClientHandler(clientSocket, userDatabase);

                // Handle each client connection in a new thread
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
