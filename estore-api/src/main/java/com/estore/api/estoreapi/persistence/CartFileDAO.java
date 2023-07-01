package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CartFileDAO implements CartDAO{
    //TODO documentation

    Map<Integer, Product> products; // a cart is a subset of the inventory, 
                                    // and it functions similarly

    private ObjectMapper objectMapper; // for reading and writing to the cart

    private String root;

    /**
     * Creates a cart file DAO
     * 
     * @param username      the name of the user whose cart to read/write tp
     * @param objectMapper  allows for serialization/deserialization
     * @throws IOException  throw if the file cannot be accessed
     */
    public CartFileDAO(@Value("${cart.file}") String root, ObjectMapper objectMapper) throws IOException {
        this.root = root;
        this.objectMapper = objectMapper;
        load("admin");
    }

    /**
     * saves the {@linkplain Product products} in the cart from the map as 
     * JSON objects
     * 
     * @return true on success
     * @throws IOException if the file cannot be accessed
     */
    private boolean save(String username) throws IOException {
        String filename = this.root + username + ".json";
        Product[] productArray = getProductArray(username);

        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * loads {@linkplain Product products} from the cart as JSON objects 
     * into java objects
     * 
     * @return true if successful
     * @throws IOException if the file cannot be accessed
     */
    private boolean load(String username) throws IOException {
        String filename = this.root + username + ".json";
        products = new TreeMap<>();

        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        for (Product product : productArray) {
            products.put(product.getId(), product);
        }

        return true;
    }

    /**
     * Generates an array of {@linkplain Product products} from tree map
     * Runs if empty, otherwise uses below implementation
     * 
     * @return The array of {@linkplain Product products}
     */
    private Product[] getProductArray(String username) throws IOException {
        return getProductArray(null, username);
    }

    /**
     * Creates an array of {@linkplain Product products} from tree map for any
     * {@linkplain Product products} that have containsText in their name
     * 
     * If containsText is null, return every {@linkplain Product product} in the map
     * 
     * @param containsText
     * @return
     */
    private Product[] getProductArray(String containsText, String username) throws IOException {
        load(username);
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }
    
    public Product[] getCartContents(String username) throws IOException {
        load(username);
        synchronized (products) {
            return getProductArray(username);
        }
    }

    public Product getCartProduct(int id, String username) throws IOException {
        load(username);
        synchronized (products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    public Product[] searchCart(String containsText, String username) throws IOException {
        load(username);
        synchronized (products) {
            return getProductArray(containsText);
        }
    }

    public Product addToCart(Product product, String username) throws IOException {
        load(username);
        synchronized (products) {
            products.put(product.getId(), product);
            save(username);
            return product;
        }
    }

    //TODO implement removeFromCart
    public boolean removeFromCart (int id, String username) throws IOException {
        return false;
    }
}
