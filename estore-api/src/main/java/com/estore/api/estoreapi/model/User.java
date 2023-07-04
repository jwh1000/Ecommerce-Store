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
    private Boolean admin;
    /**
     * The id of the user
     * @param id
     */
    @JsonProperty("id")
    private int id;

    /**
     * Creates a user with a given username and password
     * @param username This is the username of the user
     * @param id This is the password of the user
     * @param admin This is the permission of the user to see if they have administrator rights
     */
    public User(@JsonProperty("username") String username){
        this.username = username;

        // These fields can be changed
        this.admin = false;
    }
    
    public String getUsername() {
        return username;
    }


    public Boolean getAdmin() {
        return admin;
    }

    // Mutators for all fields, so that an Owner will have an easier time editing
    // any particular user.

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin(Boolean admin){
        this.admin = admin;
    }


}
