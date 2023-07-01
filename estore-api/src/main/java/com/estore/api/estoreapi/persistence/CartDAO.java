package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface CartDAO {
    //TODO documentation
    
    Product[] getCartContents(String username) throws IOException;

    Product[] searchCart(String containsText, String username) throws IOException;

    Product getCartProduct(int id, String username) throws IOException;

    Product addToCart(Product product, String usernam) throws IOException;

    boolean removeFromCart (int id, String usernam) throws IOException;
}
