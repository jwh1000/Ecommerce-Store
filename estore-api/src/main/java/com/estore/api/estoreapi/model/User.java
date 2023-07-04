package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    /**
     * The username of the User
     */
    @JsonProperty("username")
    private String username;
    /**
     * The password of the User
     */
    @JsonProperty("password")
    private String password;
    /**
     * The admin privilage of the User
     */
    @JsonProperty("admin")
    private Boolean admin;

    /**
     * Creates a user with a given username and password
     * @param username This is the username of the user
     * @param password This is the password of the user
     * @param admin This is the permission of the user to see if they have administrator rights
     */
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password;

        // These fields can be changed
        this.admin = false;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    // Mutators for all fields, so that an Owner will have an easier time editing
    // any particular user.

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(Boolean admin){
        this.admin = admin;
    }


}
