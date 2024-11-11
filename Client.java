import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private JButton loginButton;
    private String username;
    private String password;

    public void run() {
        // Start client communication in a separate thread to avoid blocking the UI thread
        new Thread(new ClientRunnable()).start();
    }

    //All network communication is handled in the background thread (ClientRunnable). 
    //This avoids blocking the Event Dispatch Thread (EDT), which is responsible for the GUI.
    private class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                // Establish socket connection with the server
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected to the server.");

                // Create the GUI components
                JFrame frame = new JFrame("Messaging App");
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Create panel components
                JTextField usernameTextField = new JTextField(10);
                JTextField passwordTextField = new JTextField(10);
                loginButton = new JButton("Login");

                JPanel panel = new JPanel();
                panel.add(new JLabel("Username:"));
                panel.add(usernameTextField);
                panel.add(new JLabel("Password:"));
                panel.add(passwordTextField);
                panel.add(loginButton);

                // Add panel to frame
                frame.getContentPane().add(panel, BorderLayout.NORTH);
                frame.setVisible(true);

                // Define button action
                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        username = usernameTextField.getText();
                        password = passwordTextField.getText();

                        if (username.isEmpty() || password.isEmpty()) {
                            System.out.println("Username or password cannot be empty.");
                            return;
                        }

                        String loginInfo = username + "," + password;

                        try {
                            // Send login command and login info to server
                            writer.println("login");
                            writer.flush();
                            System.out.println("Sent login command to server.");

                            writer.println(loginInfo);
                            writer.flush();
                            System.out.println("Sent login info to server: " + loginInfo);

                            // Wait for and handle the server's response
                            String serverMessage = reader.readLine();
                            System.out.println("Server response: " + serverMessage);
                            
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client().run();
            }
        });
    }
}
