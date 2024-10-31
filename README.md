# Team2Project
- Requirements:
  - User profiles.
  - New user account creation.
  - Password-protected login.
  - User search.
  - User viewer.
  - Add, block, and remove friend features.
  - Extra credit opportunity – Add support to upload and display profile pictures.
- Messaging requirements:
  - Send and delete messages.
  - Block users from sending messages (see block user above).
  - Restrict messages to either all users or friends only (see add friend above).
  - Extra credit opportunity – Add photo messaging.

  Things to discuss next meeting/things to add:
  1. User class
    Variables:
      - String username
      - String password
      - ArrayList friends
      - ArrayList blocked
      - (?) profilePicture
      - boolean restrictMessages
        - indicates whether or not the user will allow messages from users who aren't their friend
    Methods:
      - getters and setters
  2. User database
    methods (return types?):
      - createUser(username, pw, restrictMessage)
      - login(username, pw)
      - blockUser(User user, User blockUser)
      - friendUser(User user, User blockUser)
      - public boolean searchUser(User user)
  
  3. Message class
    Variables:
      - User sender
      - User receiver
      - String messageContents
    Methods:
      - getters and setters
  4. Message database
    Methods (return types?):
      - sendMessage(User sender, User receiver, String content)
      - deleteMessage(User sender, User receiver, String content)
      - retreiveMessages(User sender, User receiver)


  Userfile format: username;password;friends;blocked;profilePicture
  messageFile format: sender;receiver; message


