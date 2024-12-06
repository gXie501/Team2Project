import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
    private String password = "";
    private JFrame frame = new JFrame("Messaging App");

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
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

            loginOption.setFont(new Font("Arial", Font.BOLD, 16));
            createUserOption.setFont(new Font("Arial", Font.BOLD, 16));

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

            JPanel panel = new JPanel();
            panel.add(loginOption);
            panel.add(createUserOption);

            // Create a welcome message panel
            JLabel welcomeLabel = new JLabel("Welcome to the Messaging App!");
            welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
            welcomeLabel.setForeground(new Color(70, 130, 180)); // Steel blue
            welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel welcomeMessage = new JPanel();
            welcomeMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            welcomeMessage.setLayout(new BorderLayout());
            welcomeMessage.add(welcomeLabel, BorderLayout.CENTER);

            // Create a button panel with better spacing
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonPanel.add(loginOption);
            buttonPanel.add(createUserOption);

            // Add panels to the frame
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(welcomeMessage, BorderLayout.NORTH);
            frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
            frame.setSize(500, 300); // Set the frame size
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);

        }

        // Creates the login frame
        private void showLoginPanel() {

            frame.getContentPane().removeAll();

            // Create the main panel with a background color
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(240, 248, 255)); // Alice blue

            // Create a title label
            JLabel titleLabel = new JLabel("Login to Your Account");
            titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
            titleLabel.setForeground(new Color(30, 144, 255)); // Dodger blue
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Create a form panel for inputs
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

            // Add username label and text field
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            formPanel.add(usernameLabel, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            JTextField usernameTextField = new JTextField(15);
            formPanel.add(usernameTextField, gbc);

            // Add password label and text field
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            JLabel passwordLabel = new JLabel("Password:");
            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            formPanel.add(passwordLabel, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            JPasswordField passwordTextField = new JPasswordField(15);
            formPanel.add(passwordTextField, gbc);

            // Add login button
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            JButton loginButton = new JButton("Login");
            loginButton.setFont(new Font("Arial", Font.BOLD, 16));
            formPanel.add(loginButton, gbc);

            // Add components to main panel
            mainPanel.add(titleLabel, BorderLayout.NORTH);
            mainPanel.add(formPanel, BorderLayout.CENTER);

            // Add main panel to frame
            frame.getContentPane().add(mainPanel);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
            // Define button action
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    username = usernameTextField.getText();
                    char[] charPassword = passwordTextField.getPassword();
                    password = "";
                    for (char c : charPassword) {
                        System.out.println("Current pw: " + password);
                        System.out.println("Char: " + c);
                        password += c;
                    }

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
            // Create panel components
            JTextField usernameTextField = new JTextField(15);
            JPasswordField passwordTextField = new JPasswordField(15); // Use JPasswordField for password input
            JButton createUserButton = new JButton("Create User");

            // Set button appearance
            createUserButton.setFont(new Font("Arial", Font.BOLD, 14));

            // Create panel and layout
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Add username label and text field
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Username:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(usernameTextField, gbc);

            // Add password label and text field
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Password:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(passwordTextField, gbc);

            // Add create user button
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(createUserButton, gbc);

            // Add tooltip
            createUserButton.setToolTipText("Click to create a new user account");

            // Add action listener

            createUserButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    username = usernameTextField.getText();
                    char[] charPassword = passwordTextField.getPassword();
                    password = "";
                    for (char c : charPassword) {
                        System.out.println("Current pw: " + password);
                        System.out.println("Char: " + c);
                        password += c;
                    }
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
            // Remove all content from the frame
            frame.getContentPane().removeAll();

            // Create the main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // North Panel: Search Bar
            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
            searchPanel.setBorder(BorderFactory.createTitledBorder("Search for Users"));

            JTextField searchField = new JTextField(20);
            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Arial", Font.BOLD, 14));
            searchButton.setToolTipText("Search for a user by their username");

            searchPanel.add(searchField);
            searchPanel.add(Box.createHorizontalStrut(10));
            searchPanel.add(searchButton);
            mainPanel.add(searchPanel, BorderLayout.NORTH);

            // Center Panel: User Info
            JPanel userInfoPanel = new JPanel();
            userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
            userInfoPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

            JLabel friendsLabel;
            JLabel blockedLabel;
            JLabel restrictedLabel;
            String friendString = "", blockedString = "", restrictedString = "";

            try {
                writer.println("Get friends and blocked");
                writer.println(username);
                writer.flush();

                friendString = reader.readLine();
                blockedString = reader.readLine();
                restrictedString = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            friendsLabel = new JLabel(friendString.isEmpty()
                    ? "You do not have any friends"
                    : "Friend Users: " + friendString.substring(0, friendString.length() - 2));
            blockedLabel = new JLabel(blockedString.isEmpty()
                    ? "You are not blocking any users"
                    : "Blocked Users: " + blockedString.substring(0, blockedString.length() - 2));
            restrictedLabel = new JLabel(restrictedString);

            friendsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            blockedLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            restrictedLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            userInfoPanel.add(friendsLabel);
            userInfoPanel.add(Box.createVerticalStrut(10));
            userInfoPanel.add(blockedLabel);
            userInfoPanel.add(Box.createVerticalStrut(10));
            userInfoPanel.add(restrictedLabel);
            mainPanel.add(userInfoPanel, BorderLayout.CENTER);

            // South Panel: Logout Button
            JButton logoutButton = new JButton("Logout");
            logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
            logoutButton.setToolTipText("Logout and return to the welcome screen");

            logoutButton.addActionListener(e -> {
                frame.getContentPane().removeAll();
                welcomePanel();
                frame.revalidate();
                frame.repaint();
            });

            mainPanel.add(logoutButton, BorderLayout.SOUTH);

            // Add search button action listener
            searchButton.addActionListener(e -> {
                String searchText = searchField.getText();
                if (searchText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a username.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        writer.println("searchUser");
                        writer.flush();

                        writer.println(searchText);
                        writer.flush();

                        String response = reader.readLine();

                        SwingUtilities.invokeLater(() -> handleSearchResponse(searchText, response));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "An error occurred while communicating with the server.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Add the main panel to the frame
            frame.add(mainPanel);
            frame.pack(); // Adjust the frame size to fit content
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        }

        // Separate method for search response handling
        private void handleSearchResponse(String searchText, String response) {
            if ("User found".equals(response)) {
                String[] options = { "Send/Delete Message", "Block User", "Add Friend", "Cancel" };
                int choice = JOptionPane.showOptionDialog(frame,
                        "User " + searchText + " found! What would you like to do?",
                        "User Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                switch (choice) {
                    case 0 -> sendMessagetoUser(searchText);
                    case 1 -> showBlockedUsers(searchText);
                    case 2 -> showFriendUsers(searchText);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "User " + searchText + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void showBlockedUsers(String blocked) {
            // removes all current content
            frame.getContentPane().removeAll();

            // implementation for block user
            writer.println("blockUser");
            writer.flush();
            writer.println(username); // sends current user
            writer.flush();
            writer.println(blocked);
            writer.flush();

            String didBlock = "";
            try {
            didBlock = reader.readLine(); //whether or not the user was able to be added as a friend
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (didBlock.contains("successfully")) {
                JOptionPane.showMessageDialog(frame,
                        didBlock, "Block User", JOptionPane.INFORMATION_MESSAGE);
                showMainScreen();
            } else {
                JOptionPane.showMessageDialog(frame,
                        didBlock, "Block User", JOptionPane.ERROR_MESSAGE);
                showMainScreen();
            }
        }

        public void showFriendUsers(String friend) {
            // removes all current content
            frame.getContentPane().removeAll();

            // implementation for block user
            writer.println("friendUser");
            writer.flush();
            writer.println(username); // sends current user
            writer.flush();
            writer.println(friend);
            writer.flush();

            String didFriend = "";
            try {
            didFriend = reader.readLine(); //whether or not the user was able to be added as a friend
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (didFriend.contains("added")) {
                JOptionPane.showMessageDialog(frame,
                        didFriend, "Friend User", JOptionPane.INFORMATION_MESSAGE);
                showMainScreen();
            } else {
                JOptionPane.showMessageDialog(frame,
                        didFriend, "Friend User", JOptionPane.ERROR_MESSAGE);
                showMainScreen();
            }

        }

        private void sendMessagetoUser(String receiver) {
            try {
                writer.println("Check to send message");
                writer.println(username);
                writer.println(receiver);
                String canMessage = reader.readLine();
                if (canMessage.contains("blocked") || canMessage.contains("not")) {
                    JOptionPane.showMessageDialog(frame, canMessage, "Message", JOptionPane.ERROR_MESSAGE);
                    showMainScreen();
                    return;
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        
            frame.getContentPane().removeAll();
            frame.setTitle("Messaging App - Chat with " + receiver);
            frame.setSize(600, 400);
        
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BorderLayout());
        
            // Panel for displaying messages
            JPanel messagesDisplayPanel = new JPanel();
            messagesDisplayPanel.setLayout(new BoxLayout(messagesDisplayPanel, BoxLayout.Y_AXIS));
        
            ArrayList<String[]> messageLog = new ArrayList<>();
            try {
                writer.println("receive message");
                writer.flush();
                writer.println(receiver);
                writer.flush();
        
                String response;
                while (!(response = reader.readLine()).equals("END")) { // Read until "END"
                    String[] messageParts = response.split(";");
                    if (messageParts.length == 3) {
                        messageLog.add(messageParts); // [sender, receiver, message]
                    }
                }
            } catch (Exception e) {
                System.out.println("Error retrieving messages");
                e.printStackTrace();
            }
        
            // Add messages to the display panel
            for (String[] message : messageLog) {
                String sender = message[0];
                String text = message[2];
        
                JLabel messageLabel = new JLabel(text);
                messageLabel.setOpaque(true);
                messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                messageLabel.setBackground(sender.equals(username) ? new Color(173, 216, 230) : new Color(240, 240, 240));
                messageLabel.setForeground(Color.BLACK);
        
                JPanel messageContainer = new JPanel();
                messageContainer.setLayout(new BorderLayout());
                messageContainer.add(messageLabel, sender.equals(username) ? BorderLayout.EAST : BorderLayout.WEST);
                messageContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
                // Add spacing between messages
                messageContainer.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                messagesDisplayPanel.add(messageContainer);
            }
        
            JScrollPane scrollPane = new JScrollPane(messagesDisplayPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
            messagePanel.add(scrollPane, BorderLayout.CENTER);
        
            // Panel for sending messages
            JPanel inputPanel = new JPanel(new BorderLayout());
            JTextField messageField = new JTextField();
            JButton sendMessageButton = new JButton("Send");
            JButton deleteMessageButton = new JButton("Delete");
            JPanel sendDeleteButton = new JPanel();
            sendDeleteButton.add(sendMessageButton);
            sendDeleteButton.add(deleteMessageButton);
        
            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendDeleteButton, BorderLayout.EAST);
        
            messagePanel.add(inputPanel, BorderLayout.SOUTH);
        
            sendMessageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageField.getText();
                    if (message.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a message.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            writer.println("sendMessage");
                            writer.flush();
                            writer.println(receiver);
                            writer.flush();
                            writer.println(username);
                            writer.flush();
                            writer.println(message);
                            writer.flush();
                            JOptionPane.showMessageDialog(frame, "Message sent successfully to " + receiver + "!");
                            sendMessagetoUser(receiver);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        
            // Back button
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().removeAll();
                    showMainScreen();
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
        
            messagePanel.add(backButton, BorderLayout.NORTH);
        
            frame.add(messagePanel);
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
