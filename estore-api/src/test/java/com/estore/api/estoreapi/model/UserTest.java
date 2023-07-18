package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/* 
 * Tests for the {@linkplain User User} class.  
 * 
 * @author Xin Huang
*/
public class UserTest {
    /**
     * Tests the constructor function to make sure that it
     * properly constructs a user.
     * With it giving the right userId and userName
     * @author Xin Huang
     */
    @Test
    public void test_Constructor() {
        // setup
        int expected_id = 50;
        String expected_Username = "Xintilleon";

        // invoke
        User user = new User(expected_id, expected_Username);

        // analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_Username, user.getUsername());
    }


    /**
     * Tests the constructor function to make sure that it
     * properly constructs a user.
     * Making sure that it does not give equals no matter what
     * @author Xin Huang
     */
    @Test
    public void test_Constructor_Not_Equals() {
        int expected_id = 50;
        String expected_Username = "Xintilleon";

        int unexpected_id = 51;
        String unexpected_Username = "Xin";

        // invoke
        User user = new User(expected_id, expected_Username);

        // analyze
        assertTrue(user.getId() != unexpected_id);
        assertTrue(user.getUsername() != unexpected_Username);

    }
    /**
     * Makes sure the toString function works as intended
     * @author Xin Huang
     */
    @Test
    public void test_toString() {
        // setup
        int id = 50;
        String Username = "Xintilleon";
        String expected_string = "User ID 50: Xintilleon";
        User user = new User(id, Username);

        // invoke
        String actual_string = user.toString();

        // analyze
        assertEquals(expected_string, actual_string);

    }

    /**
     * Makes sure the idString function works as intended
     * @author Xin Huang
     */
    @Test
    public void test_idString() {
        // setup
        int id = 50;
        String Username = "Xintilleon";
        String expected_string = "User ID 50";
        User user = new User(id, Username);

        // invoke
        String actual_string = user.idString();

        // analyze
        assertEquals(expected_string, actual_string);

    }

    /**
     * Makes sure the usernameString function works as intended
     * @author Xin Huang
     */
    @Test
    public void test_usernameString() {
        // setup
        int id = 50;
        String Username = "Xintilleon";
        String expected_string = "Username Xintilleon";
        User user = new User(id, Username);

        // invoke
        String actual_string = user.usernameString();

        // analyze
        assertEquals(expected_string, actual_string);

    }
}
