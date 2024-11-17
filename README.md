# Messaging App

## Description

The goal is to build a fully functioning messaging social media app. With this app, users will be able to create a profile, have password protected login, search for other users, view other users, add and block friends, and upload/displa profile pictures.

## Getting Started
### Executing program

* delete testFile.txt (if exists). 
* compile all classes and interfaces
* run the RunLocalTest.java file
```
java RunLocalTest.java
```
## Authors

- Anika Thapar: User Database
- Gengjie Xie: Test cases
- Neha Pudota: Message Database
- Mithun Mahesh: Image implementation and debugging


## Version History

* 0.1
    * Database Structure
* 0.2
   * Server and Client implementation

## Classes & interfaces, outline
  # MessageDatabase

  The `MessageDatabase` class provides a simple implementation for handling user messages in a messaging application. It allows sending, deleting, and retrieving messages between users, with messages stored in a specified file. This class uses the `MessageInterface` interface, which should be defined separately. All methods are threadsafe using the gatekeeper object. 

  ## Features

  1. **Send Message** - Allows sending a message between two users, storing it in a specified file.
  2. **Delete Message** - Deletes a specified message between two users.
  3. **Retrieve Messages** - Retrieves all messages exchanged between two users.

  ## Class Details

  ### Constructor

  There is no explicit constructor in this class. All methods are static, meaning they can be directly called without instantiating the class.

  ### Methods

  #### `sendMessage(User sender, User receiver, String content, String messageFile)`

  - **Description**: Stores a message in the specified file with the format `sender;receiver;content`.
  - **Parameters**:
    - `sender`: The user who is sending the message.
    - `receiver`: The user who is receiving the message.
    - `content`: The content of the message. For image messages, the content is the location or filename of the image.
    - `messageFile`: The file where the messages are stored.

  #### `deleteMessage(User sender, User receiver, String content, String messageFile)`

  - **Description**: Deletes a specified message between two users by copying all messages except the specified one to a temporary file and renaming it.
  - **Parameters**:
    - `sender`: The user who sent the message.
    - `receiver`: The user who received the message.
    - `content`: The content of the message to be deleted.
    - `messageFile`: The file where the messages are stored.

  #### `retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile)`

  - **Description**: Retrieves all messages exchanged between two users.
  - **Parameters**:
    - `userOneUsername`: The username of the first user.
    - `userTwoUsername`: The username of the second user.
    - `messageFile`: The file where the messages are stored.
  - **Returns**: An `ArrayList<String>` containing all messages exchanged between the two users.

   ## Test Cases for MessageDatabase Class
   ### MessageDatabase Class
   - The MessageDatabase was checked that it implements 1 interface, and that it implements the MessageInterface by checking MessageDatabase is an instanceof MessageInterface.
   ### Constructor
   - Since there is no explicit constructor for this class, it was checked that there is an default constructor for the class.
   ### Methods
   - Send Message: Send message was checked by first calling the method with 2 users with the contents to a file. Then the file was read to make sure that the message was written correctly with user1;user2;content. If the text was found in the file it was correct else it failed the test.
   - Delete Message: Delete message was checked by calling the method with its parameters, then the file was read to make sure that the message of user1;user2;content no longer exist in the file, else the test case failed.
   - retrieveMessage: Retrieve Message was checked by sending a lot of messages and then saving the content of the messages to an ArrayList<String>. Then the method is called which returns an ArrayList<String>. The 2 ArrayList was compared to see whether they are equal to each other. If not the test case failed.

   # User Class

  The `User` class represents a user in a social media application. Each user has attributes such as a username, profile picture, password, and lists of friends and blocked users. This class includes methods to get and set user details and manage account preferences, including message restrictions.

  ## Features

  1. **Manage User Information** - Set and retrieve basic user details, including username, password, profile picture, and message restriction settings.
  2. **Friend and Block Management** - Maintain lists of friends and blocked users.
  3. **Equality Check** - Verify if two user accounts match based on username and password.

  ## Class Details

  ### Constructor

  #### `User(String username, String password, String pfp, Boolean restrictMessages, ArrayList<User> friends, ArrayList<User> blocked)`

  - **Description**: Initializes a `User` object with the provided username, password, profile picture, message restrictions, friends list, and blocked list.
  - **Parameters**:
    - `username`: The username of the user's account.
    - `password`: The password of the user's account.
    - `pfp`: The profile picture of the user's account.
    - `restrictMessages`: A boolean indicating whether to restrict messages from non-friends.
    - `friends`: An `ArrayList<User>` representing the user's friends.
    - `blocked`: An `ArrayList<User>` representing the users blocked by this account.

  ### Methods

  #### Setters

  - `setUsername(String username)`: Sets the username.
  - `setPassword(String password)`: Sets the password.
  - `setPfp(String pfp)`: Sets the profile picture.
  - `setRestrictMessages(Boolean restrictMessages)`: Sets the message restriction preference.
  - `setFriends(ArrayList<User> friends)`: Sets the user's friends list.
  - `setBlocked(ArrayList<User> blocked)`: Sets the user's blocked list.

  #### Getters

  - `getUsername()`: Returns the username.
  - `getPassword()`: Returns the password.
  - `getPfp()`: Returns the profile picture.
  - `getRestrictMessages()`: Returns the message restriction setting.
  - `getFriends()`: Returns the friends list.
  - `getBlocked()`: Returns the blocked list.

  #### `boolean equals(User user)`

  - **Description**: Checks if two `User` objects are equal based on their `username` and `password`.
  - **Parameters**:
    - `user`: Another `User` object to compare with.
  - **Returns**: `true` if the username and password match, otherwise `false`.
## Test Cases for User Class
### User Class
- The User class was checked by making sure that it implements the UserObject interface by checking that User is an instanceof UserObjectInterface.
### Constructor
- The User class constructor was checked with getConstructor method with all of its parameter type. If a NoSuchMethodException was thrown, it was caught and the test failed meaning that the constructor with the parameters did not exist.
### Methods
- Getters: All of the getters was tested by creating a User object with parameters, then assertEquals method was used to campare that the getters was returning the correct information.
- Setters: All of the setters was tested by first calling all the setters, then using the assertEquals method to make sure that the fields are changed correctly.
- equals: The equals method was tested by using assertTrue and calling User.equals(User), where both the user were equal. Then an assertFalse was used with 2 users that are different. If the wrong boolean was return, the test case failed.

# UserDatabase

The `UserDatabase` class is a component of a social media application that manages all `User` objects. It supports creating new users, managing user login, and updating the friends and blocked lists for each user. User data is stored in a file (`userFile.txt`) for persistence. All methods are threadsafe using the gatekeeper object. 

## Features

1. **Create User** - Adds new users to the application and stores them in a file.
2. **Login** - Authenticates users by verifying credentials.
3. **Block User** - Adds a user to another user's blocked list.
4. **Friend User** - Adds a user to another user's friends list.
5. **Retrieve User** - Finds a user by username from the in-memory list of users.

## Class Details

### Attributes

- `users`: An `ArrayList<User>` containing all user objects in the application.

### Methods

#### `ArrayList<User> getUsers()`

- **Description**: Returns the list of all users.
- **Returns**: An `ArrayList<User>` containing all `User` objects.

#### `void createUser(String username, String password, String pfp, boolean restrictMessage)`

- **Description**: Creates a new user and adds them to the `users` list and `userFile.txt` for persistent storage.
- **Parameters**:
  - `username`: The username of the new user.
  - `password`: The password for the new user.
  - `pfp`: Profile picture identifier for the user.
  - `restrictMessage`: Boolean to restrict messages from non-friends.

#### `boolean login(String username, String password)`

- **Description**: Validates user login by checking the credentials in `userFile.txt`.
- **Parameters**:
  - `username`: The username for the login attempt.
  - `password`: The password for the login attempt.
- **Returns**: `true` if login is successful; `false` otherwise.

#### `boolean blockUser(User user, User blockUser)`

- **Description**: Adds `blockUser` to the blocked list of `user`.
- **Parameters**:
  - `user`: The user who wants to block another user.
  - `blockUser`: The user to be blocked.
- **Returns**: `true` if the user was successfully blocked; `false` otherwise.

#### `boolean friendUser(User user, User friendUser)`

- **Description**: Adds `friendUser` to the friends list of `user`.
- **Parameters**:
  - `user`: The user who wants to add a friend.
  - `friendUser`: The user to be added as a friend.
- **Returns**: `true` if the user was successfully added as a friend; `false` otherwise.

#### `User returnUser(String username)`

- **Description**: Finds and returns a user by their username.
- **Parameters**:
  - `username`: The username of the user to find.
- **Returns**: A `User` object if found; `null` otherwise.

# Server Class 

The `Server` class is the entry point for a simple server application that handles client connections and manages user and message data. It listens for incoming client connections on a specified port and delegates the handling of each client to a separate thread using a `ClientHandler`.

---

## Features
- **Multi-threaded Client Handling:** Each client connection is handled in its own thread for scalability.
- **User Management:** Utilizes a `UserDatabase` class to manage user-related operations.
- **Message Management:** Uses a `MessageDatabase` class to store and manage messages sent by clients.

---

## Prerequisites
- **Java Development Kit (JDK):** Ensure JDK 8 or later is installed.
- **Dependencies:** 
  - `Database.UserDatabase` and `Database.MessageDatabase` must be implemented and included in the project.
  - `ClientHandler` class must be defined and capable of handling a socket connection.

---

## Usage Instructions

1. **Compilation:**
   Compile the project files using the following command:
   ```bash
   javac -d . Server.java


## Test Case for UserDatabase Class
### UserDatabase Class
- The UserDatabase was checked that it implements 1 interface, and that it implements the UserInterface by checking UserDatabase is an instanceof UserInterface.
### Constructor
- Since there is no explicit constructor for this class, it was checked that there is an default constructor for the class.
### Methods
- createUser: createUser method was tested by calling the method to create an User, then an User object was created with the same fields. Then the ArrayList of Users in the database was gotten using the getter for the ArrayList. The ArrayList was then iterated through and checked whether there was an User in the ArrayList that was the same as the User Object created using the equals method. Then it was assertTrue on whether the object was created, where created was an boolean variable that was true if the User exist in the ArrayList, and an assertFalse was used to check that the method returned false when the User with same Username and password already existed.
- login: The login method was checked using an assertTrue with calling the login method with an user that already existed. If login method returned false, the test case failed because the user should exist and can be logged into.
- blockUser: The blockUser method was checked by first creating 2 User and making one of the user block another user. Then an ArrayList<User> was created with the blocked user as an element, and the method was called, and an assertEquals was used to check that the ArrayList<User> was equal to the first User's (blocker) getBlocked() which was an ArrayList of Users that they have blocked.
- friendUser: The friendUser method was checked by first creating 2 User and making one of the user friend another user. Then an ArrayList<User> was created with the friend user as an element, and the method was called, and an assertEquals was used to check that the ArrayList<User> was equal to the first User's (friender) getFriends() which was an ArrayList of Users that they have friended.
- returnUser: The returnUser method was tested by first creating an user in the database, then an assertEquals method was used to check that the user that returnUser returned was equal to the user created. Another assertEquals was used to check that when inputing an user that did not exist it returned null.

# Test Case for Client/Server
## Creating a User/Login
### Quitting the Program
- Start the program, User should be able to quit with the “x” button.
### Successfully Creating an User
- Start the program again, click the create new user button
- Enter a username that is unique. (First attempt should be able to create user)
- Enter in a password. Users should be created.
- A pane should be displayed upon Successful User Creation asking whether user wants to receive messages from all Users.
- After Selecting "Yes" or "No", User should be taken to a “Home Screen”.
- If User clicks the "x" button and does not make a selection, it will default to "No".
### Failing to Create User
- In another terminal, run the Client program again.
- Click the create new user button.
- Enter the same Username and password as before.
- A pane should display that User Already Exist.
- User should be able to change their desired Username and password and attempt to create a new user again.
- Enter in another unique Username and Password to create another User.
### Login Failure/Success
- Click the logout button on the program, User should be taken back to the Create User/Login Screen.
- Click on the login button, and enter in random username and password that do not exist.
- A pane should display to the user Invalid Username or Password.
- User should be able to change their username and password in another attempt to login.
- Correctly input the username and password made before, and user should be taken to the "Home Screen" again.
## Search User Success/Failure
- Enter in an invalid User that does not exist, and hit the Search Button.
- A pane should display an Error that User was not found.
- Enter in a valid Username (User that was first created).
- A pane should display that the User was found, and asks the User whether they want to add, block, or send a message to the Searched User.
### Block User
- Click the block button for the User.
- A pane should be displayed for the User to confirm that they want to block the Searched User.
- After confirmation, if the Searched User is in the friend list of the User, they will be automatically unfriended.
- ### Friend User
- Search the same User again, and click the add button for the User.
- A pane should be displayed for the User to confirm that they want to friend the Searched User.
- After confirmation, if the User originally blocked the Searched User, they will be automatically unblocked.
### Send Message
- Search the same user again, and click the Message Button
- User should be displayed all past messages, and a text field to input in the message they want to send.
- After inputting some message to the text field, click the Send Button.
- Message should be send to the user and displayed. Message should also be stored in a file.
