package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.User;

public interface UserDAO {
    /**
     * Finds all {@linkplain User Users}
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] getUsers() throws IOException;
    
    /**
     * Creates and saves a {@linkplain User user}
     * 
     * @param user {@linkplain User user} object to be created
     * 
     * @return new {@link User user} if succesful, fail otherwise
     * 
     * @throws IOException if there is issue with underlying storage
     */
    User createUser(User user) throws IOException;

     /**
     * Finds all {@linkplain User users} whose name contain the text
     * 
     * @param containsText The text to compare to
     * 
     * @return An array of {@link User users} whoes names contain the text
     * 
     * @throws IOException if an issue with underlying storage
     */
    User[] findUsers(String containsText) throws IOException;

    /**
     * Finds specified {@linkplain User username} using id
     * 
     * @param id user's id
     * 
     * @return {@link User users} if user found with username
     * 
     * @return status not found if not found
     * 
     * @throws IOException if an issue with underlying storage
     */
    User getUser(int id) throws IOException;
}
