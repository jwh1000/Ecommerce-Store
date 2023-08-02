package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface CartDAO {

    /**
     * Gets the contents of a user's cart
     * 
     * @param username the user whose cart to show the contents of
     * @return an array of products
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] getCartContents(String username) throws IOException;

    /**
     * Searches a users cart for one or more {@linkplain Product products} 
     * 
     * @param containsText the search parameter
     * @param username the user whose cart to search
     * @return an array of products that match the search term
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] searchCart(String containsText, String username) throws IOException;

    /**
     * gets a {@linkplain Product product} from a users cart
     * 
     * @param id the id of the product to get
     * @param username the user whose cart to get the product from
     * @return the product with the given id
     * @throws IOException if underlying storage cannot be accessed
     */
    Product getCartProduct(int id, String username) throws IOException;

    /**
     * adds a {@linkplain Product product} to a users cart
     * 
     * @param product the product to add
     * @param username the user whose cart to add the product to
     * @return the product added
     * @throws IOException if underlying storage cannot be accessed
     */
    Product addToCart(Product product, String username) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with a given id from a users cart.
     * 
     * @param id The id of the product to delete.
     * @param username The username of the owner of a shopping cart.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Cole DenBleyker
     */
    boolean removeFromCart (int id, String usernam) throws IOException;

    /**
     * Clears a user's cart
     * 
     * @param username The username of the owner of a shopping cart.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Jack Hunsberger
     */
    void clearCart (String usernam) throws IOException;


    public void updateCart(String username) throws IOException;
}