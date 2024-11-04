import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Executes local tests for the social media application.
 * This class checks the correctness of methods in User, UserDatabase, and MessageDatabase classes.
 * 
 * @version Nov. 3, 2024
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

    /**
     * Contains unit test cases for verifying the functionality of User, UserDatabase, 
     * and MessageDatabase classes.
     */
    public static class TestCase {

        /**
         * Tests the declaration of the User class to ensure it is public, non-abstract, 
         * and implements the required interface.
         */
        @Test(timeout = 1000)
        public void runUserDeclarationTest() {
            Class<?> clazz = User.class;
            int modifiers = clazz.getModifiers();
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `User` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `User` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `User` implements 1 interface!", 1, superinterfaces.length);
            Assert.assertTrue("User class does not implement UserObjectInterface",
                    new User("temp", "temp", "temp", false, null, null) instanceof UserObjectInterface);
        }

        /**
         * Tests the constructor and getter/setter methods of the User class.
         */
        @Test
        public void runUserConstructorAndMethodTest() {
            try {
                Constructor<?> constructor = User.class.getConstructor(String.class, String.class, String.class,
                        Boolean.class, ArrayList.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("User constructor with expected parameters is missing or not public!");
            }

            ArrayList<User> friends = new ArrayList<>();
            ArrayList<User> blocked = new ArrayList<>();
            User user = new User("GetterTest", "Password", "w28RK.png", true, friends, blocked);

            // Tests for getters
            Assert.assertEquals("Ensure getUsername returns correct value", "GetterTest", user.getUsername());
            Assert.assertEquals("Ensure getPassword returns correct value", "Password", user.getPassword());
            Assert.assertEquals("Ensure getRestrictMessages returns correct value", true, user.getRestrictMessages());
            Assert.assertEquals("Ensure getFriends returns correct value", friends, user.getFriends());
            Assert.assertEquals("Ensure getBlocked returns correct value", blocked, user.getBlocked());

            // Tests for setters
            user.setUsername("ChangedUser");
            user.setPassword("ChangedPassword");
            user.setRestrictMessages(false);
            user.setFriends(new ArrayList<>());
            user.setBlocked(new ArrayList<>());

            Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedUser", user.getUsername());
            Assert.assertEquals("Ensure setPassword changes the correct attribute", "ChangedPassword", user.getPassword());
            Assert.assertFalse("Ensure setRestrictMessages changes the correct attribute", user.getRestrictMessages());
            Assert.assertEquals("Ensure setFriends changes the correct attribute", new ArrayList<>(), user.getFriends());
            Assert.assertEquals("Ensure setBlocked changes the correct attribute", new ArrayList<>(), user.getBlocked());
        }

        /**
         * Tests the declaration of the MessageDatabase class to ensure it is public, non-abstract,
         * and implements the MessageInterface.
         */
        @Test(timeout = 1000)
        public void runMessageDatabaseDeclarationTest() {
            Class<?> clazz = MessageDatabase.class;
            int modifiers = clazz.getModifiers();
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `MessageDatabase` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `MessageDatabase` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `MessageDatabase` implements 1 interface!", 1, superinterfaces.length);
            Assert.assertEquals("Ensure that `MessageDatabase` implements `MessageInterface`!", MessageInterface.class, superinterfaces[0]);
        }

        /**
         * Tests methods of the MessageDatabase class for functionality.
         */
        @Test
        public void runMessageDatabaseTest() {
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.createUser("SendTester", "senderPassword", "w28RK.png", false);
            User sender = userDatabase.returnUser("SendTester");
            userDatabase.createUser("Receiver", "receiverPassword", "w28RK.png", false);
            User receiver = userDatabase.returnUser("Receiver");
            MessageDatabase messageDatabase = new MessageDatabase();

            messageDatabase.sendMessage(sender, receiver, "Good Morning", "testFile.txt");

            boolean found = false;
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("testFile.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("SendTester;Receiver;Good Morning")) {
                        found = true;
                        break;
                    }
                }
            } catch (IOException e) {
                Assert.fail("An exception was encountered when reading the file.");
            }
            Assert.assertTrue("The message was not correctly saved to the file.", found);
        }

        /**
         * Tests UserDatabase class for user creation, login, blocking, and friend functions.
         */
        @Test
        public void runUserDatabaseTest() {
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.createUser("userDatabase", "12345", "w28RK.png", false);
            User user = userDatabase.returnUser("userDatabase");

            Assert.assertNotNull("User object was not created.", user);
            Assert.assertTrue("User failed to log in", userDatabase.login("userDatabase", "12345"));

            // Test for adding a friend
            userDatabase.createUser("friendUser", "password", "w28RK.png", true);
            User friendUser = userDatabase.returnUser("friendUser");
            userDatabase.friendUser(user, friendUser);
            Assert.assertTrue("Friend was not added.", user.getFriends().contains(friendUser));

            // Test for blocking a user
            userDatabase.createUser("blockedUser", "password", "w28RK.png", true);
            User blockedUser = userDatabase.returnUser("blockedUser");
            userDatabase.blockUser(user, blockedUser);
            Assert.assertTrue("User was not blocked.", user.getBlocked().contains(blockedUser));
        }
    }
}
