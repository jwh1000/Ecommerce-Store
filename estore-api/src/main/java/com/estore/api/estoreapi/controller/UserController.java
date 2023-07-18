package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

/**
 * Handles the REST API requests for the the estore
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Xin Huang
 */
@RestController
@RequestMapping("estore")
public class UserController {
    private UserDAO userDAO;

    /**
     * Creates a REST API controller which responds to http requests
     * 
     * @param userDAO the DAO to perform the basic CRUD operations
     *                this is injected by spring
     */
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * creates a {@linkplain User user} with a given user object
     * 
     * @param user the {@link User user} to create
     * 
     * @return a ResponseEntity with the new product and an OK status on success
     *         or a REsponseEntity with an internal server error
     * 
     * @author Xin Huang
     */
    @PostMapping("/users/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User[] users = userDAO.findUsers(user.getUsername());
            for (User searchUser : users) {
                if (searchUser.getUsername().equals(user.getUsername())) {
                    return new ResponseEntity<User>(HttpStatus.CONFLICT);
                }
            }
            User newUser = userDAO.createUser(user);
            return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets all products.
     * 
     * @return ResponseEntity HTTP status of OK when complete list is returned
     *         Returned list contains all users or an empty list if none
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Xin Huang
     */
    @GetMapping("/users")
    public ResponseEntity<User[]> getUsers() {
        try {
            User[] users = userDAO.getUsers();
            return new ResponseEntity<User[]>(users, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<User[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets single user based on id
     * 
     * @param id
     * 
     * @return ResponseEntity HTTP status of OK if item is found and returned
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Xin Huang
     */
    @GetMapping("/users/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        try {
            User user = userDAO.getUser(id);
            if (user != null)
                return new ResponseEntity<User>(user, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User users} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link User users}
     * 
     * @return ResponseEntity with array of {@link Users user} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all products that contain the text "ma"
     *         GET http://localhost:8080/users/?name=ma
     * @author Xin Huang
     */

    @GetMapping("/users/")
    public ResponseEntity<User[]> searchUsers(@RequestParam String name) {
        try {
            User[] user = userDAO.findUsers(name);
            try {
                if (!user[0].equals(null))
                    return new ResponseEntity<User[]>(user, HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (NullPointerException e) {
                return new ResponseEntity<User[]>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Authenticates the user based on Username
     * 
     * @param userName
     * @return ResponseEntity HTTP status of OK if item is found and returned
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Xin Huang
     */
    @GetMapping("/users/auth")
    public ResponseEntity<User> authenticateUser(@RequestParam String userName) {
        try {
            User user[] = userDAO.getUsername(userName);
            if (user != null)
                return new ResponseEntity<User>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
