import org.junit.Test;
import org.junit.Assert;
import java.io.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Database.MessageDatabase;
import Database.MessageInterface;
import Database.User;
import Database.UserDatabase;
import Database.UserInterface;
import Database.UserObjectInterface;

import java.lang.reflect.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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

			Assert.assertTrue("Ensure that User is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that User is NOT abstract!",
					Modifier.isAbstract(modifiers));
			Assert.assertEquals("Ensure that User implements 2 interfaces!",
					2, superinterfaces.length);

			// Checks that the User class implements the UserObjectInterface
			User check = new User("temp", "temp", "temp", false, null, null);
			Assert.assertTrue("The User class does not implement the UserObjectInterface",
					check instanceof UserObjectInterface);
			Assert.assertTrue("The User class does not implement the Serializable Interface",
					check instanceof Serializable);
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
			ArrayList<User> friend = new ArrayList<User>();
			friend.add(
					new User("friend1", "123", "w28RK.png", false, new ArrayList<User>(), new ArrayList<User>()));
			friend.add(
					new User("friend2", "1231", "w28RK.png", false, new ArrayList<User>(), new ArrayList<User>()));
			friend.add(
					new User("friend3", "123", "w28RK.png", false, new ArrayList<User>(), new ArrayList<User>()));
			ArrayList<User> blocked = new ArrayList<User>();
			blocked.add(new User("blocked1", "12233", "w28RK.png", false, new ArrayList<User>(),
					new ArrayList<User>()));
			blocked.add(new User("blocked2", "1223143", "w28RK.png", false, new ArrayList<User>(),
					new ArrayList<User>()));
			blocked.add(new User("blocked3", "121423", "w28RK.png", false, new ArrayList<User>(),
					new ArrayList<User>()));
			User user = new User("GetterTest", "Password", "w28RK.png", true, friend,
					blocked);

			// Tests for getter of User Class
			Assert.assertEquals("Ensure getUsername returns correct value", "GetterTest", user.getUsername());
			Assert.assertEquals("Ensure getPassword returns correct value", "Password", user.getPassword());
			Assert.assertEquals("Ensure getRestrictMessages returns correct value", true, user.getRestrictMessages());
			Assert.assertEquals("Ensure getFriends returns correct value", friend, user.getFriends());
			Assert.assertEquals("Ensure getBlocked returns correct value", blocked, user.getBlocked());
			Assert.assertEquals("Ensure getPfp returns correct value", "profile_pictures/GetterTest.png",
					user.getPfp());

			// Tests for setters of User class
			user.setUsername("ChangedUser");
			user.setPassword("ChangedPassword");
			user.setRestrictMessages(false);
			user.setFriends(new ArrayList<User>());
			user.setBlocked(new ArrayList<User>());

			Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedUser", user.getUsername());
			Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedPassword",
					user.getPassword());
			Assert.assertEquals("Ensure setUsername changes the correct attribute", false, user.getRestrictMessages());
			Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(),
					user.getFriends());
			Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(),
					user.getBlocked());

			// Test for Equals Method
			User check = new User("false", "false", "w28RK.png", false, null, null);
			Assert.assertFalse("Object was equals to each other when they are not", user.equals(check));
			Assert.assertTrue("Object was not equals to each other when they are", user.equals(user));
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

			Assert.assertTrue("Ensure that MessageDatabase is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that MessageDatabase is NOT abstract!",
					Modifier.isAbstract(modifiers));

			Assert.assertEquals("Ensure that MessageDatabase does not extend any class!",
					Object.class, superclass); // before we were checking if exception extended messageDatabase
			Assert.assertEquals("Ensure that MessageDatabase implements 1 interfaces!",
					1, superinterfaces.length);
			Assert.assertEquals("Ensure that MessageDatabase implements MessageInterface!",
					MessageInterface.class, superinterfaces[0]); // makes sure messageDatabase implements
																	// messageInterface

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
			UserDatabase ud = new UserDatabase();
			ud.createUser("SendTester", "senderPassword", "w28RK.png", false);
			User sender = ud.returnUser("SendTester");
			ud.createUser("Receiver", "receiverPassword", "w28RK.png", false);
			User receiver = ud.returnUser("Receiver");
			MessageDatabase tester = new MessageDatabase();

			tester.sendMessage(sender, receiver, "Good Morning", "testFile.txt");
			String written = "SendTester;Receiver;Good Morning";
			boolean found = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader("testFile.txt"));
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
			tester.sendMessage(sender, receiver, "Good Morning1", "testFile.txt");
			tester.sendMessage(sender, receiver, "Good Morning2", "testFile.txt");
			tester.sendMessage(sender, receiver, "Good Morning3", "testFile.txt");
			tester.sendMessage(sender, receiver, "Good Morning4", "testFile.txt");
			tester.sendMessage(sender, receiver, "Good Morning5", "testFile.txt");

			// Test the delete message method
			tester.deleteMessage(sender, receiver, "Good Morning5", "testFile.txt");
			found = false;
			written = "Good Morning5";
			try {
				BufferedReader br = new BufferedReader(new FileReader("testFile.txt"));
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
			ud.createUser("user1", "user1Password", "w28RK.png", false);
			ud.createUser("user2", "user2Password", "w28RK.png", false);
			User user1 = ud.returnUser("user1");
			User user2 = ud.returnUser("user2");
			MessageDatabase md = new MessageDatabase();
			// Send some message
			md.sendMessage(user1, user2, "Hello", "testFile.txt");
			md.sendMessage(user2, user1, "Hello", "testFile.txt");
			md.sendMessage(user1, user2, "How are you doing?", "testFile.txt");
			md.sendMessage(user2, user1, "I'm doing well, how about you?", "testFile.txt");
			md.sendMessage(user1, user2, "I'm doing well, I'll talk to you later", "testFile.txt");
			// Populate the expectedOutcome ArrayList
			ArrayList<String> expectedOutcome = new ArrayList<String>();
			expectedOutcome.add("user1;user2;Hello");
			expectedOutcome.add("user2;user1;Hello");
			expectedOutcome.add("user1;user2;How are you doing?");
			expectedOutcome.add("user2;user1;I'm doing well, how about you?");
			expectedOutcome.add("user1;user2;I'm doing well, I'll talk to you later");
			// Create the actual outcome ArrayList
			ArrayList<String> actualOutcome = md.retrieveMessages("user1", "user2", "testFile.txt");
			// Compare
			Assert.assertTrue(
					"The message retreived does not match the expected message: Expected: " + expectedOutcome.toString()
							+ " Actual: " + actualOutcome.toString(),
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

			Assert.assertTrue("Ensure that UserDatabase is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that UserDatabase is NOT abstract!",
					Modifier.isAbstract(modifiers));
			Assert.assertEquals("Ensure that UserDatabase implements 1 interfaces!",
					1, superinterfaces.length);

			// Checks that the UserDatabase implements the UserInterface
			UserDatabase check = new UserDatabase();
			Assert.assertTrue("The UserDatabase class does not implement the UserInterface",
					check instanceof UserInterface);
		}

		@Test
		public void runUserDatabaseTest() {
			try {
				Class<?> clazz = UserDatabase.class;
				Constructor<?> constructor = clazz.getConstructor();
			} catch (NoSuchMethodException e) {
				Assert.fail("There is no Constructor in the User Database Class");
			}

			// Create UserDatabase and call it to create an user
			UserDatabase ud = new UserDatabase();
			ud.createUser("userDatabase", "12345", "w28RK.png", false);
			ArrayList<User> friends = new ArrayList<User>();
			ArrayList<User> block = new ArrayList<User>();
			User create = new User("userDatabase", "12345", "w28RK.png", false, friends, block);
			boolean created = false;
			ArrayList<User> users = ud.getUsers();
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).equals(create)) {
					created = true;
				}
			}
			Assert.assertTrue("User Object was not created.", created);

			// Checks that false is return for existing user
			Assert.assertFalse("Existing User is created again, when it should not.",
					ud.createUser("userDatabase", "12345", "w28RK.png", false));

			// Checks the login method
			Assert.assertTrue("User Failed to Login", ud.login("userDatabase", "12345"));

			// Checks the friend user method
			ud.createUser("friender", "1234567890", "w28RK.png", true);
			ud.createUser("friend", "0987654321", "w28RK.png", false);
			User friender = ud.returnUser("friender");
			User friend = ud.returnUser("friend");
			ud.friendUser(friender, friend);
			ArrayList<User> expectedArrList = new ArrayList<User>();
			expectedArrList.add(friend);
			Assert.assertEquals("User has not been added to the friend ArrayList", expectedArrList,
					friender.getFriends());
			// Checks the search User method
			ud.createUser("search", "666666", "w28RK.png", false);
			User search = ud.returnUser("search");
			Assert.assertEquals("An User was found when the User does not exist", ud.returnUser("hello"), null);
			Assert.assertEquals("An User was found when the User does not exist", ud.returnUser("search"), search);
		}

		@Test
		public void runBlockedUserTest() {
			UserDatabase ud = new UserDatabase();
			ud.createUser("blocker", "1234567890", "w28RK.png", true);
			ud.createUser("blocked", "0987654321", "w28RK.png", false);
			User blocker = ud.returnUser("blocker");
			User blocked = ud.returnUser("blocked");
			ud.blockUser(blocker, blocked);
			ArrayList<User> expectedArrayList = new ArrayList<User>();
			expectedArrayList.add(blocked);
			Assert.assertEquals("User has not been added to the blocked ArrayList", expectedArrayList,
					blocker.getBlocked());
		}

		@Test
		public void runRestrictUserTest() {
			UserDatabase ud = new UserDatabase();
			ud.createUser("Tester", "123456", "w28RK.png", false);
			ud.restrictUser("Tester", true);
			Assert.assertTrue("User Restriction was not changed: Expected: True, Actual: False",
					ud.returnUser("Tester").getRestrictMessages());
			ud.restrictUser("Tester", false);
			Assert.assertFalse("User Restriction was not changed: Expected: False, Actual: True",
					ud.returnUser("Tester").getRestrictMessages());
		}

		@Test(timeout = 1000)
		public void ClientDeclarationTest() {
			Class<?> clazz;
			int modifiers;
			Class<?> superclass;
			Class<?>[] superinterfaces;

			clazz = Client.class;

			modifiers = clazz.getModifiers();

			superclass = clazz.getSuperclass();

			superinterfaces = clazz.getInterfaces();

			Assert.assertTrue("Ensure that Client is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that Client is NOT abstract!",
					Modifier.isAbstract(modifiers));

			Assert.assertEquals("Ensure that Client does not extend any class!",
					Object.class, superclass); // before we were checking if exception extended messageDatabase
			Assert.assertEquals("Ensure that Client implements 1 interfaces!",
					1, superinterfaces.length);
			Assert.assertEquals("Ensure that Client implements Client Interface!",
					ClientInterface.class, superinterfaces[0]);
		}

		@Test(timeout = 1000)
		public void ServerDeclarationTest() {
			Class<?> clazz;
			int modifiers;
			Class<?> superclass;
			Class<?>[] superinterfaces;

			clazz = Server.class;

			modifiers = clazz.getModifiers();

			superclass = clazz.getSuperclass();

			superinterfaces = clazz.getInterfaces();

			Assert.assertTrue("Ensure that Server is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that Server is NOT abstract!",
					Modifier.isAbstract(modifiers));

			Assert.assertEquals("Ensure that Server does not extend any class!",
					Object.class, superclass); // before we were checking if exception extended messageDatabase
			Assert.assertEquals("Ensure that Server implements 1 interfaces!",
					1, superinterfaces.length);
			Assert.assertEquals("Ensure that Server implements Server Interface!",
					ServerInterface.class, superinterfaces[0]);
		}

		@Test(timeout = 1000)
		public void ClientHandlerDeclarationTest() {
			Class<?> clazz;
			int modifiers;
			Class<?> superclass;
			Class<?>[] superinterfaces;

			clazz = ClientHandler.class;

			modifiers = clazz.getModifiers();

			superclass = clazz.getSuperclass();

			superinterfaces = clazz.getInterfaces();

			Assert.assertTrue("Ensure that ClientHandler is public!",
					Modifier.isPublic(modifiers));
			Assert.assertFalse("Ensure that ClientHandler is NOT abstract!",
					Modifier.isAbstract(modifiers));

			Assert.assertEquals("Ensure that ClientHandler does not extend any class!",
					Object.class, superclass); // before we were checking if exception extended messageDatabase
			Assert.assertEquals("Ensure that ClientHandler implements 2 interfaces!",
					2, superinterfaces.length);
			Assert.assertEquals("Ensure that ClientHandler implements Runnable Interface!",
					Runnable.class, superinterfaces[0]);
			Assert.assertEquals("Ensure that ClientHandler implements ClientHandler Interface!",
					ClientHandlerInterface.class, superinterfaces[1]);
		}

		@Test
		public void ClientHandlerConstructorTest() {
			try {
				Class<?> clazz = ClientHandler.class;
				Constructor<?> constructor = clazz.getConstructor(Socket.class, UserDatabase.class,
						MessageDatabase.class);
			} catch (NoSuchMethodException e) {
				Assert.fail(
						"Constructor with a Socket, UserDatabase, and MessageDatabase does not exist or is not public");
			}
		}
	}
}