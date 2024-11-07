import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.Component;

public class Client {
    //send and receive information to server

    private static final String SERVER_ADDRESS = "localhost"; // Change to server's IP if needed
    private static final int SERVER_PORT = 1234;

    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog((Component) null, "Welcome", "Search", 1);
    }

    public static String dropDown() {
        String arr[] = {"login", "create new user"};
        String selection = "";
        do {
            selection = (String) JOptionPane.showInputDialog(null, "Select title ", "Search Results",
                    JOptionPane.PLAIN_MESSAGE, null, arr, null);
        } while (selection == null);
        return selection;
    }

    public static String login() {
        String loginInfo;
        do {
            loginInfo = JOptionPane.showInputDialog((Component) null, "Please enter login information in the format username,password", "Search", 3);
            if (loginInfo == null || loginInfo.isEmpty()) {
                JOptionPane.showMessageDialog((Component) null, "Name cannot be empty!", "Search", 0);
            }
        } while (loginInfo == null || loginInfo.isEmpty());

        return loginInfo;
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server.");


            showWelcomeMessageDialog();
            String loginOrUser = dropDown();
            // System.out.print("You: ");
            writer.println(loginOrUser); //indicate to server whether client is loging in or creating user
            if (loginOrUser.equals("login")) {
                //login case
                String loginInfo = login(); //returns username,password
                writer.println(loginInfo); //send login information to server
                writer.flush();
                String serverMessage = reader.readLine();
                JOptionPane.showMessageDialog((Component) null, serverMessage, "Search", 0);


            } else {
                //create user case
                writer.println("User");
            }

            while (true) {
                
                String response = reader.readLine(); // Read response from the server
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
