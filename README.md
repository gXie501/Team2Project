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

- Anika Thapar
- Gengjie Xie
- Neha Pudota 
- Mithun Mahesh
- Leia Lynette Maduakolam


## Version History

* 0.1
    * Database Structure

## Classes & interfaces, outline
  # MessageDatabase

  The `MessageDatabase` class provides a simple implementation for handling user messages in a messaging application. It allows sending, deleting, and retrieving messages between users, with messages stored in a specified file. This class uses the `MessageInterface` interface, which should be defined separately.

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

  ## Usage Example

  ```java
  // Create instances of User for sender and receiver
  User sender = new User("alice");
  User receiver = new User("bob");

  // Specify the message content and the file where messages are stored
  String content = "Hello, Bob!";
  String messageFile = "messages.txt";

  // Send a message
  MessageDatabase.sendMessage(sender, receiver, content, messageFile);

  // Retrieve messages between Alice and Bob
  ArrayList<String> messages = MessageDatabase.retrieveMessages("alice", "bob", messageFile);

  // Delete a message
  MessageDatabase.deleteMessage(sender, receiver, content, messageFile);
```

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

  ## Usage Example

  ```java
  // Creating a new user
  ArrayList<User> friends = new ArrayList<>();
  ArrayList<User> blocked = new ArrayList<>();
  User user = new User("john_doe", "securePassword123", "profile.jpg", true, friends, blocked);

  // Setting user details
  user.setUsername("john_doe_updated");
  user.setPassword("newPassword456");

  // Adding a friend
  User friend = new User("jane_doe", "password789", "jane.jpg", true, new ArrayList<>(), new ArrayList<>());
  user.getFriends().add(friend);

  // Checking equality
  boolean isEqual = user.equals(friend); // false
```

# UserDatabase

The `UserDatabase` class is a component of a social media application that manages all `User` objects. It supports creating new users, managing user login, and updating the friends and blocked lists for each user. User data is stored in a file (`userFile.txt`) for persistence.

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

## Usage Example

```java
UserDatabase userDatabase = new UserDatabase();

// Create a new user
userDatabase.createUser("john_doe", "password123", "profile.jpg", true);

// Login
boolean isLoggedIn = userDatabase.login("john_doe", "password123");

// Block another user
User john = userDatabase.returnUser("john_doe");
User jane = new User("jane_doe", "password789", "jane.jpg", true, new ArrayList<>(), new ArrayList<>());
userDatabase.createUser(jane.getUsername(), jane.getPassword(), jane.getPfp(), jane.getRestrictMessages());
boolean isBlocked = userDatabase.blockUser(john, jane);

// Add a friend
boolean isFriended = userDatabase.friendUser(john, jane);


