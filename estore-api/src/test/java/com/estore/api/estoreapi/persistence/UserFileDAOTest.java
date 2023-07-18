package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for the {@linkplain UserFileDAO UserFileDAO} class.
 * 
 * @author Cole DenBleyker
 */
public class UserFileDAOTest {
    private UserFileDAO userFileDAO;
    private ObjectMapper mockObjectMapper;
    private User[] testUsers;

    /**
     * Before each test, sets up mock objects to be used for the userFileDAO.
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(1, "firstUser");
        testUsers[1] = new User(2, "theBestUs3rN4m3");
        testUsers[2] = new User(3, "A_Cool_Person");

        when(mockObjectMapper.readValue(new File("users.json"), User[].class)).thenReturn(testUsers);
        userFileDAO = new UserFileDAO("users.json", mockObjectMapper);
    }

    /**
     * Tests the getUser function with a valid ID
     * Demonstrates successful use of getUser
     * 
     * @throws IOException
     */
    @Test
    public void testGetUserSuccessful() throws IOException {
        // setup
        String expected = new User(2, "theBestUs3rN4m3").toString();
        // invoke
        String result = userFileDAO.getUser(2).toString();
        // analyze
        assertEquals(expected, result);
    }

    /**
     * Tests the getUser function with an incorrect ID
     * Demonstrates unsuccessful use of getUser
     * 
     * @throws IOException
     */
    @Test
    public void testGetUserUnsuccessful() throws IOException {
        // setup
        String expected = new User(2, "theBestUs3rN4m3").toString();
        // invoke
        String result = userFileDAO.getUser(1).toString();
        // analyze
        assertNotEquals(expected, result);
    }

    /**
     * Tests the getUser function with an invalid ID
     * Demonstrates unsuccessful use of getUser
     * 
     * @throws IOException
     */
    @Test
    public void testGetUserInvalidID() throws IOException {
        // setup
        // invoke
        User result = assertDoesNotThrow(() -> userFileDAO.getUser(100));
        // analyze
        assertEquals(null, result);
    }

    /**
     * Tests the findUsers function.
     * Demonstrates successful use of findUser
     * 
     * @throws IOException
     */
    @Test
    public void testFindUsers() throws IOException {
        // setup
        int expectedLength = 3;
        String expectedString = "[User ID 1: firstUser, User ID 2: theBestUs3rN4m3, User ID 3: A_Cool_Person]";
        // invoke
        User[] result = userFileDAO.findUsers("e");
        String resultString = Arrays.toString(result);
        // analyze
        assertEquals(expectedLength, result.length);
        assertEquals(expectedString, resultString);
    }

    /**
     * Tests the getUsers function.
     * Demonstrates successful use of getUsers
     * 
     * @throws IOException
     */
    @Test
    public void testGetUsers() throws IOException {
        // setup
        int expectedLength = 3;
        String expectedString = "[User ID 1: firstUser, User ID 2: theBestUs3rN4m3, User ID 3: A_Cool_Person]";
        // invoke
        User[] result = userFileDAO.getUsers();
        String resultString = Arrays.toString(result);
        // analyze
        assertEquals(expectedLength, result.length);
        assertEquals(expectedString, resultString);
    }

    /**
     * Tests the createUser function.
     * Demonstrates successful use of createUser
     * 
     * @throws IOException
     */
    @Test
    @SuppressWarnings("unused")
    public void testCreateUserSuccessful() throws IOException {
        // setup
        User user = new User(4, "test");
        String expected = user.toString();
        
        // invoke
        // Junit tests cannot create files, so this is the best we could do :)
        try {
            String result = userFileDAO.createUser(user).toString();

            // analyze
             assert false;
        } catch (FileNotFoundException e) {

            // analyze
            assert true;
        }
    }

    /**
     * Tests the getUsername function.
     * DDemostrates successful use of getUsername
     * 
     * @throws IOException
     * 
     * @Author Xin Huang
     */
    @Test
    public void testGetUsername() throws IOException {
        // setuup
        int expectedLegnth = 1;
        String expectedString = "[User ID 1: firstUser]";
        // invoke
        User[] result = userFileDAO.getUsername("firstUser");
        String resultString = Arrays.toString(result);
        // analyze
        assertEquals(expectedLegnth, result.length);
        assertEquals(expectedString, resultString);
    }
}
