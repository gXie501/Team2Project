import java.io.*;
import java.net.*;


class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
             
            //TODO: welcome message
            out.println("Welcome to the server! Type 'bye' to exit.");
                
            //start infinite while loop
                //have user choose between login and create user
                    //login cases
                    //create user cases
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from client: " + message);
                out.println("Server received: " + message); // Echo message back to client

                if (message.equalsIgnoreCase("bye")) {
                    out.println("Goodbye!");
                    break;
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