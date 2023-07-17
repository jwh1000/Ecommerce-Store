package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;

/**
 * Tests for the User Controller Class
 * 
 * @author Xin Huang
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each testm create a new UserController object and inject a mock
     * User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    /*
     * Tests to make sure the return for search works for the controller.
     */
    @Test
    public void testSearchUsersFound() throws IOException { // findUsers may throw IOException
        String searchString = "int";
        User[] users = new User[2];
        users[0] = new User(3, "dirtyinter");
        users[1] = new User(1, "Xintilleon");
        // When findusers is called with the search string, return the two
        // user above
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    /*
     * Tests to make sure the HttpStatus Not found works for search
     */
    @Test
    public void testSearchUsersNotFound() throws IOException {
        // Setup
        String searchString = "Problem";
        User[] users = new User[2];
        User user = new User(3, "dirtyinter");
        users[0] = user;
        users[1] = new User(1, "Xintilleon");
        User[] empty = new User[1];
        when(mockUserDAO.findUsers(user.getUsername())).thenReturn(empty);
        // user above
        when(mockUserDAO.findUsers(searchString)).thenReturn(empty);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


    }

    /*
     * Tests to make sure the Handle exception works for search users
     */
    @Test
    public void testSearchUsersHandleException() throws IOException { // findUsers may throw IOException
        // Setup
        String searchString = "an";
        // When findUsers is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).findUsers(searchString);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
    /*
     * Tests to make sure that create user works
     */

    @Test
    public void testcreateUser() throws IOException {
        // setup
        User user = new User(1, "Xintilleon");
        User[] users = new User[4];
        users[0] = user;
        User[] empty = new User[0];
        when(mockUserDAO.findUsers(user.getUsername())).thenReturn(empty);
        // when createProduct is called, return true simulating successful creation
        // save
        when(mockUserDAO.createUser(user)).thenReturn(user);
        // Invoke
        ResponseEntity<User> response = userController.createUser(user);
        // analysis
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    /*
     * Tests to make sure the handle exception works for create users.
     */
    @Test
    public void testcreateUserHandleException() throws IOException {
        // setup
        User user = new User(1, "Xintilleon");
        User[] empty = new User[0];

        when(mockUserDAO.findUsers(user.getUsername())).thenReturn(empty);

        // when createUser is called on the mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);
        // invoke
        ResponseEntity<User> response = userController.createUser(user);
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * Tests to make sure that get user works
     */
    @Test 
    public void testgetUser() throws IOException {
        // setup
        User user = new User(1, "Xintilleon");
        User[] users = new User[1];
        users[0] = user;
        // when get user is called
        when(mockUserDAO.getUser(1)).thenReturn(user);
        // invoke
        ResponseEntity<User> response = userController.getUser(1);
        //analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());    
    }
    /*
     * Tests to make sure that the correct response is shared when user is not found
     */
    @Test
    public void testGetUserNotFound() throws IOException {
        // setup
        User user = new User(1, "Xintilleon");
        User[] users = new User[1];
        users[0] = user;
        // when get user is called and not found
        when(mockUserDAO.getUser(5)).thenReturn(null);
        // invoke
        ResponseEntity<User> response = userController.getUser(5);
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null,response.getBody());
    }
    /*
     * Tests to make sure that the get user sends back a Internal error when promted
     */
    @Test
    public void testGetUserInternalError() throws IOException {
        // setup
        // when get user is called and Iternal Server Error
        doThrow(new IOException()).when(mockUserDAO).getUser(1);
        // invoke
        ResponseEntity<User> response = userController.getUser(1);
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    /*
     * Tests to make sure that user array gets returned when get users is called
     */
    @Test
    public void testGetUsers() throws IOException {
        // setup
        User user = new User(1, "Xintilleon");
        User user2 = new User(2, "DirtyFrank");
        User[] users = new User[2];
        users[0] = user;
        users[1] = user2;
        // when get users is called
        when(mockUserDAO.getUsers()).thenReturn(users);
        // invoke
        ResponseEntity<User[]> response = userController.getUsers();
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

    }

    /*
     * Tests to make sure that the get users sends back a Internal error when promted
     */
    @Test
    public void testGetUsersInternalError() throws IOException {
        // setup
        // when get user is called and Iternal Server Error
        doThrow(new IOException()).when(mockUserDAO).getUsers();
        // invoke
        ResponseEntity<User[]> response = userController.getUsers();
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /*
     * Tests to make sure that authenticateUsers work
     */
    @Test
    public void testAuthenticateUsers() throws IOException {
        //setup
        User user = new User(1, "Xintilleon");
        User user2 = new User(2, "DirtyFrank");
        User[] users = new User[2];
        users[0] = user;
        users[1] = user2;
        // when get users is called
        when(mockUserDAO.getUsername("Xintilleon")).thenReturn(users);
        // invoke
        ResponseEntity<User> response = userController.authenticateUser("Xintilleon");
        // analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /*
     * Tests to make sure that authenticateUsers sends not found when promted.
     */
    @Test
    public void testAuthenticateUsersNotFound() throws IOException {
        //setup
        User user = new User(1, "Xintilleon");
        User user2 = new User(2, "DirtyFrank");
        User[] users = new User[2];
        users[0] = user;
        users[1] = user2;
        // invoke
        ResponseEntity<User> response = userController.authenticateUser("a");
        // analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    /*
     * Tests to make sure that authenticateUsers sends Internal server error when promted work
     */
    @Test
    public void testAuthenticateUsersIternalServerError() throws IOException {
        // setup
        // when get user is called and Iternal Server Error
        doThrow(new IOException()).when(mockUserDAO).getUsername("a");
        // invoke
        ResponseEntity<User> response = userController.authenticateUser("a");
        // analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // invoke
    }
}
