import java.io.*;
import java.util.ArrayList;

public class MessageDatabase implements MessageInterface {

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

    public ArrayList<String> retreiveMessages(User user1, User user2, String messageFile) {
        ArrayList<String> messages = new ArrayList<>();
        String userOneUsername = user1.getUsername();
        String userTwoUsername = user2.getUsername();

        try (BufferedReader bfr = new BufferedReader(new FileReader(messageFile))) {
            String line = bfr.readLine();
            while (line != null) {
                String currentMessage = bfr.readLine();
                String firstUser = currentMessage.substring(0, currentMessage.indexOf(";"));
                String newMessage = currentMessage.substring(currentMessage.indexOf(";") + 1);
                String secondUser = newMessage.substring(0, newMessage.indexOf(";"));

                if ((firstUser.equals(userOneUsername) || firstUser.equals(userTwoUsername))
                        && (secondUser.equals(userOneUsername) || secondUser.equals(userTwoUsername))) {
                    messages.add(currentMessage);
                }

                line = bfr.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;

    }

}
