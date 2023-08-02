package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 *  A {@linkplain User User's} purchased {@linkplain Product product} history
 * 
 * @author Cole DenBleyker
 */
public interface PurchaseHistoryDAO {
    
    /**
     * Gets the {@linkplain Product product(s)} a {@linkplain User user} has purchased
     * 
     * @param username the {@linkplain User user} whose purchase history to view
     * @return an array of the {@linkplain User user's} purchased {@linkplain Product product(s)}
     * @throws IOException if purchase history cannot be accessed
     */
    Product[] getPurchasedProducts(String username) throws IOException;

    /**
     * Searches a {@linkplain User user's} purchase history for a {@linkplain Product product}
     * 
     * @param containsText the search parameter
     * @param username the {@linkplain User user} whose purchase history to view
     * @return an array of {@linkplain Product product(s)} that match the search query
     * @throws IOException if purchase history cannot be accessed
     */
    Product[] searchPurchases(String containsText, String username) throws IOException;

    /**
     * Gets a {@linkplain Product product} from a {@linkplain User user's} purchase history
     * 
     * @param id the id of the {@linkplain Product product} to get
     * @param username the {@linkplain User user} whose purchase history to view
     * @return the {@linkplain Product product} with the given id
     * @throws IOException if purchase history cannot be accessed
     */
    Product getPurchasedProduct(int id, String username) throws IOException;

    /**
     * Adds a {@linkplain Product product} to a {@linkplain User user's} purchase history
     * 
     * @param product the {@linkplain Product product} to add to the purchase history
     * @param username the {@linkplain User user} whose purchase history to view
     * @return the {@linkplain Product product} added
     * @throws IOException if purchase history cannot be accessed
     */
    Product addToPurchased(Product product, String username) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with a given id from a {@linkplain User user's} purchase history
     * 
     * @param id the id of the {@linkplain Product product} to delete from purchase history
     * @param username the {@linkplain User user} whose purchase history to view
     * @return boolean of whether or not the {@linkplain Product product} deletion was successful
     * @throws IOException if purchase history cannot be accessed
     */
    boolean removeFromPurchased(int id, String username) throws IOException;

    /**
     * Updates a {@linkplain User user's} purchased {@linkplain Product product} history
     * 
     * @param username the {@linkplain User user} whose purchase history to view
     * @throws IOException if purchase history cannot be accessed
     */
    void updatePurchased(String username) throws IOException;
}
