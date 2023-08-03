package com.estore.api.estoreapi.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import com.estore.api.estoreapi.model.User;
import org.springframework.stereotype.Component;

/**
 * Implements JSON file persistence for Users.
 * 
 * @author Xin Huang
 */
@Component
public class UserFileDAO implements UserDAO {

    Map<Integer, User> users; // local cache of users so you don't
                              // need to read file every time

    private ObjectMapper objectMapper; // allows for conversion from user
                                       // object and JSON file

    private static int nextId; // the next id to use when making a new user

    private String filename; // the file name to read and write to

    /**
     * Creates a user file DAO
     * 
     * @param filename     the name of the file to read and write to
     * @param objectMapper allows for serialization/deserialization
     * 
     * @throws IOException throw if the file cannot be accessed
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return the next id to use
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain User users} from tree map
     * Runs if empty, otherwise uses below implementation
     * 
     * @return The array of {@linkplain User users}
     */
    private User[] getUserArray() {
        return getUserArray(null);
    }

    /**
     * Creates an array of {@linkplain User users} from tree map for any
     * {@linkplain User users} that have containsText in their name
     * 
     * If containsText is null, return every {@linkplain User user} in the map
     * 
     * @param containsText
     * @return
     */
    private User[] getUserArray(String containsText) {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user : users.values()) {
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    private User[] getExactUser(String userName) {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user : users.values()) {
            if (userName == null || user.getUsername().equals(userName)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     * saves the {@linkplain User users} from the map as JSON objects
     * 
     * @return true on success
     * @throws IOException if the file cannot be accessed
     */
    private boolean save() throws IOException {
        User[] userArray = getUserArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * loads {@linkplain User users} as JSON objects into java objects
     * 
     * @return true if successful
     * @throws IOException if the file cannot be accessed
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }

        ++nextId;
        return true;
    }

    /**
     ** {@inheritDoc}
     */

    @Override
    public User[] getUsers() throws IOException {
        synchronized (users) {
            return getUserArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized (users) {
            User newUser = new User(nextId(), user.getUsername());       

            String filePath = "estore-api/data/carts/" + user.getUsername() + ".json";
            File cartFile = new File(filePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(cartFile));
            writer.write("[]");
            writer.flush();

            filePath = "estore-api/data/purchases/" + user.getUsername() + ".json";
            File historyFile = new File(filePath);
            writer = new BufferedWriter(new FileWriter(historyFile));
            writer.write("[]");
            writer.close();
            
            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] findUsers(String containsText) throws IOException {
        synchronized (users) {
            return getUserArray(containsText);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] getUsername(String userName) throws IOException {
        synchronized (users) {
            return getExactUser(userName);
        }

    }

}
