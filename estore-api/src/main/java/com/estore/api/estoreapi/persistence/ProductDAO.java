package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

public interface ProductDAO {


    /**
     * Finds all {@linkplain Product products}
     * 
     * @return An array of all {@link Product products}
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@linkplain Product products} whose name contain the text
     * 
     * @param containsText The text to compare to
     * 
     * @return An array of {@link Product products} whoes names contain the text
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;

    /**
     * Finds specified {@linkplain Product products} using id
     * 
     * @param id products id
     * 
     * @return {@link Product products} if product found with id
     * 
     * @return status not found if not found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created
     * 
     * @return new {@link Product product} if successful, fail otherwise
     * 
     * @throws IOException if there is issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * @return the updated {@link Product product} if successful, null if
     *         {@link Product product} could not be found
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a product with a given id.
     * 
     * @param id The id of the product to delete.
     * @return Boolean of whether or not the product deletion was successful.
     * @throws IOException if underlying storage cannot be accessed
     * @author Rylan Arbour
     */
    boolean deleteProduct(int id) throws IOException;
}
