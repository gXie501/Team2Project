import java.io.*;
import java.util.ArrayList;

/**
 * Team Project -- Message Database for Social Media App
 * 
 * File that sends, deletes, and retrieves messages from given users
 * 
 * @author Team 2, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */

public class MessageDatabase implements MessageInterface {

    // Given two users, the sender, receiver, content, and messageFile in that
    // order,
    // sendMessage will add a message in the messageFile using the
    // sender,receiver,content format
    public void sendMessage(User sender, User receiver, String content, String messageFile) {
        // if the content is an image, it will be stored as the image location (ex:
        // "dog.txt")
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(messageFile, true))) {
            bw.write(sender.getUsername() + ";" + receiver.getUsername() + ";" + content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Given the sender, receiver, and the content of the message,
    // deleteMessage will parse through the messageFile and delete the message with
    // the specified
    // sender, receiver, and content
    public void deleteMessage(User sender, User receiver, String content, String messageFile) {
        File tempfile = new File("tempFile.txt");
        File originalFile = new File(messageFile);

        try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(tempfile))) {
            String line = br.readLine();
            while (line != null) {
                String[] messageInfo = line.split(";", 3); // split line into sender, receiver, and content
                // if the lines are different, write the line into the temp file
                if (!(messageInfo[0].equals(sender.getUsername()) &&
                        messageInfo[1].equals(receiver.getUsername()) &&
                        messageInfo[2].equals(content))) {
                    // write line to temp file
                    bw.write(line);
                    bw.newLine();
                }
                // read next line
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Delete the original file and rename the temp file to the original filename
        if (originalFile.delete()) {
            tempfile.renameTo(originalFile);
        } else {
            System.out.println("Could not delete the original file.");
        }

    }

    // given two users, retrieveMessages will retrieve all messages between those
    // users in a given messageFiles
    public ArrayList<String> retrieveMessages(String userOneUsername, String userTwoUsername, String messageFile) {
        ArrayList<String> messages = new ArrayList<>();

        try (BufferedReader bfr = new BufferedReader(new FileReader(messageFile))) {
            String line = bfr.readLine();
            while (line != null) {
                String firstUser = line.substring(0, line.indexOf(";"));
                String newMessage = line.substring(line.indexOf(";") + 1);
                String secondUser = newMessage.substring(0, newMessage.indexOf(";"));

                if ((firstUser.equals(userOneUsername) || firstUser.equals(userTwoUsername))
                        && (secondUser.equals(userOneUsername) || secondUser.equals(userTwoUsername))) {
                    messages.add(line);
                }

                line = bfr.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;

    }

}
