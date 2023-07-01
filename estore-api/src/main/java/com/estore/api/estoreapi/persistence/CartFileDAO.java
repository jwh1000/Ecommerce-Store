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

    private @Value("${cart.file}") String path; // path where user carts are saved

    private String user; // the current user whose cart to read/write to

    private String filename; // directory of actual cart file, built from path and user

    /**
     * Creates a cart file DAO
     * 
     * @param username      the name of the user whose cart to read/write tp
     * @param objectMapper  allows for serialization/deserialization
     * @throws IOException  throw if the file cannot be accessed
     */
    public CartFileDAO(String username, ObjectMapper objectMapper) throws IOException {
        
        if (username.equals(null)) {
            this.user = "admin";
        } else {
            this.user = username;
        }

        this.filename = this.path + this.user;
        
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * saves the {@linkplain Product products} in the cart from the map as 
     * JSON objects
     * 
     * @return true on success
     * @throws IOException if the file cannot be accessed
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductArray();

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
    private boolean load() throws IOException {
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
    private Product[] getProductArray() {
        return getProductArray(null);
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
    private Product[] getProductArray(String containsText) {
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

    //TODO Implement these 
    
    public Product[] getCartContents() throws IOException {
        synchronized (products) {
            return getProductArray();
        }
    }

    public Product getCartProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    public Product[] searchCart(String containsText) throws IOException {
        synchronized (products) {
            return getProductArray(containsText);
        }
    }

    public Product addToCart(Product product) throws IOException {
        synchronized (products) {
            products.put(product.getId(), product);
            save();
            return product;
        }
    }

    public boolean removeFromCart (int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            } else {
                return false;
            }
        }
    }
}
