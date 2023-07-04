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
     * Finds all {@linkplain User users}
     * 
     * @return An array of all {@link User users}
     * 
     * @throws IOException if an issue with underly storage
     */
    
}
