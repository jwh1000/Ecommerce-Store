package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface CartDAO {
    //TODO documentation
    
    Product[] getCartContents(String username) throws IOException;

    Product[] searchCart(String containsText, String username) throws IOException;

    Product getCartProduct(int id, String username) throws IOException;

    Product addToCart(Product product, String usernam) throws IOException;

    /**
     * Deletes a product with a given id.
     * 
     * @param id The id of the product to delete.
     * @param usernam The username of the owner of a shopping cart.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Cole DenBleyker
     */
    boolean removeFromCart (int id, String usernam) throws IOException;
}
