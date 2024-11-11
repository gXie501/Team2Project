import java.io.*;
import java.net.*;


//server side, processing
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
             

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);

                if (message.equals("login")) {
                    String loginInfo = reader.readLine();
                    System.out.println("Received login info from client: " + loginInfo);
                    writer.println("Login information: " + loginInfo);
                    System.out.println(loginInfo);
                    writer.flush();
                } else if (message.equals("exit")) {
                    // Break out of the loop and close the connection
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