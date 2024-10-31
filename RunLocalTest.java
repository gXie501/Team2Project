import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

import static org.junit.Assert.*;

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

        @Test(timeout = 1000)
        public void MessageDatabaseDeclarationTest() {
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
            Assert.assertEquals("Ensure that `MessageDatabase` extends `Exception`!",
                    Exception.class, superclass);
            Assert.assertEquals("Ensure that `MessageDatabase` implements 1 interfaces!",
                    1, superinterfaces.length);
        }
    }

    @Test(timeout = 1000)
    public void UserDatabaseDeclarationTest() {
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
    }

    @Test
    public void testUserConstructorAndMethod() {
        //Tests the constructor exist
        try {
            Class<?> clazz = User.class;
            Constructor<?> constructor = clazz.getConstructor(String.class, String.class, String.class, Boolean.class,
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

        //Tests for getter of User Class
        Assert.assertEquals("Ensure getUsername returns correct value", "GetterTest", user.getUsername());
        Assert.assertEquals("Ensure getPassword returns correct value", "Password", user.getPassword());
        Assert.assertEquals("Ensure getRestrictMessages returns correct value", true, user.getRestrictMessages());
        Assert.assertEquals("Ensure getFriends returns correct value", friend, user.getFriends());
        Assert.assertEquals("Ensure getBlocked returns correct value", blocked, user.getBlocked());
        Assert.assertEquals("Ensure getPfp returns correct value", "PFP", user.getPfp());

        //Tests for setters of User class
        user.setUsername("ChangedUser");
        user.setPassword("ChangedPassword");
        user.setRestrictMessages(false);
        user.setPfp("ChangedPFP");
        user.setFriends(new ArrayList<String>());
        user.setBlocked(new ArrayList<String>());

        Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedUser", user.getUsername());
        Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedPassword", user.getPassword());
        Assert.assertEquals("Ensure setUsername changes the correct attribute", false, user.getRestrictMessages());
        Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(), user.getFriends());
        Assert.assertEquals("Ensure setUsername changes the correct attribute", new ArrayList<String>(), user.getBlocked());
        Assert.assertEquals("Ensure setUsername changes the correct attribute", "ChangedPFP", user.getPfp());
    }
}