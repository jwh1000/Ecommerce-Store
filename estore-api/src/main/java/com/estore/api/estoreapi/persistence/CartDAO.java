package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface CartDAO {
    //TODO documentation
    
    Product[] getCartContents() throws IOException;

    Product[] searchCart(String containsText) throws IOException;

    Product getCartProduct(int id) throws IOException;

    Product addToCart(Product product) throws IOException;

    boolean removeFromCart (int id) throws IOException;
}
