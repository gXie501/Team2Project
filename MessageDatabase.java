import java.io.*;
import java.util.ArrayList;

/**
 * Manages sending, deleting, and retrieving messages between users.
 * 
 * @version Nov. 3, 2024
 */
public class MessageDatabase implements MessageInterface {

    /**
     * Sends a message from sender to receiver with the specified content and saves it to the message file.
     * 
     * @param sender the user sending the message
     * @param receiver the user receiving the message
     * @param content the content of the message
     * @param messageFile the file where the message will be saved
     */
    public void sendMessage(User sender, User receiver, String content, String messageFile) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(messageFile, true))) {
            bufferedWriter.write(sender.getUsername() + ";" + receiver.getUsername() + ";" + content);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a message from the message file matching the specified sender, receiver, and content.
     * 
     * @param sender the user who sent the message
     * @param receiver the user who received the message
     * @param content the content of the message to delete
     * @param messageFile the file where the message is stored
     */
    public void deleteMessage(User sender, User receiver, String content, String messageFile) {
        File tempFile = new File("tempFile.txt");
        File originalFile = new File(messageFile);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] messageInfo = line.split(";");
                if (!(messageInfo[0].equals(sender.getUsername()) &&
                        messageInfo[1].equals(receiver.getUsername()) &&
                        messageInfo[2].equals(content))) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!originalFile.delete() || !tempFile.renameTo(originalFile)) {
            System.out.println("Could not delete the original file.");
        }
    }

    /**
     * Retrieves all messages between the specified users from the message file.
     * 
     * @param userOneUsername the username of the first user
     * @param userTwoUsername the username of the second user
     * @param messageFile the file where messages are stored
     * @return an ArrayList of messages between the two users
     */
    public ArrayList<String> retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile) {
        ArrayList<String> messages = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(messageFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] messageParts = line.split(";", 3);
                if (messageParts.length == 3) {
                    String firstUser = messageParts[0];
                    String secondUser = messageParts[1];
                    if ((firstUser.equals(userOneUsername) || firstUser.equals(userTwoUsername)) &&
                            (secondUser.equals(userOneUsername) || secondUser.equals(userTwoUsername))) {
                        messages.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
