import java.io.*;
import java.net.*;

public class Client {
    //send and receive information to server

    private static final String SERVER_ADDRESS = "localhost"; // Change to server's IP if needed
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server.");

            // Read welcome message from server
            System.out.println("Server: " + in.readLine());

            String userInput;
            while (true) {
                System.out.print("You: ");
                userInput = consoleInput.readLine(); // Read input from console

                out.println(userInput); // Send message to the server

                String response = in.readLine(); // Read response from the server
                System.out.println("Server: " + response);

                if (userInput.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            System.out.println("Disconnected from the server.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
