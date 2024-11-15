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
    private JFrame frame;

    public void run() {
        // Start client communication in a separate thread to avoid blocking the UI thread
        new Thread(new ClientRunnable()).start();
    }

    // All network communication is handled in the background thread
    // (ClientRunnable). This avoids blocking the Event Dispatch Thread (EDT),
    // which is responsible for the GUI.
    private class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                // Establish socket connection with the server
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected to the server.");

                // Create the login panel
                welcomePanel();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
        //welcome panel
        private void welcomePanel() {
            //create buttons
            JButton loginOption = new JButton("Login");
            JButton createUserOption = new JButton("Create new user");

            ActionListener welcomeListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    if (source == loginOption) {
                        showLoginPanel();
                    } else if (source == createUserOption) {
                        showNewUser();
                    }
                }
            };
            loginOption.addActionListener(welcomeListener);
            createUserOption.addActionListener(welcomeListener);

            frame = new JFrame("Messaging App");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.add(loginOption);
            panel.add(createUserOption);

            JPanel welcomeMessage = new JPanel();
            welcomeMessage.add(new JLabel("Welcome to the messaging app! Would you like to login or create new user?"));

            //add panels to frame
            frame.getContentPane().add(welcomeMessage, BorderLayout.NORTH); 
            frame.getContentPane().add(panel, BorderLayout.CENTER);         
            frame.setVisible(true);



        }
        // Creates the login frame
        private void showLoginPanel() {
            // Create the GUI components
            frame = new JFrame("Messaging App");
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
                        JOptionPane.showMessageDialog(frame, "Username or password cannot be empty.");
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
                        String loginStatus = reader.readLine();
                        System.out.println("Server response: " + loginStatus);

                        // Process the server's response
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                handleLoginResponse(loginStatus);
                            }
                        });

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        // Handle server's login response and transition to a new screen
        private void handleLoginResponse(String loginStatus) {
            // Remove the login panel
            frame.getContentPane().removeAll();

            JPanel loginResults = new JPanel();
            JLabel results = new JLabel();

            if (loginStatus.equals("Login successful")) {
                results.setText("Login successful");
                // Transition to the next screen (e.g., a home panel)
                showMainScreen();  // Show the main screen after successful login
            } else if (loginStatus.equals("Invalid password")) {
                results.setText("Invalid password");
            } else if (loginStatus.equals("User does not exist")) {
                results.setText("User does not exist");
            }

            // Add the results panel and refresh the frame
            loginResults.add(results);
            frame.getContentPane().add(loginResults, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        }
        //creates the new user frame
        private void showNewUser() {
            // Create the GUI components
            frame = new JFrame("Messaging App");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create panel components
            JTextField usernameTextField = new JTextField(10);
            JTextField passwordTextField = new JTextField(10);
            JButton createUserButton = new JButton("Create User");

            JPanel panel = new JPanel();
            panel.add(new JLabel("Username:"));
            panel.add(usernameTextField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordTextField);
            panel.add(createUserButton);

            createUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    username = usernameTextField.getText();
                    password = passwordTextField.getText();

                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Username or password cannot be empty.");
                        return;
                    }

                    String loginInfo = username + "," + password;

                    try {
                        // Send login command and login info to server
                        writer.println("createUser");
                        writer.flush();
                        System.out.println("Sent create new user command to server.");

                        writer.println(loginInfo);
                        writer.flush();
                        System.out.println("Sent new user info to server: " + loginInfo);

                        // Wait for and handle the server's response
                        String loginStatus = reader.readLine();
                        System.out.println("Server response: " + loginStatus);

                        // Process the server's response
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                handleNewUserResponse(loginStatus);
                            }
                        });

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Add panel to frame
            frame.getContentPane().add(panel, BorderLayout.NORTH);
            frame.setVisible(true);

            
        }

        private void handleNewUserResponse(String loginStatus) {
            if (loginStatus.equals("New User Created")) {
                JOptionPane.showMessageDialog(frame, "User successfully created", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Transition to the next screen (e.g., a home panel)
                // Remove the login panel
                frame.getContentPane().removeAll();
                showMainScreen();  // Show the main screen after successful login
            } else if (loginStatus.equals("User Already Exists")) {
                JOptionPane.showMessageDialog(frame, "User Already exists", "Error", JOptionPane.ERROR_MESSAGE);
            }
            frame.revalidate();
            frame.repaint();
        }

        // Show main screen or new panel after successful login
        private void showMainScreen() {
            // Remove the login result panel and show the main screen
            //uncomment this code during integration
            //frame.getContentPane().removeAll();

            //delete this during integration
            frame = new JFrame("Messaging App");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // Create a panel for the main screen
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new JLabel("Welcome to the Main Screen", SwingConstants.CENTER), BorderLayout.CENTER);

            // You can add more components to the main screen here, such as buttons, menus, etc.
            JButton logoutButton = new JButton("Logout");
            mainPanel.add(logoutButton, BorderLayout.SOUTH);

            // Define logout button action
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle logout - reset and go back to the login screen. uncomment this during integration
                    // frame.getContentPane().removeAll();
                    // createLoginPanel();  // Show the login panel again
                    // frame.revalidate();
                    // frame.repaint();
                }
            });

            // Update the frame to show the main screen
            //uncomment these during integration
            //frame.getContentPane().add(mainPanel, BorderLayout.CENTER); 
            //frame.revalidate();
            //frame.repaint();

            frame.add(mainPanel);
            frame.setVisible(true);
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
