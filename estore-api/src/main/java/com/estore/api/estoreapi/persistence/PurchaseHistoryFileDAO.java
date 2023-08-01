package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.User; 

/**
 * Purchase history file DAO
 * 
 * @author Cole DenBleyker
 */
@Component
public class PurchaseHistoryFileDAO implements PurchaseHistoryDAO{
    
    Map<Integer, Product> products; // purchase history is a subset of the inventory
                                    // and it functions similarly

    private ObjectMapper objectMapper; // for reading and writing to the purchase history

    private String root; // root directory for purchase history

    /**
     * Creates a purchase history file DAO
     * 
     * @param objectMapper allows for serialization/deserialization
     * @throws IOException if the file cannot be accessed
     */
    public PurchaseHistoryFileDAO(@Value("${purchases.file}") String root, ObjectMapper objectMapper) throws IOException {
        this.root = root;
        this.objectMapper = objectMapper;
        load("admin");
    }

    /**
     * Saves the {@linkplain Product products} in the purchase history 
     * from the map as JSON objects
     * 
     * @param username the username of the {@linkplain User user} whose purchase history to save
     * @return true if save was successful
     * @throws IOException if purchase history cannot be accessed
     */
    private boolean save(String username) throws IOException {
        String filename = this.root + username + ".json";
        Product[] purchasedArray = getPurchasedProducts(username);

        objectMapper.writeValue(new File(filename), purchasedArray);
        return true;
    }

    /**
     * Loads {@linkplain Product product{s}} from the cart as JSON objects
     * into java objects
     * 
     * @param username the username of the {@linkplain User user} whose purchase history to load
     * @return true if load was successful
     * @throws IOException if purchase history cannot be accessed
     */
    private boolean load(String username) throws IOException {
        String filename = this.root + username + ".json";
        products = new TreeMap<>();

        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        if(productArray == null) {
            productArray = new Product[0];
        }
        
        for(Product product : productArray) {
            products.put(product.getId(), product);
        }

        return true;
    }

    /**
     * Generates an array of {@linkplain Product products} from treemap
     * Runs if empty, otherwise uses implementation below
     * 
     * 
     * @param username the username of the {@linkplain User user} whose purchase history to get
     * @return an array of {@linkplain Product products}
     * @throws IOException if purchase history cannot be accessed
     */
    private Product[] getProductArray(String username) throws IOException {
        return getProductArray(null, username);
    }

    /**
     * Creates an array of {@linkplain Product products} from treemap
     * for any {@linkplain Product products} that have containsText in their name
     * 
     * If containsText is null, return every {@linkplain Product product} in the map
     * 
     * @param username the username of the {@linkplain User user} whose purchase history to get
     * @return an array of {@linkplain Product products}
     * @throws IOException if purchase history cannot be accessed
     */
    private Product[] getProductArray(String containsText, String username) throws IOException {
        ArrayList<Product> productArrayList = new ArrayList<>();

        for(Product product : products.values()) {
            if(containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * {@inheritDoc}
     */
    public Product[] getPurchasedProducts(String username) throws IOException {
        synchronized(products) {
            return getProductArray(username);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Product[] searchPurchases(String containsText, String username) throws IOException {
        synchronized(products) {
            return getProductArray(containsText, username);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Product getPurchasedProduct(int id, String username) throws IOException {
        synchronized(products) {
            if(products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Product addToPurchased(Product product, String username) throws IOException {
        synchronized(products) {
            products.put(product.getId(), product);
            save(username);
            return product;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeFromPurchased(int id, String username) throws IOException {
        synchronized(products) {
            if(products.containsKey(id)) {
                products.remove(id);
                return save(username);
            } else {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updatePurchased(String username) throws IOException {
        load(username);
    }
    
}
