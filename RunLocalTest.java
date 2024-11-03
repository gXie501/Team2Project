import org.junit.Test;
import org.junit.Assert;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


import java.lang.reflect.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Team Project -- Run Local Test for Social Media App
 * 
 * File that helps to check if methods are written correctly
 * 
 * @author Gengjie Xie, Lab 19
 * 
 * @version Nov. 3, 2024
 * 
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {

        // Test Case to make sure that User class is declared correctly
        @Test(timeout = 1000)
        public void runUserDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = User.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `User` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `User` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `User` implements 1 interfaces!",
                    1, superinterfaces.length);

            // Checks that the User class implements the UserObjectInterface
            User check = new User("temp", "temp", "temp", false, null, null);
            Assert.assertTrue("The User class does not implement the UserObjectInterface",
                    check instanceof UserObjectInterface);
        }

        // Test case for the User class (Constructor and Methods(Getters and Setters))
        @Test
        public void runUserConstructorAndMethodTest() {
            // Tests the constructor exist
            try {
                Class<?> clazz = User.class;
                Constructor<?> constructor = clazz.getConstructor(String.class, String.class, String.class,
                        Boolean.class,
                        ArrayList.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail(
                        "User Constructor with 3 String, boolean, 2 ArrayList parameter does not exist or is not public!");
            }

            // Instantiate the User object with its constructor
            ArrayList<String> friend = new ArrayList<String>();
            friend.add("friend1");
            friend.add("friend2");
            friend.add("friend3");
            ArrayList<String> blocked = new ArrayList<String>();
            blocked.add("blocked1");
            blocked.add("blocked2");
            blocked.add("blocked3");
            User user = new User("GetterTest", "Password", "PFP", true, friend, blocked);

            // Tests for getter of User Class
            Assert.assertEquals("Ensure getUsername returns correct value", "GetterTest", user.getUsername());
            Assert.assertEquals("Ensure getPassword returns correct value", "Password", user.getPassword());
            Assert.assertEquals("Ensure getRestrictMessages returns correct value", true, user.getRestrictMessages());
            Assert.assertEquals("Ensure getFriends returns correct value", friend, user.getFriends());
            Assert.assertEquals("Ensure getBlocked returns correct value", blocked, user.getBlocked());
            Assert.assertEquals("Ensure getPfp returns correct value", "PFP", user.getPfp());

            // Tests for setters of User class
            user.setUsername("ChangedUser");
            user.setPassword("ChangedPassword");
            user.setRestrictMessages(false);
            user.setPfp("ChangedPFP");
            user.setFriends(new ArrayList<String>());
            user.setBlocked(new ArrayList<String>());

            Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedUser", user.getUsername());
            Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedPassword",
                    user.getPassword());
            Assert.assertEquals("Ensure setUsername changes the correct attribute", false, user.getRestrictMessages());
            Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(),
                    user.getFriends());
            Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(),
                    user.getBlocked());
            Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedPFP", user.getPfp());
        }

        // Test case to make sure that MessageDatabase class is declared correctly
        @Test(timeout = 1000)
        public void runMessageDatabaseDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = MessageDatabase.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `MessageDatabase` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `MessageDatabase` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));

            Assert.assertEquals("Ensure that `MessageDatabase` does not extend any class!",
                    Object.class, superclass); // before we were checking if exception extended messageDatabase 
            Assert.assertEquals("Ensure that `MessageDatabase` implements 1 interfaces!",
                    1, superinterfaces.length); 
            Assert.assertEquals("Ensure that `MessageDatabase` implements `MessageInterface`!",
                    MessageInterface.class, superinterfaces[0]); // makes sure messageDatabase implements messageInterface

        }

        // Test cases for the Message class (Constructor and Methods)
        @Test
        public void runMessageDatabaseTest() {
            // Tests that there is a constructor
            try {
                Class<?> clazz = MessageDatabase.class;
                Constructor<?> constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                Assert.fail("There is no Constructor in the Message Database Class");
            }

            // Checks the sendMessage Method
            User sender = new User("SendTester", "senderPassword", "cat.png", false, null, null);
            User receiver = new User("Receiver", "receiverPassword", "dog.png", false, null, null);
            MessageDatabase tester = new MessageDatabase();

            tester.sendMessage(sender, receiver, "Good Morning", "someFile.txt");
            String written = "SendTester;Receiver;Good Morning";
            boolean found = false;
            try {
                BufferedReader br = new BufferedReader(new FileReader("someFile.txt"));
                String line = br.readLine();
                while (line != null) {
                    if (line.equals(written)) {
                        found = true;
                        break;
                    }
                    line = br.readLine();
                }
                br.close();
                if (!found) {
                    Assert.assertTrue("The Message was not correctly saved to the correct file.", false);
                }
            } catch (IOException e) {
                Assert.assertTrue("An exception was encountered when reading the file.", false);
            }

            // Populate the File
            tester.sendMessage(sender, receiver, "Good Morning1", "someFile.txt");
            tester.sendMessage(sender, receiver, "Good Morning2", "someFile.txt");
            tester.sendMessage(sender, receiver, "Good Morning3", "someFile.txt");
            tester.sendMessage(sender, receiver, "Good Morning4", "someFile.txt");
            tester.sendMessage(sender, receiver, "Good Morning5", "someFile.txt");

            // Test the delete message method
            tester.deleteMessage(sender, receiver, "Good Morning5", "someFile.txt");
            found = false;
            written = "Good Morning5";
            try {
                BufferedReader br = new BufferedReader(new FileReader("someFile.txt"));
                String line = br.readLine();
                while (line != null) {
                    if (line.equals(written)) {
                        found = true;
                        break;
                    }
                    line = br.readLine();
                }
                br.close();
                if (found) {
                    Assert.assertTrue("The Message was not deleted correctly from the file.", false);
                }
            } catch (IOException e) {
                Assert.assertTrue("An exception was encountered when reading the file.", false);
            }

            // Test for the retreive message method
            User user1 = new User("user1", "user1Password", "user1.png", false, null, null);
            User user2 = new User("user2", "user2Password", "user2.png", false, null, null);
            MessageDatabase md = new MessageDatabase();
            // Send some message
            md.sendMessage(user1, user2, "Hello", "someFile.txt");
            md.sendMessage(user2, user1, "Hello", "someFile.txt");
            md.sendMessage(user1, user2, "How are you doing?", "someFile.txt");
            md.sendMessage(user2, user1, "I'm doing well, how about you?", "someFile.txt");
            md.sendMessage(user1, user2, "I'm doing well, I'll talk to you later", "someFile.txt");
            // Populate the expectedOutcome ArrayList
            ArrayList<String> expectedOutcome = new ArrayList<String>();
            expectedOutcome.add("user1;user2;Hello");
            expectedOutcome.add("user2;user1;Hello");
            expectedOutcome.add("user1;user2;How are you doing?");
            expectedOutcome.add("user2;user1;I'm doing well, how about you?");
            expectedOutcome.add("user1;user2;I'm doing well, I'll talk to you later");
            // Create the actual outcome ArrayList
            ArrayList<String> actualOutcome = md.retreiveMessages(user1, user2, "someFile.txt");
            // Compare
            Assert.assertTrue("The message retreived does not match the expected message: Expected: " + expectedOutcome.toString() + " Actual: " + actualOutcome.toString(),
                    expectedOutcome.equals(actualOutcome));
        }

        // Test case to make sure UserDatabase class is declared correctly
        @Test(timeout = 1000)
        public void runUserDatabaseDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = UserDatabase.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `UserDatabase` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `UserDatabase` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `UserDatabase` implements 1 interfaces!",
                    1, superinterfaces.length);

            // Checks that the UserDatabase implements the UserInterface
            UserDatabase check = new UserDatabase();
            Assert.assertTrue("The UserDatabase class does not implement the UserInterface",
                    check instanceof UserInterface);
        }

        @Test
        public void runUserDatabaseTest() {

            //Create UserDatabase and call it to create an user
            UserDatabase ud = new UserDatabase();
            ud.createUser("userDatabase", "12345", "database.png", false);

            //Read the file to check if the user is created successfully
            try {
                BufferedReader br = new BufferedReader(new FileReader("userFile.txt"));
                String line = br.readLine();
                boolean created = false;
                while (line != null) {
                    if (line.substring(0,line.indexOf(";")).equals("userDatabase")) {
                        created = true;
                        break;
                    }
                    line = br.readLine();
                }
                br.close();

                Assert.assertTrue("The User was not successfully created.", created);
            } catch (IOException e) {
                Assert.assertTrue("An Exception was encountered when reading the file.", false);
            }

            //Checks the login method
            Assert.assertTrue("User Failed to Login", ud.login("userDatabase", "12345"));

            //Checks the block user method
            //Correctly saves photos, using ImageIO. Create User needs to be a bit better
            User blocker = new User("blocker", "1234567890", "blocker.png", true, null, null);
            User blocked = new User("blocked", "0987654321", "blocked.png", false, null, null);
            ud.blockUser(blocker, blocked);
            ArrayList<String> expectedArrayList = new ArrayList<>();
            expectedArrayList.add(blocked.getUsername());
            Assert.assertEquals("User has not been added to the blocked ArrayList", expectedArrayList, blocker.getBlocked());

            try {
                BufferedReader br = new BufferedReader(new FileReader("userFile.txt"));
                String line = br.readLine();
                boolean correct = false;

                while (line != null) {
                    if (line.substring(0, line.indexOf(";")).equals(blocker.getUsername())) {
                        String[] parts = line.split(";");
                        if (parts[3].equals(blocked.getUsername())) {
                            correct = true;
                            break;
                        }
                    }
                    line = br.readLine();
                }
                br.close();
                Assert.assertTrue("User being blocked was not correctly added to the file.", correct);
            } catch (IOException e) {
                Assert.assertTrue("Error was encountered while reading the file.", false);
            }
            Assert.assertTrue("Database and User class need to match calls for block and friend user", false);
            

            //Checks the friend user method

            //Checks the search User method
            ud.createUser("search", "666666", "search.png", false);
            Assert.assertTrue("User was not found, when it existed", ud.searchUser("search"));
            Assert.assertFalse("User was found when it did not existed", ud.searchUser("doesNotExist"));
        }
    }
}