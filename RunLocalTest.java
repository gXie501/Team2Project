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
}