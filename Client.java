import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Team Project -- Client Side of the program that takes in all User inputs
 * 
 * Takes in all of the user's inputs from GUI.
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 17, 2024
 */

public class Client implements ClientInterface {
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
        // Start client communication in a separate thread to avoid blocking the UI
        // thread
        new Thread(new ClientRunnable()).start();
    }

    // All network communication is handled in the background thread
    // (ClientRunnable). This avoids blocking the Event Dispatch Thread (EDT),
    // which is responsible for the GUI.
    /**
     * Team Project -- Client Side of the program that takes in all User inputs
     * 
     * Takes in all of the user's inputs from GUI.
     * 
     * @author Team 2, Lab 19
     * 
     * @version Nov. 17, 2024
     */
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

        // welcome panel
        private void welcomePanel() {
            // create buttons
            JButton loginOption = new JButton("Login");
            JButton createUserOption = new JButton("Create new user");

            ActionListener welcomeListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Object source = e.getSource();
                    frame.getContentPane().removeAll(); // Remove all existing components from the frame
                    frame.getContentPane().invalidate(); // Invalidate the layout to clear
                    if (source == loginOption) {
                        frame.getContentPane().removeAll();
                        showLoginPanel();
                        frame.getContentPane().revalidate(); // Revalidate the layout
                        frame.getContentPane().repaint(); // Repaint to reflect changes
                    } else if (source == createUserOption) {
                        frame.getContentPane().removeAll();
                        showNewUser();
                        frame.getContentPane().revalidate(); // Revalidate the layout
                        frame.getContentPane().repaint(); // Repaint to reflect changes
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

            // add panels to frame
            frame.getContentPane().add(welcomeMessage, BorderLayout.NORTH);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);

        }

        // Creates the login frame
        private void showLoginPanel() {
            // Create the GUI components
            // frame = new JFrame("Messaging App");
            // frame.setSize(600, 400);
            // frame.setLocationRelativeTo(null);
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.getContentPane().removeAll();
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
            if (loginStatus.equals("Login successful")) {
                JOptionPane.showMessageDialog(frame, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Transition to the next screen (e.g., a home panel)
                frame.getContentPane().removeAll();
                showMainScreen(); // Show the main screen after successful login
            } else if (loginStatus.equals("Invalid password")) {
                JOptionPane.showMessageDialog(frame, "Invalid Password, please try again", "Error",
                        JOptionPane.ERROR_MESSAGE);

            } else if (loginStatus.equals("User does not exist")) {
                JOptionPane.showMessageDialog(frame, "User does not exist, please try again", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // refresh the frame
            frame.revalidate();
            frame.repaint();
        }

        // creates the new user frame
        private void showNewUser() {
            // Create the GUI components
            // frame = new JFrame("Messaging App");
            // frame.setSize(600, 400);
            // frame.setLocationRelativeTo(null);
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().removeAll();
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
                writer.println("restrict messages");
                writer.flush();
                int result = JOptionPane.showConfirmDialog(frame,

                        "Would you like to receive messages from all users?"
                                + " If not, you will only receive messages from friends.",
                        "Swing Tester",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    try {
                        writer.println("yes");
                        writer.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (result == JOptionPane.NO_OPTION) {
                    try {
                        writer.println("no");
                        writer.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(frame, "User successfully created", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                // Transition to the next screen (e.g., a home panel)
                // Remove the login panel
                frame.getContentPane().removeAll();
                showMainScreen(); // Show the main screen after successful login
            } else if (loginStatus.equals("User Already Exists")) {
                JOptionPane.showMessageDialog(frame, "Username is taken, please choose another one.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            frame.revalidate();
            frame.repaint();
        }

        // Show main screen or new panel after successful login
        private void showMainScreen() {
            // Remove the login result panel and show the main screen
            frame.getContentPane().removeAll();

            // Create a panel for the main screen
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel panel = new JPanel();
            JTextField searchField = new JTextField(10);
            panel.add(searchField);

            JButton search = new JButton("Search");
            panel.add(search);

            mainPanel.add(panel, BorderLayout.NORTH);

            String friendString = "";
            String blockedString = "";
            String restrictedString = "";
            try {
                writer.println("Get friends and blocked");
                writer.println(username);
                friendString = reader.readLine();
                blockedString = reader.readLine();
                restrictedString = reader.readLine();

            } catch (Exception e) {
                e.printStackTrace();
            }

            JLabel friends = new JLabel("Friend Users: " + friendString.substring(0, friendString.length() - 2));
            JLabel blocked = new JLabel("Blocked Users: " + blockedString.substring(0, blockedString.length() - 2));
            JLabel restricted = new JLabel(restrictedString);

            JPanel userInfo = new JPanel();
            userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
            userInfo.add(friends);
            userInfo.add(blocked);
            userInfo.add(restricted);
            // You can add more components to the main screen here, such as buttons, menus,
            // etc.
            JButton logoutButton = new JButton("Logout");
            mainPanel.add(logoutButton, BorderLayout.SOUTH);
            mainPanel.add(userInfo, BorderLayout.CENTER);

            // Define logout button action
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle logout - reset and go back to the login screen. uncomment this during
                    // integration
                    frame.getContentPane().removeAll();
                    welcomePanel(); // Show the login panel again
                    frame.revalidate();
                    frame.repaint();
                }
            });

            // Update the frame to show the main screen
            // uncomment these during integration
            // frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            // frame.revalidate();
            // frame.repaint();

            search.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String searchText = searchField.getText();
                    if (searchText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a username.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            writer.println("searchUser");
                            writer.flush();
                            System.out.println("Sent search command to the server.");

                            // Send the username to search
                            writer.println(searchText);
                            writer.flush();
                            System.out.println("Searching for user: " + searchText);

                            // Read server's response
                            String response = reader.readLine();
                            System.out.println("Server response: " + response);

                            SwingUtilities.invokeLater(() -> {
                                if (response.equals("User found")) {
                                    String[] options = { "Send or Delete Message",

                                            "Block User", "Add Friend",
                                            "Cancel" };

                                    int choice = JOptionPane.showOptionDialog(frame, "User " +
                                            searchText + " found! What would you like to do?",
                                            "User Options", JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                                    if (choice == 0) { // send Message
                                        System.out.println("Sending/ Deleting message");
                                        // CHECK TO SEE IF YOU CAN SEND MESSAGES.
                                        try {
                                            writer.println("Check restrict messages");
                                            writer.println(username);
                                            writer.println(searchText);
                                            String canSend = reader.readLine();
                                            if (canSend.equals("true")) {
                                                sendMessagetoUser(searchText);
                                            } else if (canSend.equals("false")) {
                                                JOptionPane.showMessageDialog(frame,
                                                        "You cannot send messages to this user. Either user may be blocked or have messages restricted",
                                                        "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                                showMainScreen();
                                            }
                                        } catch (Exception f) {
                                            f.printStackTrace();
                                        }
                                    } else if (choice == 1) { // BLOCK USER
                                        showBlockedUsers(searchText);

                                    } else if (choice == 2) { // ADD FRIEND
                                        showFriendUsers(searchText);
                                    } else { // CANCEL
                                        frame.getContentPane().removeAll();
                                        showMainScreen();

                                    }

                                } else if (response.equals("User not found")) {
                                    JOptionPane.showMessageDialog(frame, "User " + searchText + " does not exist.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "User not found.", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            });
                        } catch (IOException f) {
                            f.printStackTrace();
                            JOptionPane.showMessageDialog(frame,
                                    "An error occurred while communicating with the server.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            frame.add(mainPanel);
            frame.setVisible(true);
        }

        private void showBlockedUsers(String blocked) {
            // removes all current content
            frame.getContentPane().removeAll();

            frame = new JFrame("Messaging App - Blocking");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // implementation for block user
            writer.println("blockUser");
            writer.flush();
            writer.println(username); // sends current user
            writer.flush();
            writer.println(blocked);
            writer.flush();

            try {
                // Clear the mainPanel

                JPanel panelNew = new JPanel();
                panelNew.setLayout(new BoxLayout(panelNew, BoxLayout.Y_AXIS)); // Set a vertical layout for labels

                String users = reader.readLine();
                users.trim();
                String[] usersB = users.split(" ");
                for (String user : usersB) {
                    panelNew.add(new JLabel(user));
                }
                JButton backButton = new JButton("Back");
                backButton.addActionListener(h -> showMainScreen());
                panelNew.add(backButton);

                frame.add(panelNew);
                frame.setVisible(true);

                frame.revalidate();
                frame.repaint();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        public void showFriendUsers(String friend) {
            // removes all current content
            frame.getContentPane().removeAll();

            frame = new JFrame("Messaging App - Blocking");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // implementation for block user
            writer.println("friendUser");
            writer.flush();
            writer.println(username); // sends current user
            writer.flush();
            writer.println(friend);
            writer.flush();

            try {
                // Clear the mainPanel

                JPanel panelNew = new JPanel();
                panelNew.setLayout(new BoxLayout(panelNew, BoxLayout.Y_AXIS)); // Set a vertical layout for labels

                String users = reader.readLine();
                users.trim();
                String[] usersB = users.split(" ");
                for (String user : usersB) {
                    panelNew.add(new JLabel(user));
                }
                JButton backButton = new JButton("Back");
                backButton.addActionListener(h -> showMainScreen());
                panelNew.add(backButton);

                frame.add(panelNew);
                frame.setVisible(true);

                frame.revalidate();
                frame.repaint();

            } catch (IOException e1) {

                e1.printStackTrace();
            }

        }

        private void sendMessagetoUser(String receiver) {
            // create a new panel for sending a message
            // IMPLEMENT FRIEND USER

            // removes all current content
            frame.getContentPane().removeAll();

            frame = new JFrame("Messaging App - Sending or Deleting Message");
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BorderLayout());

            String messageLog = "";
            // retrive past messages
            try {
                System.out.println("attempting to retrieve past messages");
                writer.println("receive message");
                writer.flush();
                writer.println(receiver);
                writer.flush();
                System.out.println("sent receiver to clientHandler");
                messageLog = reader.readLine();
                System.out.println("received message from server");
            } catch (Exception e) {
                System.out.println("an error occured while trying to retrieve messages");
                e.printStackTrace();
            }

            // panel for SEND and DELETE button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            JTextField messageField = new JTextField(10);
            buttonPanel.add(messageField);

            JButton sendMessageButton = new JButton("Send Message");
            buttonPanel.add(sendMessageButton);

            JButton deleteMessageButton = new JButton("Delete Message");
            buttonPanel.add(deleteMessageButton);

            JButton backButton = new JButton("Back");
            buttonPanel.add(backButton);

            // add button panel to the message panel
            messagePanel.add(buttonPanel, BorderLayout.SOUTH);

            // add past messages to the message panel
            messagePanel.add(new JLabel(messageLog), BorderLayout.CENTER);

            sendMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageField.getText();
                    if (message.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a message.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        writer.println("sendMessage");
                        writer.flush();
                        writer.println(receiver);
                        writer.flush();
                        writer.println(username);
                        writer.flush();
                        writer.println(message);
                        writer.flush();
                        JOptionPane.showMessageDialog(frame,
                                "Message sent successfully to " + receiver + "!");
                        sendMessagetoUser(receiver);
                    }
                }
            });

            deleteMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageField.getText();
                    if (message.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a message.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        writer.println("deleteMessage");
                        writer.flush();
                        writer.println(receiver);
                        writer.flush();
                        writer.println(username);
                        writer.flush();
                        writer.println(message);
                        writer.flush();
                        JOptionPane.showMessageDialog(frame,
                                "Message deleted successfully to " + receiver + ".");
                        sendMessagetoUser(receiver);
                    }
                }
            });

            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().removeAll();
                    showMainScreen();
                }
            });

            // Add the new message panel to the main screen
            frame.add(messagePanel);

            // Refresh the frame
            frame.setVisible(true);
            frame.revalidate();
            frame.repaint();
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
