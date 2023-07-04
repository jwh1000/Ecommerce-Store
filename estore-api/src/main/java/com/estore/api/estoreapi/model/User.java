package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * Class to represent a Product in our EStore.
 * 
 * @author Xin Huang
 */
public class User {
    /**
     * The username of the User
     */
    @JsonProperty("username")
    private String username;
    /**
     * The admin privilage of the User
     */
    @JsonProperty("admin")
    private String admin;
    /**
     * The id of the User.
     */
    @JsonProperty("id")
    private int id;

    /**
     * Creates a user with a given username and password
     * @param username This is the username of the user
     * @param admin This is the permission of the user to see if they have administrator rights
     */
    public User(@JsonProperty("id") int id, @JsonProperty("username") String username){
        this.username = username;
        this.id = id;

        // These fields can be changed
        this.admin = "no";
    }
    
    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }


    public String getAdmin() {
        return admin;
    }

    // Mutators for all fields, so that an Owner will have an easier time editing
    // any particular user.

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin(String admin){
        this.admin = admin;
    }

    public void setId(int id) {
        this.id = id;
    }

    // String representation of user, for tests/debugging
    @Override
    public String toString() {
        return "User ID " + this.id + ": " + this.username;
    }

}
